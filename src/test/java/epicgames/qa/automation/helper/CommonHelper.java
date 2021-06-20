package epicgames.qa.automation.helper;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class CommonHelper {

	public static void wait(int numOfSeconds) {

		try {
			TimeUnit.SECONDS.sleep(numOfSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		/*
		 * // as alternative of Thread.sleep - current time can be used but it may be
		 * cpu costly long now = System.currentTimeMillis(); long expectedTime = now +
		 * (numOfSeconds * 1000); while (now < expectedTime) { now =
		 * System.currentTimeMillis(); }
		 */
	}

	public static void waitInMilliSeconds(int numOfMilliSeconds) {

		try {
			TimeUnit.MILLISECONDS.sleep(numOfMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// get first tag value from html or xml file
	// usage String tgVal = CommonHelper.getTagValue(System.getProperty("user.dir")
	// + "\\pom.xml", "selenium.java.version");

	// send http get request - return response info
	// responseInfoType accepts: statusCode or responseBody
	public static String sendHttpGetRequestGetResponseInfo(String url, String responseInfoType) {

		String responseInfo = "";

		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);

			switch (responseInfoType) {

			case "statusCode":
				responseInfo = Integer.toString(response.getStatusLine().getStatusCode());
				break;
			case "responseBody":
				responseInfo = EntityUtils.toString(response.getEntity());
				break;
			}

		} catch (Exception e) {
			responseInfo = e.toString();
		}

		return responseInfo;
	}

	// returns the path part of the URL or an empty string
	// if path does not exist or in case of any error
	public static String getPathName(String url) {

		try {

			return new URL(url).getPath();

		} catch (Exception e) {

			return "";
		}
	}

}