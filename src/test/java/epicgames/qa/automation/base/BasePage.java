package epicgames.qa.automation.base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import epicgames.qa.automation.helper.DriverHelper;
import epicgames.qa.automation.helper.FrameworkHelper;

public abstract class BasePage {

	protected final DriverHelper driverHelper;
	protected final WebDriver driver;

	public BasePage(DriverHelper driverHelper, WebDriver driver) {
		this.driverHelper = driverHelper;
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// check if image is loaded or not. Make sure an Image is a really an Image
	// usage: boolean imgOK = isImageCreatedFromTheGivenSource(driver, element);
	public static boolean isImageCreatedFromTheGivenSource(WebDriver driver, WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		// check whether image is loaded properly or not
		return (Boolean) js.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0;",
				element);
	}

	protected boolean isWebElementDisplayed(WebElement wElement) {

		// no mix of implicit and explicit wait turn it off
		// and then set it back on, at the end of the this method
		driverHelper.turnOffImplicitWait();

		try {

			WebDriverWait wait = new WebDriverWait(driver, FrameworkHelper.MAX_TIME_TO_SEARCH_FOR_ELEMENT_IN_SECONDS);
			wait.until(ExpectedConditions.visibilityOf(wElement));

			return true;

		} catch (TimeoutException e) {

			return false;

		} finally {

			driverHelper.setImplicitWait();
		}

	}

	// can be used when grabbing text to prevent
	// fast running script to incorrectly grab empty text
	protected boolean waitForAnyTextToAppear(final WebElement wElement) {

		// no mix of implicit and explicit wait turn it off
		// and then set it back on, at the end of the this method
		driverHelper.turnOffImplicitWait();

		WebDriverWait wait = new WebDriverWait(driver, FrameworkHelper.MAX_TIME_TO_SEARCH_FOR_ELEMENT_IN_SECONDS);

		try {
			wait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					return wElement.getText().length() != 0;
				}
			});

		} catch (TimeoutException e) {

			return false;

		} finally {

			driverHelper.setImplicitWait();
		}

		return true;
	}
	
	// can be used when checking if image is properly to prevent
	// fast running script to incorrectly return that image is not loaded
	protected boolean waitForImageToBeFullyLoaded(final WebElement wElement) {

		// no mix of implicit and explicit wait turn it off
		// and then set it back on, at the end of the this method
		driverHelper.turnOffImplicitWait();

		WebDriverWait wait = new WebDriverWait(driver, FrameworkHelper.MAX_TIME_TO_SEARCH_FOR_ELEMENT_IN_SECONDS);

		try {
			wait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					return isImageCreatedFromTheGivenSource(driver, wElement);
				}
			});

		} catch (TimeoutException e) {

			return false;

		} finally {

			driverHelper.setImplicitWait();
		}

		return true;
	}

	public static boolean isWebElementExisting(WebElement webEle) {		
		boolean webEleExist;
		try {
			webEle.getTagName();	// Check if the Web Element exists. If not will goto exception.
			webEleExist = true;			
		} catch (NoSuchElementException e) {
			webEleExist = false;
		}
		return webEleExist;		
	}
	
	// check if image is loaded or not. Make sure an Image is a really an Image
	// usage: boolean imgOK = isImageCreatedFromTheGivenSource(driver, element);
	protected boolean isImageLoaded(WebDriver driver, WebElement element) {

		boolean isImageLoaded = (Boolean) ((JavascriptExecutor) driver)
				.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth "
						+ "!= \"undefined\" && arguments[0].naturalWidth > 0", element);
		return isImageLoaded;
	}

}
