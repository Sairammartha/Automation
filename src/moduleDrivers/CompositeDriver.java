package moduleDrivers;

import java.lang.reflect.Method;

import framework.Framework;

public class CompositeDriver {
	public static void CompositeDriverMethod() {
		RevalDriver objDriver2 = new RevalDriver();
		TestDemo Module2 = new TestDemo();

	}

	public CompositeDriver() {
		Method[] arrMethods = this.getClass().getDeclaredMethods();
		Framework.allMethods.add(Framework.iMethod, arrMethods);
		Framework.allDrivers.add(Framework.iDriver, this);
		// Framework.objModuleDriver.put(arrMethods, this);
		Framework.iMethod++;
		Framework.iDriver++;

		// return Framework.iDriver;
	}

}
