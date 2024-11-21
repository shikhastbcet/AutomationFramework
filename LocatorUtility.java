package utilities;import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LocatorUtility {
    
    /**
     * Method to find an element based on locator type and value.
     *
     * @param driver       the WebDriver instance
     * @param locatorType  the type of locator (e.g., "id", "name", "xpath", etc.)
     * @param locatorValue the value of the locator
     * @return the located WebElement
     */
    public static WebElement findElement(WebDriver driver, String locatorType, String locatorValue) {
        WebElement element = null;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds wait
 
        try {
            switch (locatorType.toLowerCase()) {
                case "id":
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
                    break;

                case "name":
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
                    break;

                case "classname":
                case "class":
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
                    break;

                case "tagname":
                case "tag":
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locatorValue)));
                    break;

                case "cssselector":
                case "css":
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
                    break;

                case "xpath":
                	
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
                    break;

                case "linktext":
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
                    break;

                case "partiallinktext":
                case "partiallink":
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorValue)));
                    break;
            }
        } catch (Exception e) {
            System.out.println("An error occurred while locating the element: " + e.getMessage());
            e.printStackTrace();
        }

        return element;
    }
    
    
    
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");
        
        // Example 1: Locate an element by ID
        WebElement usernameField = LocatorUtility.findElement(driver, "id", "username");
        usernameField.sendKeys("testUser");

        // Example 2: Locate an element by XPath
        WebElement loginButton = LocatorUtility.findElement(driver, "xpath", "//button[@type='submit']");
        loginButton.click();

        // Example 3: Locate an element by CSS Selector
        WebElement linkElement = LocatorUtility.findElement(driver, "css", ".some-css-class");
        linkElement.click();
        
    }
}
