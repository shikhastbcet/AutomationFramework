package Base;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import ExceptionHandler.ActionResult;  // Import the ActionResult class
import utilities.ScrollUtility;

public class SendKeysActions extends ScrollUtility {

    // Method to perform sendKeys action
    public ActionResult performSendKeys(WebElement element, String action, String value) {
        try {
//        	System.out.println("performSendKeys : "+action+"|  Value :"+value);

            switch (action.toLowerCase()) {
                case "sendkeys":
                    return sendKeys(element, value);
                case "javascriptsendkeys":
                    return javascriptSendKeys(element, value);
                case "actionssendkeys":
                	return  actionsSendKeys(element, value);
                case "robotsendkeys":
                	return  robotSendKeys(value);
                default:
                    return handleException(new IllegalArgumentException("Invalid action: " + action), action);
            }
        } catch (Exception e) { 
            return handleException(e, action);
        }
    }

    // Regular sendKeys using Selenium's WebDriver
    public ActionResult sendKeys(WebElement element, String value) {
        try {
            element.sendKeys(value);
            return new ActionResult("Send keys action performed successfully.", "Pass");
        } catch (Exception e) {
            return handleException(e, "Fail");
        }
    }

    // SendKeys using JavaScript to bypass some issues with sendKeys (useful for hidden fields, etc.)
    public ActionResult javascriptSendKeys(WebElement element, String value) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value=arguments[1];", element, value);
            return new ActionResult("JavaScript send keys action performed successfully.", "Pass");
        } catch (Exception e) {
            return handleException(e, "Fail");
        }
    }


    // SendKeys using Actions Class (for complex key combinations or mouse movements)
    public ActionResult actionsSendKeys(WebElement element, String value) {
        try {
        	
            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().sendKeys(value).perform();

            return new ActionResult("actionsSendKeys send keys action performed successfully.", "Pass");
        } catch (Exception e) {
            return handleException(e, "Fail");
        }
    }

    // SendKeys using Robot class (useful for non-interactable fields or keyboard simulations)
    public ActionResult robotSendKeys(String value) {
        try {
            Robot robot = new Robot();
            for (char c : value.toCharArray()) {
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                    throw new RuntimeException("Key code not found for character " + c);
                }
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
            }return new ActionResult("actionsSendKeys send keys action performed successfully.", "Pass");
        } catch (Exception e) {
            return handleException(e, "Fail");
        }
    }

    
    // Exception handling method that returns an ActionResult
    public ActionResult handleException(Exception e, String action) {
        String errorMessage = "An exception occurred while performing action: " + action + ". Error: " + e.getMessage();
        System.out.println(errorMessage);
        e.printStackTrace();
        return new ActionResult(errorMessage, "fail");
    }
}
