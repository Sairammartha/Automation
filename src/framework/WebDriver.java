package framework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;

import java.util.Set;

public class WebDriver extends BrowserDriver {
    public static Set<HashMap<String, String>> orSheet;

    public static void readOrSheet() {
        try {
            orSheet = ExcelConnect.readExcelData(Main.orSheetPath, "OR");
        } catch (Exception e) {
            System.out.println("Unable to read the OR Sheet from the location :" + Main.orSheetPath);
        }
    }

    public static String getElementProperty(String strElementName) {
        String strElementProperty = "";
        for (HashMap<String, String> elementDetails : orSheet) {
            if (elementDetails.get("elementName").equalsIgnoreCase(strElementName)) {
                strElementProperty = elementDetails.get("Property");
            }
        }
        if (strElementProperty.equalsIgnoreCase("")) {
            System.out.println("Element Name Not Found in Reporitory : " + strElementName);
        }
        return strElementName;
    }

    public static void launchUrl(String url) {
        BrowserDriver.driver.get(url);
    }

    public static WebElement getControl(String strElementName) {
        String actualLocator = getElementProperty(strElementName);
        WebElement element = null;
        int index = 1;
        final By[] byCollections = {By.id(actualLocator),
                By.name(actualLocator), By.xpath(actualLocator),
                By.className(actualLocator), By.cssSelector(actualLocator),
                By.tagName(actualLocator), By.linkText(actualLocator),
                By.partialLinkText(actualLocator)};
        for (By by : byCollections) {
            try {
                element = driver.findElement(by);
                if (!element.equals(null)) {
                    break;
                }
            } catch (Exception e) {
                if (index == byCollections.length) {

                    System.out.println("Unable to find element  " + strElementName + e);
                } else {
                    index++;
                    continue;
                }
            }

        }
        return element;
    }
    public  static void clickElement(String strElementName){
        try {
            getControl(strElementName).click();
        }catch (Exception e){
            System.out.println("Failed to click on the element "+ strElementName+ " "+ e);
            e.printStackTrace();
        }
    }
}
