package org.sprouts.digitalmusic.backend.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sprouts.digitalmusic.backend.security.UserDetailsService;
import org.sprouts.digitalmusic.model.Customer;
import org.sprouts.digitalmusic.model.Item;
import org.sprouts.digitalmusic.model.parser.recommender.AlsoBoughtRecommender;
import org.sprouts.digitalmusic.model.parser.recommender.BestReviewedDuringLastSixMonths;
import org.sprouts.digitalmusic.model.parser.recommender.CollaborativeFilteringJobServerResponse;
import org.sprouts.digitalmusic.model.parser.recommender.ItemProfileRecommender;
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
	
	public List<Item> getCollaborativeFilteringRecommends() {
		List<CollaborativeFilteringJobServerResponse> lCollaborative = new ArrayList<>();
		List<Item> result = new ArrayList<>();
		
		try {
			Customer customer = customerService.findByUsername(UserDetailsService.getPrincipal().getUsername());
			
			List<Object> objects;
			objects = getObjectMapper().readValue(getResultsJobServer("sprouts.spark.recommender.RecommendProductsCollaborativeFiltering", customer.getId()),
					new TypeReference<List<Object>>() {
					});
			for(Object o:objects){
				List<Object> aux = getObjectMapper().readValue(o.toString(),new TypeReference<List<Object>>(){
				});
				
				lCollaborative.add(new CollaborativeFilteringJobServerResponse((int) aux.get(0), (int) aux.get(1), (double) aux.get(2)));
			}
			
			List<CollaborativeFilteringJobServerResponse> sublist = lCollaborative.subList(0, 12);
			Collections.shuffle(sublist);
			
			for(CollaborativeFilteringJobServerResponse cf: sublist.subList(0, 6)){
				try{
					result.add(itemService.findOne(cf.getItemId()));
				}catch(Exception e){
				}
			}
			
		} catch (Exception e) {
			result = new ArrayList<>();
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
	
	public static String getResultsJobServer(String classPath, int userId){
		String embeddedObj;
		try {
			disableSSL();
			String url = "https://jobserver.sprouts-project.com:8090/jobs?appName=sprouts-jobs&classPath="+classPath+"&sync=true&timeout=300";
			String authStringEnc = new String(Base64.encode((userJobServer + ":" + passJobServer).getBytes()));

			URL obj = new URL(url);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			String postStr = "input.string = "+new Integer(userId).toString();
		    
			con.setDoOutput(true);
			con.setRequestProperty("Authorization", "Basic " + authStringEnc);
			con.getOutputStream().write(postStr.getBytes("UTF-8"));
			
			String response = IOUtils.toString(con.getInputStream());
			
			JSONObject json = new JSONObject(response);
			embeddedObj = json.get("result").toString();
						
		} catch (IOException e) {
			embeddedObj = "";
		} catch (JSONException e) {
			// TODO Auto-generated catch block
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