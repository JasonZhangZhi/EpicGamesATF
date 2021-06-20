package epicgames.qa.automation.helper;

import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumHelper {

	// check that href of provided links returns status code 200
	public static boolean isHrefStatusCodeOK(List<WebElement> links) {

		return isHrefStatusCodeOK(links, false, false);
	}

	// check that href of provided links returns status code 200
	// pauseBetweenResuests - slowing down between each href check may help to avoid
	// "HTTP/1.1 502 Bad Gateway" error
	// which can be returned when too many requests are hitting server too fasts
	// retryFailedRequest - check failed request 1 more time before returning false
	// and exiting the function
	public static boolean isHrefStatusCodeOK(List<WebElement> links, boolean pauseBetweenResuests,
			boolean retryFailedRequest) {

		for (WebElement lnk : links) {

			if (pauseBetweenResuests) {
				CommonHelper.waitInMilliSeconds(500);
			}

			//January 12, 2021 adding this condition temporary
			//apache client browser is not supported by twitter
			//skip twitter in the check
			
			if(lnk.getAttribute("text").contains("Twitter")) {
				continue;
			}

			String statusCode = CommonHelper.sendHttpGetRequestGetResponseInfo(lnk.getAttribute("href"), "statusCode");

			if (!statusCode.equals("200")) {

				if (retryFailedRequest) {

					CommonHelper.waitInMilliSeconds(500);
					statusCode = CommonHelper.sendHttpGetRequestGetResponseInfo(lnk.getAttribute("href"), "statusCode");

					if (!statusCode.equals("200")) {
						return false;
					}

				} else {
					
					return false;
				}
			}
		}
		return true;
	}

	public static void highlightElement(WebDriver driver, WebElement element, boolean enableBlinkingEffect) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		String style = "background-color: cyan; border: 4px solid red;";
		String script = "arguments[0].setAttribute('style', arguments[1]);";

		if (enableBlinkingEffect) {

			for (int i = 0; i < 2; i++) {

				js.executeScript(script, element, style); // apply style
				CommonHelper.waitInMilliSeconds(500);
				js.executeScript(script, element, ""); // remove style
				CommonHelper.waitInMilliSeconds(500);
			}

		} else {

			js.executeScript(script, element, style);
			CommonHelper.wait(1);
			js.executeScript(script, element, "");
		}

	}

	public static String getElementAttributeViaJS(WebDriver driver, WebElement element, String attribute) {

		String customScript = "return arguments[0]." + attribute + ";";
		return (String) ((JavascriptExecutor) driver).executeScript(customScript, element);

	}

	public static void scrollToBottom(WebDriver driver) {

		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		CommonHelper.wait(2);

	}

	public static void scrollToElement(WebDriver driver, WebElement we) {

		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].scrollIntoView(false);", we);
		CommonHelper.waitInMilliSeconds(500);
	}

	// usage: String url = SeleniumHelper.getBackgroundImageUrl(webElement);
	// returns: if provided element has background-image set returns corresponding
	// url, otherwise returns "none"
	public static String getBackgroundImageUrl(WebElement element) {

		String tempCss = element.getCssValue("background-image");
		return tempCss.replaceFirst("url", "").replaceAll("['()\"]", "");
	}
	
	//check image size in KB
	//returns integer in KB
	public static int checkImgSizeKB(String imgUrl) {
		int imgSize = 0;
		try {
			if(!imgUrl.contains(("data:image"))) {
				URLConnection urlConnection = new URL(imgUrl).openConnection();
				imgSize = urlConnection.getContentLength()/1024;
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return imgSize;
	}
	
	//check image width
		//returns integer 
		public static int checkImgWidth(WebElement imgUrl) {
			int element = 0;
			try {
				element = imgUrl.getSize().getWidth();
				} catch (Exception e) {
					e.printStackTrace();
				}
			return element;
		}
		
		//check image Height
		//returns integer 
		public static int checkImgHeight(WebElement imgUrl) {
			int element = 0;
			try {
				element = imgUrl.getSize().getHeight();
				} catch (Exception e) {
					e.printStackTrace();
				}
			return element;
		}
}