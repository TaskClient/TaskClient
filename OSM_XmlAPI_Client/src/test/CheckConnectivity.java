package test;

import java.net.HttpURLConnection;
import java.net.URL;

public class CheckConnectivity {
	
	public static String publicIP="183.82.68.84";
	public static String port="7051";
	public static Boolean result;
	
	
	public Boolean Connectivity() throws Exception {
		URL url = new URL(
				"http://"+publicIP+":"+port+"/OrderManagement/");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(1000);

		if (connection.getHeaderField(0) == null) {
			result = false;
		} else {
			result = true;
		}

		return result;
	}

}
