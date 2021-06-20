package epicgames.qa.automation.search;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import epicgames.qa.automation.base.BaseTest;
import epicgames.qa.automation.helper.FrameworkHelper;
import epicgames.qa.automation.pageobject.PageObject_Search_CaseSensitive;

public class CaseSensitive extends BaseTest {

    @Test(timeOut = FrameworkHelper.MAX_TEST_DURATION_IN_MSECONDS, description = "Verify Search function = Case Sensitive")
    public void testCaseSensitive() throws InterruptedException {
	SoftAssert softAssert = new SoftAssert();
	PageObject_Search_CaseSensitive poCaseSensitive = new PageObject_Search_CaseSensitive(driverHelper, driver);

	// Validate the Search function - Case Sensitive user case
	reporterTitleMessage("1. Verify Search - Case Sensitive user case.");
	boolean searchCaseSensitive = poCaseSensitive.isSearchCaseSensitiveWorking();
	softAssert.assertTrue(searchCaseSensitive, "The Search - Case Sensitive does not work well.");
	reporterStatusMessage(searchCaseSensitive);

	softAssert.assertAll();
    }
}
