package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import ExceptionHandler.ActionResult;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BaseClass {
	public static WebDriver driver;
    protected static Logger logger = LogManager.getLogger(BaseTest.class);

	@BeforeClass
	public void setupDriver() {
        logger.info("Starting the setup process.");

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@AfterClass
	public void quitDriver() {
	    try {
	        if (driver != null) {
	            driver.quit();
	        }
	    } catch (Exception e) {
	        System.err.println("Error closing the driver: " + e.getMessage());
	    }
	} 
	
	 // Method to open a new tab
    public void openNewTab(String url) {
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to(url);

    }
	 // Method to open a new tab
    public ActionResult openUrl(String url) {
        try {
            System.out.println("URL OPEN : " + url);
            driver.navigate().to(url);
            return new ActionResult( "URL OPEN", "    Pass");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid URL provided: " + url);
            e.printStackTrace();
            return new ActionResult( "URL OPEN", "    Failes");

        } catch (WebDriverException e) {
            System.out.println("WebDriver encountered an issue while navigating to: " + url);
            e.printStackTrace();
            return new ActionResult( "URL OPEN", "    Failes");

        } catch (Exception e) {
            System.out.println("An unexpected error occurred while navigating to: " + url);
            e.printStackTrace();
            return new ActionResult( "URL OPEN", "    Failes");

        }
    }

    @AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
