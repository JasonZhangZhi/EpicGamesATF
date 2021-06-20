package epicgames.qa.automation.pageobject;

import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

import epicgames.qa.automation.base.BasePage;
import epicgames.qa.automation.helper.DriverHelper;
import epicgames.qa.automation.helper.FrameworkHelper;

public class PageObject_Search_SpecialCharacters extends BasePage {

    // The locator of Search Input
    @FindBy(css = "#searchInput")
    private WebElement searchInput;

    // The locator of Search Button
    @FindBy(css = ".css-kvenkf")
    private WebElement searchButton;

    // The locator of Search Results - Cards
    @FindBy(css = ".css-r0wyro-BrowseGrid-styles__card")
    private List<WebElement> searchCards;

    // The locator of "No Result Found"
    @FindBy(css = ".css-1blv02u-Empty__emptyTitle")
    private WebElement noResultFound;

    public PageObject_Search_SpecialCharacters(DriverHelper driverHelper, WebDriver driver) {
	super(driverHelper, driver);
    }

    public boolean isSearchSpecialCharactersWorking() throws InterruptedException {

	int i = 0;
	boolean searchResultsCorrect = true;
	LinkedHashMap<Integer, String> searchCardTitles = new LinkedHashMap<Integer, String>();

	// Search the Keyword that setup in FrameworkHelper.SEARCH_CASE_SENSITIVE_INPUTS
	// respectively to make sure they all get the same results
	for (String input : FrameworkHelper.SEARCH_SPECIAL_CHARACTERS_INPUTS) {
	    searchInput.clear();
	    searchInput.sendKeys(input);
	    searchButton.click();
	    Thread.sleep(2000);
	    i++;

	    // Verify the keyword of "TotalWar" will get no results found.
	    if (i == 1) {
		if (isWebElementExisting(noResultFound)) {
		    System.out.println("The keyword: " + input + " no result found.");
		    Reporter.log("<font color='lightgreen'>The Keyword: " + "<font color='blue'>" + input
			    + "<font color='lightgreen'> no result found.");
		} else {
		    if (searchCards.isEmpty()) {
			System.out.println("The keyword: " + input + " found some cards unexpectly.");
			Reporter.log("<font color='lightgreen'>The Keyword: " + "<font color='blue'>" + input
				+ "<font color='lightgreen'> found some cards unexpectly.</font>");
			searchResultsCorrect = false;
		    }
		}
	    } else {
		if (!searchCards.isEmpty()) {
		    for (WebElement searchCard : searchCards) {
			String cardTitle = searchCard.findElement(By.cssSelector(".css-2ucwu")).getText();
			if (!cardTitle.contains(input)) {
			    searchResultsCorrect = false;
			} else {
			    System.out.println(
				    "The keyword " + input + " found related card which Title is " + cardTitle);
			    Reporter.log("<font color='lightgreen'>The Keyword: " + "<font color='blue'>" + input
				    + "<font color='lightgreen'> found related card with Title is "
				    + "<font color='blue'>" + cardTitle + "</font>");
			}
		    }
		}
	    }
	}
	return searchResultsCorrect;
    }

}
