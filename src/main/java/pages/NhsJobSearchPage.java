
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
import java.util.*;


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

    public List<String> getJobTitlesFromResults(String keyword) {
        List<WebElement> jobTitleElements = driver.findElements(By.cssSelector("a[data-test$='search-result-job-title']"));
        List<String> jobTitles = new ArrayList<>();

        for (WebElement element : jobTitleElements) {
            String title = element.getText();
            jobTitles.add(title);

            if (title.toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("Match found: " + title);
            }
        }

        System.out.println("All Job Titles:");
        for (String title : jobTitles) {
            System.out.println(title);
        }

        return jobTitles;
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

    public void clickOnJob() {
        getJobCards().get(0).click();
    }

    public boolean isJobDetailVisible() {
        return driver.findElement(By.cssSelector("div[data-test='job-detail']")) != null;
    }

    //span[normalize-space()='Contract type']

    public void selectCategory(String categoryName) {
        WebElement category = driver.findElement(By.xpath("//span[normalize-space()='" + categoryName + "']"));
        category.click();


    }

    public List<WebElement> getCheckboxesByCategory(String categoryName) {
        String xpath = String.format("//input[@type='checkbox' and @name='%s']", categoryName);
        return driver.findElements(By.xpath(xpath));
    }

    private Map<String, List<String>> selectedFilters = new HashMap<>();

    public Map<String, List<String>> getSelectedFilters() {
        return selectedFilters;
    }

    public void selectCheckBox(String categoryName, String... valuesToSelect) {
        selectCategory(categoryName);
        selectedFilters.put(categoryName, Arrays.asList(valuesToSelect));

        for (String value : valuesToSelect) {
            boolean found = false;

            String labelXpath = String.format("//details[.//span[normalize-space()='%s']]//label", categoryName);
            List<WebElement> labels = driver.findElements(By.xpath(labelXpath));

            for (WebElement label : labels) {
                String labelText = label.getText().trim();

                if (labelText.equalsIgnoreCase(value.trim())) {
                    String forAttr = label.getAttribute("for");

                    WebElement checkbox = driver.findElement(By.id(forAttr));
                    if (!checkbox.isSelected()) {
                        checkbox.click();
                        System.out.println("Selected: " + categoryName + " → " + labelText);
                    } else {
                        System.out.println("Already selected: " + labelText);
                    }

                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Value not found: " + value + " in category " + categoryName);
            }
        }
    }


    public void iClickTheApplyFiltersButton() {
        WebElement applyFilterBtn = driver.findElement(By.id("refine-search"));
        applyFilterBtn.click();
    }

    public void iClickTheClearFiltersButton() {
        WebElement clearFilterBtn = driver.findElement(By.xpath("//a[normalize-space()='Clear filters']"));
        clearFilterBtn.click();
        selectedFilters.clear();

    }


    public List<String> getWorkingTypes(List<String> keywords) {
        List<String> workingType = new ArrayList<>();
        List<WebElement> workingTypes = driver.findElements(
                By.xpath("//li[@data-test='search-result-workingPattern']")
        );

        for (WebElement element : workingTypes) {
            try {
                WebElement strongTag = element.findElement(By.tagName("strong"));
                String workingText = strongTag.getText().trim();

                for (String keyword : keywords) {
                    if (workingText.toLowerCase().contains(keyword.toLowerCase())) {
                        System.out.println("Match found for '" + keyword + "': " + workingText);
                        workingType.add(workingText);
                        break;
                    }
                }

            } catch (NoSuchElementException e) {
            }
        }

        return workingType;
    }

    public List<String> getSelectedFilterValue(String filterName, List<String> keywords) {
        List<String> matchedValues = new ArrayList<>();
        List<WebElement> filterElements;
        String xpath;

        switch (filterName.toLowerCase()) {
            case "pay range":
                xpath = "//li[@data-test='search-result-salary']";
                break;
            case "working pattern":
                xpath = "//li[@data-test='search-result-workingPattern']";
                break;
            case "contract type":
                xpath = "//li[@data-test='search-result-jobType']";
                break;
            default:
                System.out.println("Unknown filter name: " + filterName);
                return matchedValues;
        }

        filterElements = driver.findElements(By.xpath(xpath));

        for (WebElement element : filterElements) {
            try {
                WebElement strongTag = element.findElement(By.tagName("strong"));
                String text = strongTag.getText().trim();

                for (String keyword : keywords) {
                    if (text.toLowerCase().contains(keyword.toLowerCase())) {
                        System.out.println("Match found for '" + keyword + "': " + text);
                        matchedValues.add(text);
                        break;
                    }
                }
            } catch (NoSuchElementException e) {
            }
        }

        return matchedValues;
    }
    public boolean areAnyFiltersVisible() {
        List<WebElement> activeFilters = driver.findElements(By.cssSelector(".nhsuk-tag")); // or whatever tag shows active filters
        return !activeFilters.isEmpty();
    }



}
