package craftsvilla.assignment;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class reporterClass {
	public static ExtentReports report;
	public static ExtentTest test;

	// Constructor to initialize the constructor and Extent Test
	public reporterClass(ExtentTest test) {
		reporterClass.test = test;
	}

	// Setter getter for Extent Test
	public static ExtentTest getTest() {
		return test;
	}

	public static void setTest(ExtentTest test) {
		reporterClass.test = test;
	}

	// Getting the instance of Extent Reports "NEED TO MODIFY PATH FOR NETWORK
	public static ExtentReports getInstance() {
		if (report == null) {
			String projectPath = System.getProperty("user.dir");
			String reportPath = projectPath + "/test-output/RegisterCheck.html";
			File file = new File(reportPath);
			if (file.exists())
				file.delete();
			report = new ExtentReports(reportPath, true);
		}
		return report;
	}

	// Updating the report with the test step results and call for screenshots
	// as required
	public static void reportUpdate(String stepName, String status,
			String reason) {
		if (status.equalsIgnoreCase("PASS"))
			getTest().log(LogStatus.PASS, stepName, reason);
		else if (status.equalsIgnoreCase("INCONCLUSIVE"))
			getTest().log(LogStatus.UNKNOWN, stepName, reason);
		else if (status.equalsIgnoreCase("INFO"))
			getTest().log(LogStatus.INFO, stepName, reason);
		else
			getTest().log(LogStatus.FAIL, stepName, reason);

	}
}
