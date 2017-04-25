package org.sprouts.digitalmusic.backend.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sprouts.digitalmusic.model.Customer;
import org.sprouts.digitalmusic.model.Review;
import org.sprouts.digitalmusic.model.parser.recommender.AlsoBoughtRecommender;
import org.sprouts.digitalmusic.model.parser.recommender.BestReviewedDuringLastSixMonths;
import org.sprouts.digitalmusic.model.parser.recommender.CollaborativeFilteringRecommender;
import org.sprouts.digitalmusic.model.parser.recommender.ItemProfileRecommender;
import org.sprouts.digitalmusic.model.parser.recommender.ItemRecommendation;
import org.sprouts.digitalmusic.model.parser.recommender.MostSoldDuringLastSixMonths;

import com.amazonaws.util.Base64;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("recommenderService")
public class RecommenderService {

	private static String user = "admin";
	private static String pass = "sup3r-4dm1n-pa$$-rE$t-H3ART";

	private static String userJobServer = "sprouts";
	private static String passJobServer = "j0b-s3rv3r-suP3r-PA$$-SprOut$";

	@Autowired
	private ItemService itemService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ReviewService reviewService;
	
	public AlsoBoughtRecommender getAlsoBoughtRecommender(int itemId) {
		AlsoBoughtRecommender alsoBoughtRecommender;

		try {
			List<AlsoBoughtRecommender> listAlsoBought;
			listAlsoBought = getObjectMapper().readValue(
					getResults("also_bought_recommender?filter={item_id:" + itemId + "}"),
					new TypeReference<List<AlsoBoughtRecommender>>() {
					});
			alsoBoughtRecommender = listAlsoBought.get(0);
		} catch (Exception e) {
			alsoBoughtRecommender = new AlsoBoughtRecommender();
		}

		return alsoBoughtRecommender;
	}

	public List<BestReviewedDuringLastSixMonths> getBestReviewedDuringLastSixMonths() {
		List<BestReviewedDuringLastSixMonths> bestReviewedDuringLastSixMonths;

		try {
			bestReviewedDuringLastSixMonths = getObjectMapper().readValue(
					getResults("best_reviewed_during_last_six_months"),
					new TypeReference<List<BestReviewedDuringLastSixMonths>>() {
					});
		} catch (IOException e) {
			bestReviewedDuringLastSixMonths = new ArrayList<>();
		}

		return bestReviewedDuringLastSixMonths;
	}

	public List<MostSoldDuringLastSixMonths> getMostSoldDuringLastSixMonths() {
		List<MostSoldDuringLastSixMonths> mostSoldDuringLastSixMonths;

		try {
			mostSoldDuringLastSixMonths = getObjectMapper().readValue(getResults("most_sold_during_last_six_months"),
					new TypeReference<List<MostSoldDuringLastSixMonths>>() {
					});
		} catch (IOException e) {
			mostSoldDuringLastSixMonths = new ArrayList<>();
		}

		return mostSoldDuringLastSixMonths;
	}

	public List<ItemRecommendation> getCollaborativeFilteringRecommends() {
		List<CollaborativeFilteringRecommender> lCollaborative = new ArrayList<>();
		List<ItemRecommendation> result = new ArrayList<>();

		Customer customer =
		//customerService.findByUsername(UserDetailsService.getPrincipal().getUsername());
		customerService.findByUsername("wesleyhurley27");
				
		// first, check if this user has reviews
		Collection<Review> reviews = reviewService.findReviewsOfCustomer(customer);
		
		// if this user has reviews, search in the collaborative filtering
		if(!reviews.isEmpty()){
			// query the warehouse to search the recommendations for this user
			try {
				lCollaborative = getObjectMapper().readValue(
						getResults("collaborative_filtering_recommendations?filter={customer_id:" + customer.getId() + "}"),
						new TypeReference<List<CollaborativeFilteringRecommender>>() {
						});
			} catch (IOException e) {
				lCollaborative = new ArrayList<>();
			}

			// if there are not recommendations in the warehouse, call the jobserver
			if (lCollaborative.isEmpty()) {
				try {
					lCollaborative = getObjectMapper().readValue(
							getResultsJobServer("sprouts.spark.recommender.RecommendProductsCollaborativeFiltering", customer.getId()),
							new TypeReference<List<CollaborativeFilteringRecommender>>() {
							});
				} catch (Exception e) {
					result = new ArrayList<>();
				}
			}
			
			if(!lCollaborative.isEmpty()){
				CollaborativeFilteringRecommender rec = lCollaborative.get(0);
				Collections.shuffle(rec.getItems());
				result = rec.getItems().subList(0, 6);
			}
		}

		return result;
	}

