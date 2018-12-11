package framework;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import framework.ExcelConnect.*;

public class Reporter extends ExcelConnect {
	static Boolean testStepStatus = true;
	static String strTimeStamp = null;

	public static void getScreenShot() {
		File screenshot = ((TakesScreenshot) BrowserDriver.driver).getScreenshotAs(OutputType.FILE);
		strTimeStamp = null;
		try {
			strTimeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
			Main.strScreenShotFile = Main.strScreenShotFolder + "\\ScreenShot_" + strTimeStamp + ".jpg";
			FileUtils.copyFile(screenshot, new File(Main.strScreenShotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			URL url = new URL("file:ScreenShots/" + "Screenshot_" + strTimeStamp
					+ /* "_" + excelconnect.reporterRow + */".jpg");
			Main.strScreenShotFile = url.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void createExecutionReport() {
		String strReportFolder = Main.executionReportPath += "ExecutionReport_"
				+ new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		new File(strReportFolder).mkdir();
		Main.strReportPath = strReportFolder + "\\ExecutionReport.xlsx";
		Main.strScreenShotFolder = strReportFolder + "\\ScreenShots";
		new File(Main.strScreenShotFolder).mkdir();
		createReport();
	}

	public static void createReport() {
		File input1 = new File(Main.reportTemplate);
		File input2 = new File(Main.strReportPath);
		try {
			FileUtils.copyFile(input1, input2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("File not Found " + Main.reportTemplate);
			e.printStackTrace();
		}
	}

	public static void reportPass(String string, String strStep, String strExpected, String strActualPass) {
		Reporter.reporter("Pass", strStep, strExpected, strActualPass);

	}

	public static void reportFail(String strStep, String strExpected, String strActualFail) {
		Reporter.reporter("Fail", strStep, strExpected, strActualFail);
	}

	public static void reportPassFail(boolean bPassFail, String strStep, String strExpected, String strActualPass,
			String strActualFail) {
		if (bPassFail == true) {
			Reporter.reporter("Pass", strStep, strExpected, strActualPass);
		} else {
			Reporter.reporter("Fail", strStep, strExpected, strActualPass);
		}

	}

	static int rowCount = 2;

	public static void reporter(String strPassFail, String strStep, String strExpected, String strActualPass) {
		getScreenShot();
		String[] result = { Integer.toString(rowCount - 1), strStep, strExpected, strActualPass, strPassFail,
				strTimeStamp, Framework.testCaseName };
		ExcelConnect.strFilePath = Main.strReportPath;
		ExcelConnect.getWorkbookObject();
		ExcelConnect.strSheetName = "Report";
		ExcelConnect.getSheetObject();
		for (int i = 1; i <= result.length; i++) {
			if (i == 5)
				ExcelConnect.setHyperLink(Main.strScreenShotFile);
			ExcelConnect.writeDataInExcel(workbook, strSheetName, rowCount, i, result[i - 1]);

		}
		rowCount++;
		if (testStepStatus == true) {
			String[] testCaseResult = new String[2];
			ExcelConnect.getWorkbookObject();
			ExcelConnect.strSheetName = "Summary";
			ExcelConnect.getSheetObject();
			if (strPassFail.equals("Pass")) {
				// Framework.testCaseName
				testCaseResult[0] = Framework.testCaseName;
				testCaseResult[1] = "PASS";
			} else if (strPassFail.equals("Fail")) {
				// Framework.testCaseName
				testCaseResult[0] = Framework.testCaseName;
				testCaseResult[1] = "FAIL";
				testStepStatus = false;
			}
			for (int i = 1; i <= testCaseResult.length; i++) {

				ExcelConnect.writeDataInExcel(workbook, strSheetName, Framework.testCaseCount + 1, i,
						testCaseResult[i - 1]);
			}

		}

	}
}
