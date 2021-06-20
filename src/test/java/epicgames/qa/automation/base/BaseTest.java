package epicgames.qa.automation.base;

import java.time.LocalDateTime;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import epicgames.qa.automation.helper.DriverHelper;

public abstract class BaseTest {

    public DriverHelper driverHelper;
    public WebDriver driver;

    // read parameters from test suite
    @Parameters({ "paramBrowserType", "paramTestUrl" })
    @BeforeMethod
    public void beforeMethod(@Optional("defaultBrowserType") String pBrowserType,
	    @Optional("defaultTestUrl") String pTestUrl) {

	// capture test case date & start time
	System.out.println("Test Case: (" + this.getClass().getName() + ") started at (" + LocalDateTime.now()
		+ ") with params: (Browser:" + pBrowserType + " & Environment:" + pTestUrl + ")");

	// create instance of DriverFactoryHelper and get driver
	driverHelper = new DriverHelper(pBrowserType, pTestUrl);

	driver = driverHelper.getDriver();

	// navigate
	driver.get(driverHelper.getTestUrl());

    }

    @AfterMethod
    public void afterMethod() {
	// close test
	driverHelper.quitDriver();
    }

    public void reporterTitleMessage(String message) {
	Reporter.log("<font color='Darkblue'><b>" + message + " ......</b></font>");
    }

    public void reporterStatusMessage(Boolean testStatus) {

	if (testStatus) {
	    Reporter.log("<font color='Green'><b>    ---------- This test is Passed!</b></font>");
	} else {
	    Reporter.log("<font color='Red'><b>    ---------- This test is Failed!</b></font>");
	}
    }

}
