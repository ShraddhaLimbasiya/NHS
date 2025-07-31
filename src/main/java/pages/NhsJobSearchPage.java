
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


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

    @FindBy(id = "search-results-heading")
    private WebElement errorMessage;

    @FindBy(xpath = "//a[@class='nhsuk-pagination__link nhsuk-pagination__link--next']")
    private WebElement nextBtn;

    private List<WebElement> getJobCards() {
        return driver.findElements(By.cssSelector("li[data-test='search-result']"));
    }

    private By jobTitleSelector = By.cssSelector("a[data-test='search-result-job-title']");
    private By jobLocationSelector = By.cssSelector("div[data-test='search-result-location'] h3 > div");
    private By postedDateSelector = By.cssSelector("div[data-test='search-result-posted-date']");

    public void enterJobTitle(String title) {
        jobTitle.sendKeys(title);
    }

    public void enterLocation(String preferredLocation) {
        location.sendKeys(preferredLocation);

    }

    public void enterSearchData(String title, String preferredLocation) {
        enterJobTitle(title);
        enterLocation(preferredLocation);
    }

    private void selectByVisibleText(By selector, String text) {
        Select dropdown = new Select(driver.findElement(selector));
        dropdown.selectByVisibleText(text);
    }

    public void selectDistance(String distance) {
        selectByVisibleText(By.name("distance"), "+" + distance + " Miles");
    }


    public void clickSearchBtn() {
        searchBtn.click();
    }

    public List<WebElement> getJobTitlesFromResults() {
        List<WebElement> jobTitles = driver.findElements(By.cssSelector("a[data-test$='search-result-job-title']"));
        return jobTitles;
    }

    public boolean resultsMatchPreferredTitle(String expectedTitle) {
        if (getJobCards().isEmpty()) return false;

        for (WebElement job : getJobTitlesFromResults()) {
            if (job.getText().toLowerCase().contains(expectedTitle.toLowerCase())) {
                return true;
            }
        }
        return false;
    }


    public boolean resultsMatchPreferredLocation(String expectedLocation) {

        if (getJobCards().isEmpty()) {
            return false;
        }

        for (WebElement job : getJobCards()) {
            String actualLocation = job.findElement(By.cssSelector("div[data-test='search-result-location'] h3 > div")).getText().toLowerCase();
            String actualPostcode = actualLocation.substring(0, 2);

            if (actualLocation.contains(expectedLocation.toLowerCase())
                    | actualLocation.startsWith(actualPostcode.toLowerCase())) {
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

    public List<LocalDate> getPostedDates() {
        List<LocalDate> dates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");

        List<WebElement> dateElements = driver.findElements(By.cssSelector("li[data-test='search-result'] div[data-test='search-result-posted-date']"));

        for (WebElement dateElement : dateElements) {
            try {
                String text = dateElement.getText().replace("Posted", "").trim();
                LocalDate date = LocalDate.parse(text, formatter);
                dates.add(date);
            } catch (Exception e) {
                System.out.println("Date parsing failed for: " + dateElement.getText());
            }
        }

        return dates;
    }

    public boolean isSortedDescending(List<LocalDate> dates) {
        List<LocalDate> sorted = new ArrayList<>(dates);
        sorted.sort(Collections.reverseOrder());
        return dates.equals(sorted);
    }


    public boolean isErrorMessageDisplayed() {
        try {
            WebElement message = driver.findElement(By.xpath("//*[contains(text(), 'No result found for')]"));
            return message.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickNextBtn() {
        nextBtn.click();
    }

    public void clickOnJob(){
        getJobCards().get(0).click();
    }


}
