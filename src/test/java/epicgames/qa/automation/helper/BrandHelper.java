package epicgames.qa.automation.helper;

public class BrandHelper {

    // currently supported brands (main domain) - web test
    public static final String EPICGAMES_MAIN_DOMAIN = "epicgames.com";
    public static final String UAT_DOMAIN = "://uat";

    // currently supported brands (url of uat & production environment) - web test
    public static final String EPICGAMES_TEST_URL = "https://www.epicgames.com/store/en-US/browse";

    // determine if provided test url contains UAT Domain pattern
    public static boolean isUatEnvironment(String testURL) {

	if (testURL.contains(UAT_DOMAIN)) {
	    return true;
	}
	return false;
    }
}
