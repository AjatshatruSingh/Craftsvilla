package craftsvilla.assignment;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;



public class RegisterApiCheck extends Init {
	private static ExtentTest test;
	public static ExtentReports report;
	
	@Test
	public void IntraCityCabsSearch() throws ParseException,
			NoSuchAlgorithmException, IOException {
		
		test = report.startTest("Flight different Search Criterias");
		reporterClass.setTest(test);

		
		String response = "", responseMessage;
		String URL = "www.craftsvilla.com/register/v1";
		String stepName = "Verfying the Register API";
		
		response = hitURL(URL);
		System.out.println("Response :" + response);
		if (response.isEmpty()) {
			reporterClass.reportUpdate(stepName, "INCONCLUSIVE", "No response recieved");
			System.out.println("No Search Result received");
			report.endTest(test);
			return;
		}
		System.out.println(response);
		
		if(response.contains("Invalid search Result")){
			reporterClass.reportUpdate(stepName, "FAIL", "API Hit returns error "+response);
			System.out.println("API Hit returns error");
			report.endTest(test);
			return;
		}
		
		// Fetch the searchToken and array of searchProviders
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) jsonParser.parse(response);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		JSONObject json = (JSONObject) jsonObject.get("data");
		responseMessage = (String) json.get("response"); // Fetching the
		System.out.println(responseMessage);
		
		reporterClass.reportUpdate(stepName, "PASS", "The Registration Api hit was successfull with the response: "+response);
		
	}

}
