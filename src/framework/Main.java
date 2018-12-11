package framework;

import java.lang.reflect.InvocationTargetException;

public class Main {

	static String path = System.getProperty("user.dir");
	public static String testSetFile = path;
	public static String executionReportPath = path + "\\Projects\\logs\\";
	public static String strReportPath, strScreenShotFolder;
	public static String strBrowserDriver = path + "\\Projects\\BrowserDrivers\\";
	public static String testSetPath = path + "\\Projects\\TestSet.xlsx";
	public static String testCasePath = path + "\\Projects\\TestCase\\";
	public static String testDataPath = path + "\\Projects\\Data\\TestData.xlsx";
	public static String reportTemplate = path + "\\Projects\\Report Template\\ReportTemplate.xlsx";
	public static String orSheetPath=path+"\\Projects\\OR\\Repository.xlsx";
	public static String strScreenShotFile;

	public static void main(String[] args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("*********************Start***********************");

		if (args.length == 0) {
			System.out.println("Arguments Not Set, Please set the arguments as re-execute");
		}
		WebDriver.readOrSheet();
		BrowserDriver.getDriver(args[0]);
		Reporter.createExecutionReport();
		Framework.runTestSet();

	}
}
