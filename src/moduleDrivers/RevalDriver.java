package moduleDrivers;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;

import com.thoughtworks.qdox.tools.QDoxTester.Reporter;

import framework.BrowserDriver;
import framework.Framework;

public class RevalDriver extends CompositeDriver {

	/*
	 * public RevalDriver() { /*Method[] arrMethods =
	 * this.getClass().getDeclaredMethods();
	 * Framework.allMethods.add(Framework.iMethod, arrMethods);
	 * Framework.allDrivers.add(Framework.iDriver, this); //
	 * Framework.objModuleDriver.put(arrMethods, this); Framework.iMethod++;
	 * Framework.iDriver++;
	 */

	// }

	// Component here

	public static void openURL(HashMap<String, String> input) {
		System.out.println("=======================RevalDriver===========================" + "openURL");
		BrowserDriver.driver.get(input.get("URL"));
		framework.Reporter.reportPass("PAsS", "launch the application ", "application should be launched successfully",
				"application has been launched ");
		System.out.println(input);
	}

	public static void loginApplication(HashMap<String, String> input) {
		framework.Reporter.reportFail("launch the application ", "application should be launched successfully",
				"application has been launched ");
		System.out.println("=======================RevalDriver===========================" + "loginApplication");
		System.out.println(input);
		framework.Reporter.reportPass("PAsS", "launch the application ", "application should be launched successfully",
				"application has been launched ");
	}

	public static void navigateToCash(HashMap<String, String> input) {
		System.out.println("/*****************333333333333**********/");
	}

}
