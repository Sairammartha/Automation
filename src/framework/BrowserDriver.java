package framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserDriver {

	public static WebDriver driver;

	public static WebDriver getDriver(String browser) {

		try {
			if (browser.equalsIgnoreCase("chrome")) {
				Main.strBrowserDriver += "chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", Main.strBrowserDriver);
				driver = new ChromeDriver();
				}
		}
		catch (Exception e){
			e.printStackTrace();

		}
		return driver;

	}

}
