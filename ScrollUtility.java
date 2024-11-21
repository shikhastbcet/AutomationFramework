package utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Base.SeleniumWaits;
import ExceptionHandler.ActionResult;

import java.time.Duration;

public class ScrollUtility extends SeleniumWaits {

	// Example usage in your test
//	ScrollUtility scrollUtility = new ScrollUtility(driver);
//
//	// Execute scrolling methods based on user input
//	scrollUtility.executeScrollMethod("scrollDown", 500);                       // Scroll down by 500 pixels
//	scrollUtility.executeScrollMethod("scrollToElementAndClick", element, 5000); // Scroll to an element and click
//	scrollUtility.executeScrollMethod("scrollToTop");                            // Scroll to the top of the page
//	scrollUtility.executeScrollMethod("scrollToBottom");                          // Scroll to the bottom of the page
//    scrollUtility.scrollDown(500); 	                                          // Example: Scroll down by 500 pixels
//    scrollUtility.scrollUp(300);                                                // Example: Scroll up by 300 pixels

	public ActionResult scrollMethod(WebElement element, String action) {
		// TODO Auto-generated method stub
		// Remove parentheses and split the string
		int timeoutMillis = 0;
		int pix = 0;
		String methodName = action.substring(0, action.indexOf('('));
		String pixValue = action.substring(action.indexOf('(') + 1, action.indexOf(')'));
		pix = Integer.parseInt(pixValue); // Convert String to int
		executeScrollMethod(methodName, element, pix, timeoutMillis);
		return new ActionResult("scrollMethod : " + methodName, "Pass");

	}

	// Execute scrolling methods based on user input using switch case
	public void executeScrollMethod(String methodName, WebElement element, int pix, int timeoutMillis) {
		switch (methodName.toLowerCase()) {
		case "scrolldown":
			if (pix != 0) {
				scrollDown(pix);
			} else {
				System.out.println("Invalid parameters for scrollDown method.");
			}
			break;

		case "scrollup":
			if (pix != 0) {
				scrollUp(pix);
			} else {
				System.out.println("Invalid parameters for scrollUp method.");
			}
			break;

		case "scrolltotop":
			scrollToTop();
			break;

		case "scrolltobottom":
			scrollToBottom();
			break;
		case "scrolltoelement":
			if (element != null) {
				scrollToElement(element);
			} else {
				System.out.println("Invalid parameters for scrollToElement method.Element locator is null.");
			}
			break;

		case "scrolltoelementandclick":
			if (element != null) {
				scrollToElementAndClick(element);
			} else {
				System.out.println("Invalid parameters for scrollToElementAndClick method.Element locator is null.");
			}
			break;

		case "scrolltoelementandwaitforvisibility":
			if (element != null) {
				scrollToElementAndWaitForVisibility(element, timeoutMillis);
			} else {
				System.out.println("Invalid parameters for scrollToElementAndWaitForVisibility method.");
			}
			break;

		default:
			System.out.println("Invalid method name: " + methodName);
			break;
		}
	}

	// New method to scroll to an element only
	public void scrollToElement(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			System.out.println("Scrolled to element: " + element);
		} catch (Exception e) {
			System.out.println("Error while scrolling to element: " + e.getMessage());
			throw e;
		}
	}

	// Scroll to an element and wait until it's clickable
	public void scrollToElementAndClick(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			System.out.println("Scrolled to element: " + element);
			element.click();
			System.out.println("Element clicked: " + element);
		} catch (Exception e) {
			System.out.println("Error while scrolling to element and clicking: " + e.getMessage());
			throw e;
		}
	}

	// Scroll to an element and wait until it's visible
	public void scrollToElementAndWaitForVisibility(WebElement element, int timeoutMillis) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			System.out.println("Scrolled to element: " + element);

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeoutMillis));
			wait.until(ExpectedConditions.visibilityOf(element));

			System.out.println("Element is visible: " + element);
		} catch (Exception e) {
			System.out.println("Error while scrolling to element and waiting for visibility: " + e.getMessage());
			throw e;
		}
	}

	// Scroll down by a specified number of pixels
	public void scrollDown(int pixels) {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + pixels + ");");
			System.out.println("Scrolled down by: " + pixels + " pixels.");
		} catch (Exception e) {
			System.out.println("Error while scrolling down: " + e.getMessage());
			throw e;
		}
	}

	// Scroll up by a specified number of pixels
	public void scrollUp(int pixels) {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-" + pixels + ");");
			System.out.println("Scrolled up by: " + pixels + " pixels.");
		} catch (Exception e) {
			System.out.println("Error while scrolling up: " + e.getMessage());
			throw e;
		}
	}

	// Scroll to the top of the page
	public void scrollToTop() {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
			System.out.println("Scrolled to the top of the page.");
		} catch (Exception e) {
			System.out.println("Error while scrolling to the top: " + e.getMessage());
			throw e;
		}
	}

	// Scroll to the bottom of the page
	public void scrollToBottom() {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
			System.out.println("Scrolled to the bottom of the page.");
		} catch (Exception e) {
			System.out.println("Error while scrolling to the bottom: " + e.getMessage());
			throw e;
		}
	}

}