	public ItemProfileRecommender getItemProfileRecommeender(int itemId) {
		ItemProfileRecommender itemProfileRecommender;

		try {
			List<ItemProfileRecommender> listItemProfileRec;
			listItemProfileRec = getObjectMapper().readValue(
					getResults("item_profile_recommender?filter={item_id:" + itemId + "}"),
					new TypeReference<List<ItemProfileRecommender>>() {
					});
			itemProfileRecommender = listItemProfileRec.get(0);
			Collections.shuffle(itemProfileRecommender.getItems());
			itemProfileRecommender.setItems(itemProfileRecommender.getItems().subList(0, 6));
		} catch (Exception e) {
			itemProfileRecommender = new ItemProfileRecommender();
		}

		return itemProfileRecommender;
	}

	/*** Returns a configured ObjectMapper instance */
	public static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	/*** Returns an String representing the warehouse response */
	public static String getResults(String collection) {
		String embeddedObj;
		try {
			disableSSL();
			String url = "https://warehouse.sprouts-project.com/warehouse/" + collection;
			String authStringEnc = new String(Base64.encode((user + ":" + pass).getBytes()));

			URL obj = new URL(url);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", "Basic " + authStringEnc);

			String response = IOUtils.toString(con.getInputStream());
			
			JSONObject json = new JSONObject(response);
			embeddedObj = json.get("_embedded").toString();
		} catch (IOException e) {
			embeddedObj = "";
		} catch (JSONException e) {
			embeddedObj = "";
		}
		return embeddedObj;
	}

	public static String getResultsJobServer(String classPath, int userId) {
		String embeddedObj;
		try {
			disableSSL();
			String url = "https://jobserver.sprouts-project.com:8090/jobs?appName=sprouts-jobs&classPath=" + classPath
					+ "&sync=true&timeout=300";
			String authStringEnc = new String(Base64.encode((userJobServer + ":" + passJobServer).getBytes()));

			URL obj = new URL(url);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			String postStr = "input.string = " + new Integer(userId).toString();

			con.setDoOutput(true);
			con.setRequestProperty("Authorization", "Basic " + authStringEnc);
			con.getOutputStream().write(postStr.getBytes("UTF-8"));

			String response = IOUtils.toString(con.getInputStream());
			
			// unescape json string
			response = StringEscapeUtils.UNESCAPE_JSON.translate(response);
			
			// remove first and last double quotes
			response = response.replaceFirst("\"\\{", "{");
			
			int ind = response.lastIndexOf('"');
			if( ind>=0 )
			    response = new StringBuilder(response).replace(ind, ind+1,"").toString();
			
			// parse json
			JSONObject json = new JSONObject(response);
			embeddedObj = json.get("result").toString();
			
		} catch (IOException e) {
			embeddedObj = "";
		} catch (JSONException e) {
			embeddedObj = "";
		}
		return embeddedObj;
	}

	/***** DISABLE CERTIFICATES ******/
	private static void disableSSL() {
		try {
			TrustManager[] trustAllCerts = new TrustManager[] { new MyTrustManager() };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (NoSuchAlgorithmException e) {

		} catch (KeyManagementException e) {

		}
	}

	static class MyTrustManager implements X509TrustManager {
		@Override
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return new java.security.cert.X509Certificate[0];
		}

		@Override
		public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
				String paramString) throws CertificateException {

		}

		@Override
		public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
				String paramString) throws CertificateException {

		}
	}

}