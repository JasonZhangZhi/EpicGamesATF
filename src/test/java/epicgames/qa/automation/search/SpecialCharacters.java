package epicgames.qa.automation.search;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import epicgames.qa.automation.base.BaseTest;
import epicgames.qa.automation.helper.FrameworkHelper;
import epicgames.qa.automation.pageobject.PageObject_Search_SpecialCharacters;

public class SpecialCharacters extends BaseTest {

    @Test(timeOut = FrameworkHelper.MAX_TEST_DURATION_IN_MSECONDS, description = "Verify Search function = Special Characters")
    public void testSpecialCharacters() throws InterruptedException {
	SoftAssert softAssert = new SoftAssert();
	PageObject_Search_SpecialCharacters poSpecialCharacters = new PageObject_Search_SpecialCharacters(driverHelper,
		driver);

	// Validate the Search function - Case Sensitive user case
	reporterTitleMessage("1. Verify Search - Special Characters user case.");
	boolean searchSpecialCharacters = poSpecialCharacters.isSearchSpecialCharactersWorking();
	softAssert.assertTrue(searchSpecialCharacters, "The Search - Special Characters does not work well.");
	reporterStatusMessage(searchSpecialCharacters);

	softAssert.assertAll();
    }
}
