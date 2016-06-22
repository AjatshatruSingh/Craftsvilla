package craftsvilla.assignment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

public class Init {

	public String PostBodyGenerator() throws IOException {

		String PostBody;
		PostBody = "{\"productSubType\":Register,\"userName\":"
				+ readPropFile("usrname", "userData.properties")
				+ ",\"password\":"
				+ readPropFile("passwd", "userData.properties")
				+ ",\"fullName\":"
				+ readPropFile("fullName", "userData.properties")
				+ ",\"mobNum\":\"" + readPropFile("mob", "userData.properties")
				+ "\",\"email\":\""
				+ readPropFile("email", "userData.properties") + "\"}";
		System.out.println(PostBody);
		return PostBody;
	}

	public String hitURL(String URL) {
		try {
			URL obj = new URL(URL);
			String PostBody;
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// making the request type "POST"
			con.setRequestProperty("Content-Type",
					"application/json; charset=utf-8");
			con.setRequestProperty("apiKey", "<testKey>");
			// // add request header
			con.setRequestProperty("User-Agent", "<USER_AGENT>");
			con.setConnectTimeout(60000);
			con.setRequestMethod("POST");

			con.setDoInput(true);
			con.setDoOutput(true);
			
			PostBody = PostBodyGenerator();
			
			// Putting post body
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeChars(PostBody);
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();

			if (responseCode != 200) {
				return "Invalid search Result. Response code: " + responseCode;
			}

			System.out.println("\nSending 'GET' request to URL : " + URL);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public String readPropFile(String s, String filename) throws IOException {
		Properties prop = new Properties();
		String path = URLDecoder.decode(ClassLoader.getSystemResource(filename)
				.getPath(), "UTF-8");

		FileInputStream fs = new FileInputStream(path);
		prop.load(fs);
		if (prop.containsKey(s)) {
			return prop.getProperty(s);
		} else {
			return "Value not found";
		}

	}

}
