package org.sprouts.digitalmusic.backend.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.stereotype.Service;
import org.sprouts.digitalmusic.model.parser.customer.BestReviewers;
import org.sprouts.digitalmusic.model.parser.customer.CustomerOverview;
import org.sprouts.digitalmusic.model.parser.customer.CustomerSegmentationAgeAndBrand;
import org.sprouts.digitalmusic.model.parser.customer.CustomerSegmentationAgeAndItemProfile;
import org.sprouts.digitalmusic.model.parser.finance.FinanceOverview;
import org.sprouts.digitalmusic.model.parser.finance.SalesPredictionsByItemProfiles;
import org.sprouts.digitalmusic.model.parser.items.ItemProfile;
import org.sprouts.digitalmusic.model.parser.recommender.AlsoBoughtRecommender;
import org.sprouts.digitalmusic.model.parser.stock.MonthlySalesPredictions;
import org.sprouts.digitalmusic.model.parser.stock.StockOverview;

import com.amazonaws.util.Base64;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("dashboardService")
public class DashboardService {

	private static String user = "admin";
	private static String pass = "sup3r-4dm1n-pa$$-rE$t-H3ART";

	public CustomerOverview getCustomerOverview() {
		CustomerOverview customerOverview;

		try {
			List<CustomerOverview> customerOverviews;
			customerOverviews = getObjectMapper().readValue(getResults("customer_overview"),
					new TypeReference<List<CustomerOverview>>() {
					});
			customerOverview = customerOverviews.get(0);
		} catch (IOException e) {
			customerOverview = new CustomerOverview();
		}
		return customerOverview;
	}

	public FinanceOverview getFinanceOverview() {
		FinanceOverview financeOverview;

		try {
			List<FinanceOverview> financeOverviews;
			financeOverviews = getObjectMapper().readValue(getResults("finances_overview"),
					new TypeReference<List<FinanceOverview>>() {
					});
			financeOverview = financeOverviews.get(0);
		} catch (IOException e) {
			financeOverview = new FinanceOverview();
		}
		return financeOverview;
	}

	public StockOverview getStockOverview() {
		StockOverview stockOverview;

		try {
			List<StockOverview> stockOverviews;
			stockOverviews = getObjectMapper().readValue(getResults("stock_overview"),
					new TypeReference<List<StockOverview>>() {
					});
			stockOverview = stockOverviews.get(0);
		} catch (IOException e) {
			stockOverview = new StockOverview();
		}
		return stockOverview;
	}

	public List<SalesPredictionsByItemProfiles> getSalesPredictionsByItemProfiles() {
		List<SalesPredictionsByItemProfiles> salesPredictionsByItemProfiles;

		try {
			salesPredictionsByItemProfiles = getObjectMapper().readValue(
					getResults("sales_predictions_by_item_profiles"),
					new TypeReference<List<SalesPredictionsByItemProfiles>>() {
					});
		} catch (IOException e) {
			salesPredictionsByItemProfiles = new ArrayList<>();
		}

		return salesPredictionsByItemProfiles;
	}
	
	
	public List<MonthlySalesPredictions> getMonthlySalesPredictions() {
		List<MonthlySalesPredictions> monthlySalesPredictions;

		try {
			monthlySalesPredictions = getObjectMapper().readValue(
					getResults("sales_predictions"),
					new TypeReference<List<MonthlySalesPredictions>>() {
					});
		} catch (IOException e) {
			monthlySalesPredictions = new ArrayList<>();
		}

		return monthlySalesPredictions;
	}
	
	public List<BestReviewers> getBestReviewers() {
		List<BestReviewers> bestReviewers;

		try {
			bestReviewers = getObjectMapper().readValue(
					getResults("best_reviewers"),
					new TypeReference<List<BestReviewers>>() {
					});
		} catch (IOException e) {
			bestReviewers = new ArrayList<>();
		}

		return bestReviewers;
	}
	
	public List<CustomerSegmentationAgeAndBrand> getCustomerSegmentationAgeAndBrand() {
		List<CustomerSegmentationAgeAndBrand> customerSegmentationAgeAndBrand;

		try {
			customerSegmentationAgeAndBrand = getObjectMapper().readValue(
					getResults("customer_segmentation_age_and_brand"),
					new TypeReference<List<CustomerSegmentationAgeAndBrand>>() {
					});
		} catch (IOException e) {
			customerSegmentationAgeAndBrand = new ArrayList<>();
		}

		return customerSegmentationAgeAndBrand;
	}
	
	public List<CustomerSegmentationAgeAndItemProfile> getCustomerSegmentationAgeAndItemProfile() {
		List<CustomerSegmentationAgeAndItemProfile> customerSegmentationAgeAndItemProfile;

		try {
			customerSegmentationAgeAndItemProfile = getObjectMapper().readValue(
					getResults("customer_segmentation_age_and_item_profile"),
					new TypeReference<List<CustomerSegmentationAgeAndItemProfile>>() {
					});
		} catch (IOException e) {
			customerSegmentationAgeAndItemProfile = new ArrayList<>();
		}

		return customerSegmentationAgeAndItemProfile;
	}
	
	public List<ItemProfile> getItemProfile() {
		List<ItemProfile> itemProfile;

		try {
			itemProfile = getObjectMapper().readValue(
					getResults("item_profiles"),
					new TypeReference<List<ItemProfile>>() {
					});
		} catch (IOException e) {
			itemProfile = new ArrayList<>();
		}

		return itemProfile;
	}
	
	public List<AlsoBoughtRecommender> getAlsoBoughtRecommender() {
		List<AlsoBoughtRecommender> alsoBoughtRecommender;

		try {
			alsoBoughtRecommender = getObjectMapper().readValue(
					getResults("also_bought_recommender"),
					new TypeReference<List<AlsoBoughtRecommender>>() {
					});
		} catch (IOException e) {
			alsoBoughtRecommender = new ArrayList<>();
		}

		return alsoBoughtRecommender;
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