package moduleDrivers;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;

import framework.Framework;

public class TestDemo extends CompositeDriver {
	/*
	 * public Module2() { Method[] arrMethods =
	 * this.getClass().getDeclaredMethods();
	 * Framework.allMethods.add(Framework.iMethod, arrMethods);
	 * Framework.allDrivers.add(Framework.iDriver, this); //
	 * Framework.objModuleDriver.put(arrMethods, this); Framework.iMethod++;
	 * Framework.iDriver++;
	 * 
	 * }
	 */

	public static void openURL1(HashMap<String, String> input) {
		System.out.println("=======================TestDemo1===========================" + "openURL1");
		framework.Reporter.reportPass("PAsS", "launch the application openURL1", "application should be launched successfully openURL1",
				"application has been launched openURL1");
		System.out.println(input);
	}

	public static void loginApplication1(HashMap<String, String> input) {
		System.out.println("=======================TestDemo1===========================" + "loginApplication1");
		System.out.println(input);
	}

	public static void navigateToCash1(HashMap<String, String> input) {
		System.out.println("/*****************333333333333**********/");
	}

}
