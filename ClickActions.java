package Base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import ExceptionHandler.ActionResult;

public class ClickActions extends SendKeysActions {

    public ActionResult performAction(WebElement element, String action) {
        try {
//        	System.out.println("performAction : "+action);
 
            switch (action.toLowerCase()) {
                case "click":
                    click(element);
                    break;
                case "javascriptclick": 
                    javascriptClick(element); 
                    break;
                case "doubleclick":
                    doubleClick(element);
                    break;
                case "rightclick":
                    rightClick(element);
                    break;
                case "click&hold":
                    clickAndHold(element);
                    break;
                case "releaseclick":
                    releaseClick(element);
                    break;
                case "keyboardclick":
                    keyboardClick(element);
                    break;
                default:
             return handleException(new IllegalArgumentException("Invalid Click Method Name : "), "Fail");


            }
        } catch (Exception e) {
          return  handleException(e, action);
        }
        return new ActionResult( action, "    Pass");
    }

    public void click(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            handleException(e, "click");
        }
    }

    public void javascriptClick(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            handleException(e, "javascriptClick");
        }
    }

    public void actionsClick(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().perform();
        } catch (Exception e) {
            handleException(e, "actionsClick");
        }
    }

    public void rightClick(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.contextClick(element).perform();
        } catch (Exception e) {
            handleException(e, "rightClick");
        }
    }

    public void doubleClick(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.doubleClick(element).perform();
        } catch (Exception e) {
            handleException(e, "doubleClick");
        }
    }

    public void clickAndHold(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.clickAndHold(element).perform();
        } catch (Exception e) {
            handleException(e, "clickAndHold");
        }
    }

    public void releaseClick(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.clickAndHold(element).release().perform();
        } catch (Exception e) {
            handleException(e, "releaseClick");
        }
    }

    public void keyboardClick(WebElement element) {
        try {
            element.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            handleException(e, "keyboardClick");
        }
    }

    // Exception handling method
    public  ActionResult handleException(Exception e, String action) {
//        System.out.println("An exception occurred while performing action: " + action);
//        e.printStackTrace();
//        throw new RuntimeException("Stopping execution due to exception: " + e.getMessage());
//    
    	String errorMessage = "An exception occurred while performing action: " + action + ". Error: " + e.getMessage();
        System.out.println(errorMessage);
        e.printStackTrace();
        return new ActionResult(errorMessage, "fail");
    }
}
