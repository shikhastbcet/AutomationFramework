package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import ExceptionHandler.ActionResult;

public class SeleniumWaits extends BaseTest {
public ActionResult processWaitAction(String action, WebElement element) {
    int time = 0, pollingTime = 0;
    try {
        // Extracting the arguments inside parentheses
        String waitType = action.substring(0, action.indexOf('(')).toLowerCase();
        String argumentsString = action.substring(action.indexOf('(') + 1, action.indexOf(')'));

        // Handle multiple arguments (time and pollingTime)
        if (argumentsString.contains(",")) {
            String[] arguments = argumentsString.split(",\\s*");
            time = Integer.parseInt(arguments[0]);
            pollingTime = Integer.parseInt(arguments[1]);
        } else { 
            time = Integer.parseInt(argumentsString);
        }

        applyWait(waitType, time, pollingTime, element);

        // Return success ActionResult
        return new ActionResult("Wait action completed successfully.", "success");

    } catch (NumberFormatException nfe) {
        System.out.println("Error parsing wait time or polling time: " + nfe.getMessage());
        nfe.printStackTrace(); // For detailed error tracing
        return new ActionResult("Error parsing wait time or polling time: " + nfe.getMessage(), "FAIL");
        
    } catch (Exception e) {
        System.out.println("An unexpected error occurred during wait action: " + e.getMessage());
        e.printStackTrace(); // For detailed error tracing
        return new ActionResult("An unexpected error occurred: " + e.getMessage(), "FAIL");
    }
}

	
    public ActionResult applyWait(String waitType, int timeoutMillis, int pollingMillis, WebElement element) {
        try { 
            switch (waitType.toLowerCase()) {
                case "wait": 
                    System.out.println("applyWait: " +"waitForElement" + " | timeoutMillis: " + timeoutMillis + " | pollingMillis: " + pollingMillis);

                    return waitForElement(timeoutMillis);

                case "waitt":
                    waitFor(timeoutMillis);
                    System.out.println("applyWait: " +"Thread wait" + " | timeoutMillis: " + timeoutMillis + " | pollingMillis: " + pollingMillis);

                    return new ActionResult("Waited for " + timeoutMillis + " milliseconds", "success");

                case "implicitwait":
                    setImplicitWait(timeoutMillis);
                    System.out.println("applyWait: " +"implicitwait" + " | timeoutMillis: " + timeoutMillis + " | pollingMillis: " + pollingMillis);

                    return new ActionResult("Implicit wait set for " + timeoutMillis + " milliseconds", "success");

                case "explicitwait":
                    if (timeoutMillis > 0 && element != null) {
                    	
                        waitForElementToBeClickable(element, timeoutMillis);
                        System.out.println("applyWait: " +"waitForElementToBeClickable" + " | timeoutMillis: " + timeoutMillis + " | pollingMillis: " + pollingMillis);

                        return new ActionResult("Element became clickable", "success");
                    } else {
                        return new ActionResult("Error: Element is required and timeout must be greater than zero for explicit wait.", "FAIL");
                    }

                case "fluentwait":
                    if (timeoutMillis > 0 && pollingMillis > 0 && element != null) {
                        WebElement visibleElement = (WebElement) waitForElementVisibility(element, timeoutMillis, pollingMillis);
                        System.out.println("applyWait: " +"fluentwait" + " | timeoutMillis: " + timeoutMillis + " | pollingMillis: " + pollingMillis);
                        return new ActionResult("Visible element found: " + visibleElement, "success");
                    } else { 
                        return new ActionResult("Error: Element is required and both timeout and polling must be greater than zero for fluent wait.", "FAIL");
                    }

                default:
                    return new ActionResult("Invalid wait type: " + waitType, "FAIL");
            }
        } catch (Exception e) {
            return new ActionResult("Error during applyWait: " + e.getMessage(), "FAIL");
        }
    }

    // Wait
    public ActionResult waitForElement(int timeoutMillis) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeoutMillis));
            // Add your logic to wait for an element
            return new ActionResult("Wait successful for timeoutMillis: " + timeoutMillis, "success");
        } catch (TimeoutException te) {
            return new ActionResult("Timeout while waiting for element: " + te.getMessage(), "FAIL");
        } catch (Exception e) {
            return new ActionResult("An error occurred during waitForElement: " + e.getMessage(), "FAIL");
        }
    }

    // Implicit Wait
    public Timeouts setImplicitWait(int timeoutMillis) {
        return driver.manage().timeouts().implicitlyWait(Duration.ofMillis(timeoutMillis));
    }

    // Explicit Wait for Element to be Clickable
    public ActionResult waitForElementToBeClickable(WebElement element, int timeoutMillis) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeoutMillis));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return new ActionResult("Element became clickable", "success");
        } catch (TimeoutException te) {
            return new ActionResult("Timeout while waiting for element to be clickable: " + te.getMessage(), "FAIL");
        } catch (Exception e) {
            return new ActionResult("An error occurred while waiting for element to be clickable: " + e.getMessage(), "FAIL");
        }
    }

    // Fluent Wait for Element Visibility
    public ActionResult waitForElementVisibility(WebElement element, int timeoutMillis, int pollingMillis) {
        try {
            FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofMillis(timeoutMillis))
                    .pollingEvery(Duration.ofMillis(pollingMillis))
                    .ignoring(NoSuchElementException.class);

            fluentWait.until(driver -> element);
            return new ActionResult("Element is visible", "success");
        } catch (TimeoutException te) {
            return new ActionResult("Timeout while waiting for element visibility: " + te.getMessage(), "FAIL");
        } catch (Exception e) {
            return new ActionResult("An error occurred during fluent wait: " + e.getMessage(), "FAIL");
        }
    }

    // Method to pause execution for the specified number of milliseconds
    public static void waitFor(int milliseconds) {
        try {
            if (milliseconds <= 0) {
                throw new IllegalArgumentException("Polling interval must be greater than 0.");
            }
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("Thread was interrupted while sleeping: " + e.getMessage());
        }
    }
}
