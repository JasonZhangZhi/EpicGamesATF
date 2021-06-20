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

public class PageObject_Search_CaseSensitive extends BasePage {

    // The locator of Search Input
    @FindBy(css = "#searchInput")
    private WebElement searchInput;

    // The locator of Search Button
    @FindBy(css = ".css-kvenkf")
    private WebElement searchButton;

    // The locator of Search Results - Cards
    @FindBy(css = ".css-r0wyro-BrowseGrid-styles__card")
    private List<WebElement> searchCards;

    public PageObject_Search_CaseSensitive(DriverHelper driverHelper, WebDriver driver) {
	super(driverHelper, driver);
    }

    public boolean isSearchCaseSensitiveWorking() throws InterruptedException {

	int i = 0;
	boolean searchResultsSame = true;
	String savedKeyword = "";
	LinkedHashMap<Integer, String> searchCardTitles = new LinkedHashMap<Integer, String>();

	// Search the Keyword that setup in FrameworkHelper.SEARCH_CASE_SENSITIVE_INPUTS
	// respectively to make sure they all get the same results
	for (String input : FrameworkHelper.SEARCH_CASE_SENSITIVE_INPUTS) {
	    searchInput.clear();
	    searchInput.sendKeys(input);
	    searchButton.click();
	    Thread.sleep(2000);

	    i++;
	    if (i == 1) {
		// Search the 1st Keyword and then save the results to a LinkedHashMap
		System.out.println("The keyword: " + input + " get the following Cards:");
		Reporter.log("<font color='lightgreen'>The keyword: <font color='blue'>" + input
			+ "<font color='lightgreen'> get the following Cards:</font>");
		searchCardTitles = getSearchCardTitles();
		savedKeyword = input;
	    } else {
		// Search other Keywords to compare the result with the saved corresponding
		// value based on the key.
		searchResultsSame = compareSearchCardTitles(searchCardTitles);
		if (searchResultsSame) {
		    System.out.println("The keyword: " + input + " get the same results as " + savedKeyword);
		    Reporter.log("<font color='lightgreen'>The keyword: <font color='blue'>" + input
			    + "<font color='lightgreen'> get the same results as :<font color='blue'>" + savedKeyword
			    + "</font>");
		}
	    }
	}
	return searchResultsSame;
    }

    private LinkedHashMap<Integer, String> getSearchCardTitles() {
	int i = 0;
	LinkedHashMap<Integer, String> searchCardTitle = new LinkedHashMap<Integer, String>();

	// Go through each searched Card and save the Title to LinkedHashMap
	for (WebElement searchCard : searchCards) {
	    i++;
	    String cardTitle = searchCard.findElement(By.cssSelector(".css-2ucwu")).getText();
	    searchCardTitle.put(i, cardTitle);
	    System.out.println("Card #" + i + " Title: " + cardTitle);
	    Reporter.log("<font color='lightgreen'>Card #" + i + " Title: <font color='blue'>" + cardTitle + "</font>");
	}
	return searchCardTitle;
    }

    private boolean compareSearchCardTitles(LinkedHashMap<Integer, String> searchCardTitles) {
	int i = 0;
	boolean searchResultsSame = true;

	// Go through each searched Card to compare the Title with the saved
	// corresponding value in LinkedHashMap based on the key.
	for (WebElement searchCard : searchCards) {
	    i++;
	    String cardTitle = searchCard.findElement(By.cssSelector(".css-2ucwu")).getText();
	    // System.out.println("The current Card Title: " + cardTitle);
	    // System.out.println("The saved.. Card Title: " + searchCardTitles.get(i));

	    // If the Card Title is Not same as the saved previous Title, it means the
	    // search results are not same.
	    // If one of the Title is not same, the test should be failed.
	    if (!cardTitle.endsWith(searchCardTitles.get(i))) {

		System.out.println("The test result is not same!-----------------------------------------------------");

		searchResultsSame = false;
	    }
	}

	return searchResultsSame;
    }
}
