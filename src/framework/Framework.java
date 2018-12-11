package framework;

import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import moduleDrivers.CompositeDriver;
import org.apache.http.MethodNotSupportedException;

public class Framework {

	static String userDir = System.getProperty("user.dir");
	public static List<Method[]> allMethods = new ArrayList<Method[]>();
    public static List<Object> allDrivers = new ArrayList<Object>();
	public static int iMethod = 0;
	public static int iDriver = 0, testCaseCount = 0;
	public static Method sActualMethod;
	public static Object oActualDriver;
	public static String testCaseName;

	public static void getDriverMethods(String strMethod) {
		// new CompositeDriver();
		try{
		CompositeDriver.CompositeDriverMethod();
		// compositeDriver.toString();
		List<Method[]> aMethodList = Framework.allMethods;
		int intCounter = 0;
		for (Method[] aMethod : aMethodList) {
			Method objActuaMethod = getMethod(aMethod, strMethod);
			if (objActuaMethod != null) {
				sActualMethod = objActuaMethod;
				oActualDriver = Framework.allDrivers.get(intCounter);
			}

		}
		intCounter++;
		}catch (Exception e){
			System.out.println("unable to find the method "+strMethod);
			e.printStackTrace();
		}
	}

	private static Method getMethod(Method[] arrMethods, String strMethod) {
		// TODO Auto-generated method stub
		Method sActualMethod = null;
		try{
		for (Method sMethod : arrMethods) {
			if (sMethod.toString().contains(strMethod + "(")) {
				sActualMethod = sMethod;
				break;
			}
		}
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("Unable to get the methods : "+strMethod);
		}
		return sActualMethod;
	}

	public static void runTestSet() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String path;
		Set<HashMap<String, String>> testSetData = ExcelConnect.readExcelData(Main.testSetPath, "TestSet");

		for (HashMap<String, String> testSet : testSetData) {
			if (testSet.get("SelectYN").equalsIgnoreCase("y")) {
				System.out.println("==============Executing Testcase " + testSet.get("TestCase Name") + "============");
				// Main.testCasePath += testSet.get("TestCase Name") + ".xlsx";
				testCaseName = testSet.get("TestCase Name");
				testCaseCount++;
				Reporter.testStepStatus = true;
				path = Main.testCasePath + testSet.get("TestCase Name") + ".xlsx";
				Set<HashMap<String, String>> testCaseData = ExcelConnect.readExcelData(path, "TestCase");
				for (HashMap<String, String> testCase1 : testCaseData) {
					if (testCase1.get("SelectYN").equalsIgnoreCase("y")) {
						System.out.println("----Executing Component '" + testCase1.get("componentName")
								+ "' for Data Row ID : '" + testCase1.get("DataRowID") + "'----");
						Set<HashMap<String, String>> testData = ExcelConnect.readExcelData(Main.testDataPath,
                                testCase1.get("componentName"));
						for (HashMap<String, String> testData1 : testData) {
							if (testData1.get("DataRowID").equalsIgnoreCase(testCase1.get("DataRowID"))) {
								getDriverMethods(testCase1.get("componentName"));
								try {
									sActualMethod.invoke(oActualDriver, testData1);
								} catch (Exception e) {
									e.printStackTrace();
									System.out.println("Error in Component:" + testCase1.get("componentName") + ":"
											+ e.toString());
								}
							}
						}
					}
				}
			}
		}
	}
}
