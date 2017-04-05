package org.sprouts.digitalmusic.backend.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.stereotype.Service;
import org.sprouts.digitalmusic.model.parser.CustomerOverview;

import com.amazonaws.util.Base64;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.json.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("dashboardService")
public class DashboardService {

	public CustomerOverview getCustomerOverview() {
		CustomerOverview customerOverview;
		try {
			disableSSL();
			String url = "https://warehouse.sprouts-project.com/warehouse/customer_overview";
			String userPassword = "admin" + ":" + "sup3r-4dm1n-pa$$-rE$t-H3ART";
			byte[] authEncBytes = Base64.encode(userPassword.getBytes());
			String authStringEnc = new String(authEncBytes);

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

	/***** DISABLE CERTIFICATES ******/
	private static void disableSSL() {
		try {
			TrustManager[] trustAllCerts = new TrustManager[] { new MyTrustManager() };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class MyTrustManager implements X509TrustManager {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
				String paramString) throws CertificateException {
			// TODO Auto-generated method stub

		}

		@Override
		public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
				String paramString) throws CertificateException {
			// TODO Auto-generated method stub

		}
	}

}