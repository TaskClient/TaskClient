package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class OSM_GetOrder {
	public static String cookie;
	public static StringBuffer responseForGetOrder;
	public static StringBuffer xmlResponseForGetOrder;

	public static String user, password;
	 String localIP;
	 String port;
	 public String Result;
	 public static String orderId,HistOrderId;
	 
	@SuppressWarnings("static-access")
	public String getOrderData() throws Exception {

		@SuppressWarnings("deprecation")
		String str = URLEncoder.encode("username") + "="
				+ URLEncoder.encode(user) + "&" + URLEncoder.encode("password")
				+ "=" + URLEncoder.encode(password);
		CheckConnectivity checkConnectivity=new CheckConnectivity();
		boolean connectivity=checkConnectivity.Connectivity();
		System.out.println("My answer"+connectivity);
		if(connectivity==true)
		{
		Result="Success";
		localIP=checkConnectivity.publicIP;
		port=checkConnectivity.port;
		byte[] bytes = str.getBytes();
		URL url = new URL("http://" + localIP
				+ ":"+port+"/OrderManagement/XMLAPI/login");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(1000);
		connection.setAllowUserInteraction(false);
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length",
				String.valueOf(bytes.length));
		connection.connect();
		OutputStream out = connection.getOutputStream();
		out.write(bytes);
		out.close();
		int code = connection.getResponseCode();
		System.out.println("Code output : "+code);

		if (code == HttpURLConnection.HTTP_OK) {
			String receivedcookie = connection.getHeaderField("Set-Cookie");
			if (receivedcookie == null) {
				throw new Exception("Server did not return session cookie");
			}
			cookie = receivedcookie.substring(0, receivedcookie.indexOf(';'));
		} else {
			System.out.println("Gettig Here");
			throw new Exception("HTTP response code != 200 OK :" + code);
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String inputLine;
		responseForGetOrder = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			responseForGetOrder.append(inputLine);
		}
		System.out.println(responseForGetOrder);
		in.close();
		connection.disconnect();

		URL xmlApiUrl = new URL("http://" + localIP
				+ ":"+port+"/OrderManagement/XMLAPI/XMLAPI");
		connection = (HttpURLConnection) xmlApiUrl.openConnection();

		
		String request1= "<GetOrder.Request xmlns=\"urn:com:metasolv:oms:xmlapi:1\">";
		String request2="<OrderID>"+orderId+"</OrderID>";
		String request3="<Accept>true</Accept>";
		String request4="<OrderHistID>"+HistOrderId+"</OrderHistID>";
		String request5="</GetOrder.Request>";

		
		String requestXml =request1+request2+request3+request4+request5;
		System.out.println("Request: " + requestXml);
		connection.setAllowUserInteraction(false);
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.addRequestProperty("Cookie", cookie);
		connection.setRequestProperty("Content-Type", "text/xml");
		

		connection.connect();
		out = connection.getOutputStream();
		out.write(requestXml.getBytes());
		out.close();

		connection.getResponseMessage();

		in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		System.out.println("Request: " + requestXml);

		xmlResponseForGetOrder = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			xmlResponseForGetOrder.append(inputLine);
		}
		System.out.println("Response from OSM XML:" + xmlResponseForGetOrder);
		in.close();
		connection.disconnect();

		url = new URL("http://" + localIP
				+ ":"+port+"/OrderManagement/XMLAPI/logout");
		connection = (HttpURLConnection) url.openConnection();
		connection.setAllowUserInteraction(false);
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.addRequestProperty("Cookie", cookie);
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length", "0");
		connection.connect();
		code = connection.getResponseCode();

		if (code == HttpURLConnection.HTTP_OK) {

		} else {
			throw new Exception("HTTP response code != 200 OK :" + code);
		}
		in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		responseForGetOrder = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			responseForGetOrder.append(inputLine);
		}
		System.out.println(responseForGetOrder);
		in.close();
		connection.disconnect();
		}
		else
		{
			Result="Failure";
		}

		return Result;
	}

}
