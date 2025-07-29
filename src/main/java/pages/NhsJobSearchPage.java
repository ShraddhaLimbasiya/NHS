package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class NhsJobSearchPage {

    WebDriver driver;

    public NhsJobSearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "keyword")
    private WebElement jobTitle;

    @FindBy(name = "location")
    private WebElement location;

    @FindBy(id = "search")
    private WebElement searchBtn;


    public void enterSearchData(String title, String preferedLocation) {
        jobTitle.sendKeys(title);
        location.sendKeys(preferedLocation);
    }

    public void clickSearchBtn() {
        searchBtn.click();
    }

    public boolean resultsMatchPreferences(String expectedTitle, String expectedLocation) {
        List<WebElement> jobCards = driver.findElements(By.cssSelector("li[data-test='search-result']"));

        if (jobCards.isEmpty()) {
            return false;
        }

        for (WebElement job : jobCards) {
            String actualTitle = job.findElement(By.cssSelector("a[data-test='search-result-job-title']")).getText().toLowerCase();
            String actualLocation = job.findElement(By.cssSelector("div[data-test='search-result-location'] h3 > div")).getText().toLowerCase();

            if (actualTitle.contains(expectedTitle.toLowerCase()) && actualLocation.contains(expectedLocation.toLowerCase())) {
                return true;
            }
        }

        return false;
    }



    public void sortBy(String optionText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By sortDropdownBy = By.cssSelector("select[name='sort']");
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(sortDropdownBy));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);

        Select select = new Select(dropdown);
        select.selectByVisibleText(optionText);
    }


}
