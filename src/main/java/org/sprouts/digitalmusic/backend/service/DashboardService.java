package org.sprouts.digitalmusic.backend.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.stereotype.Service;
import org.sprouts.digitalmusic.model.parser.customer.CustomerOverview;
import org.sprouts.digitalmusic.model.parser.finance.FinanceOverview;
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
			disableSSL();
			String url = "https://warehouse.sprouts-project.com/warehouse/customer_overview";
			String authStringEnc = new String(Base64.encode((user + ":" + pass).getBytes()));

			URL obj = new URL(url);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", "Basic " + authStringEnc);

			String response = IOUtils.toString(con.getInputStream());				
			
			JSONObject json = new JSONObject(response);
			String embedded_obj = json.get("_embedded").toString();
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<CustomerOverview> array = mapper.readValue(embedded_obj,new TypeReference<List<CustomerOverview>>() {});
			customerOverview = array.get(0);
				
		} catch (Exception e) {
			customerOverview = new CustomerOverview();
		}
		return customerOverview;
	}
	
	public FinanceOverview getFinanceOverview() {
		FinanceOverview financeOverview;
		try {
			disableSSL();
			String url = "https://warehouse.sprouts-project.com/warehouse/finances_overview";
			String authStringEnc = new String(Base64.encode((user + ":" + pass).getBytes()));

			URL obj = new URL(url);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", "Basic " + authStringEnc);

			String response = IOUtils.toString(con.getInputStream());				
			
			JSONObject json = new JSONObject(response);
			String embedded_obj = json.get("_embedded").toString();
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<FinanceOverview> array = mapper.readValue(embedded_obj,new TypeReference<List<FinanceOverview>>() {});
			financeOverview = array.get(0);
				
		} catch (Exception e) {
			financeOverview = new FinanceOverview();
		}
		return financeOverview;
	}
	
	public StockOverview getStockOverview() {
		StockOverview stockOverview;
		try {
			disableSSL();
			String url = "https://warehouse.sprouts-project.com/warehouse/stock_overview";
			String authStringEnc = new String(Base64.encode((user + ":" + pass).getBytes()));

			URL obj = new URL(url);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", "Basic " + authStringEnc);

			String response = IOUtils.toString(con.getInputStream());				
			
			JSONObject json = new JSONObject(response);
			String embeddedObj = json.get("_embedded").toString();
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<StockOverview> array = mapper.readValue(embeddedObj,new TypeReference<List<StockOverview>>() {});
			stockOverview = array.get(0);
				
		} catch (IOException e) {
			stockOverview = new StockOverview();
		} catch (JSONException e) {
			stockOverview = new StockOverview();
		}
		return stockOverview;
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