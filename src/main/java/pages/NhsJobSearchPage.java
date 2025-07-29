package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;


public class NhsJobSearchPage {

    WebDriver driver;

    public void NhsJobSearchPage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(name = "keyword")
    private WebElement jobTitle;

    @FindBy(name = "location")
    private WebElement location;

    @FindBy(id = "search")
    private WebElement searchBtn;

    @FindBy(name = "sort")
    private WebElement sortBy;


    public void enterSearchData(String title, String preferedLocation) {
        jobTitle.sendKeys(title);
        location.sendKeys(preferedLocation);
    }

    public void clickSearchBtn() {
        searchBtn.click();
    }

    public boolean resultsMatchPreferences(String jobTitle, String city) {
        List<WebElement> jobCards = driver.findElements(By.cssSelector(".job-listing")); // adjust as needed
        if (jobCards.isEmpty()) {
            return false;
        }
        for (WebElement job : jobCards) {
            String jobText = job.getText().toLowerCase();
            if (!jobText.contains(jobTitle.toLowerCase()) && !jobText.contains(city.toLowerCase())) {
                return false;
            }
        }

        return true;
    }

    public void sortBy(String optionText) {
        Select dropdown = new Select(sortBy);
        dropdown.selectByVisibleText(optionText);
    }

}
