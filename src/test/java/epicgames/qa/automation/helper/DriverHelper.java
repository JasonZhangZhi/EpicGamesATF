package epicgames.qa.automation.helper;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverHelper {

    // currently supported desktop platforms
    public static final String DESKTOP_PLATFORM_WIN = "WINDOWS";
    public static final String DESKTOP_PLATFORM_MAC = "MAC";
    public static final String DESKTOP_PLATFORM_LINUX = "LINUX";

    public static final String CHROME = "CHROME";
    public static final String CHROME_DRIVER = "chromedriver.exe";
    public static final String CHROME_DRIVER_MAC = "chromedriver";

    // currently supported test types
    public static final String WEB_TEST = "WEB";

    private String backupTestUrl = BrandHelper.EPICGAMES_TEST_URL;
    private String backupBrowserType = DriverHelper.CHROME;
    private String browserType; // actual browser to be used. Can be either: paramBrowserType from testng.xml
				// file or value of backupBrowser
    private String testUrl; // actual test address to be used. Can be either: paramTestUrl from testng.xml
			    // file or value of backupTestUrl
    private String workingDirectory;
    private String testType;
    private String testPlatform;

    private WebDriver driver;

    // constructor
    public DriverHelper(String pBrowserType, String pTestUrl) {

	this.browserType = pBrowserType.equals("defaultBrowserType") ? this.backupBrowserType
		: pBrowserType.toUpperCase();
	this.testUrl = pTestUrl.equals("defaultTestUrl") ? this.backupTestUrl : pTestUrl;
	this.workingDirectory = System.getProperty("user.dir");
	this.testType = DriverHelper.WEB_TEST;
	this.testPlatform = DriverHelper.getDesktopPlatform();
    }

    /*
     * public DriverHelper (String aBrowserType, String aTestUrl) {
     * 
     * this.browserType = aBrowserType.equals("defaultBrowserType") ?
     * this.backupBrowserType : aBrowserType.toUpperCase(); this.testUrl =
     * aTestUrl.equals("defaultTestUrl") ? this.backupTestUrl : aTestUrl;
     * this.workingDirectory = System.getProperty("user.dir"); }
     */

    public String getBrowserType() {
	return this.browserType;
    }

    public String getTestUrl() {
	return this.testUrl.toLowerCase();
    }

    public String getWorkingDirectory() {
	return this.workingDirectory;
    }

    public String getTestType() {
	return this.testType;
    }

    public String getTestPlatform() {
	return this.testPlatform;
    }

    public void setImplicitWait() {
	this.driver.manage().timeouts().implicitlyWait(FrameworkHelper.MAX_TIME_TO_SEARCH_FOR_ELEMENT_IN_SECONDS,
		TimeUnit.SECONDS);
    }

    public void turnOffImplicitWait() {
	this.driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    // caller did not provide DesiredCapabilities
    public WebDriver getDriver() {
	return getDriver(null);
    }

    // call this method to get an WebDriver instance
    // if instance has already been created, existing one will be returned
    public WebDriver getDriver(DesiredCapabilities extraCapabilities) {

	// create new instance
	if (this.driver == null) {

	    ChromeOptions options = new ChromeOptions();

	    // These options are for ATF running on AWS within Linux Container
//			options.addArguments("--no-sandbox");
//			options.addArguments("--headless");
//			options.addArguments("--disable-dev-shm-usage");
//			options.addArguments("--disable-gpu");

	    // These options are for ATF running on local machine
	    options.addArguments("--disable-extensions");
	    options.addArguments("--disable-application-cache");
	    options.addArguments("--incognito");
	    options.addArguments("start-maximized");

	    LoggingPreferences logPrefs = new LoggingPreferences();
	    logPrefs.enable(LogType.PERFORMANCE, Level.ALL);

	    options.setCapability("goog:loggingPrefs", logPrefs);
	    options.setCapability("w3c: false", logPrefs);

	    DesiredCapabilities capabilities = new DesiredCapabilities();
	    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
	    options.merge(capabilities);

	    // set extra properties for WebDriver instance
	    if (extraCapabilities != null) {
		capabilities.merge(extraCapabilities);
	    }

	    // set driver as per browser requested
	    if (this.browserType.equals(DriverHelper.CHROME)) {

		System.setProperty("webdriver.chrome.driver",
			this.workingDirectory + System.getProperty("file.separator") + DriverHelper.CHROME_DRIVER);

		if (this.testPlatform.equals(DriverHelper.DESKTOP_PLATFORM_WIN)) {
		    this.driver = new ChromeDriver(new ChromeOptions().merge(options));
		} else if (this.testPlatform.equals(DriverHelper.DESKTOP_PLATFORM_MAC)) {
		    System.setProperty("webdriver.chrome.driver", this.workingDirectory
			    + System.getProperty("file.separator") + DriverHelper.CHROME_DRIVER_MAC);
		    this.driver = new ChromeDriver(options);
		} else if (this.testPlatform.equals(DriverHelper.DESKTOP_PLATFORM_LINUX)) {
		    this.driver = new ChromeDriver(options);
		}

		this.driver.manage().window().maximize();
	    }

	    setImplicitWait();
	}

	return this.driver;
    }

    // call this method to quite driver and to set null for webdriver held at the
    // class level
    public void quitDriver() {
	if (this.driver != null) {
	    this.driver.quit();
	    this.driver = null;
	}
    }

    // get desktop platform
    public static String getDesktopPlatform() {

	String os = System.getProperty("os.name").toLowerCase();
	String desktopPlatform = "";

	if (os.contains("windows")) {
	    desktopPlatform = DriverHelper.DESKTOP_PLATFORM_WIN;
	} else if (os.contains("mac")) {
	    desktopPlatform = DriverHelper.DESKTOP_PLATFORM_MAC;
	} else if (os.contains("linux")) {
	    desktopPlatform = DriverHelper.DESKTOP_PLATFORM_LINUX;
	}

	return desktopPlatform;
    }
}
