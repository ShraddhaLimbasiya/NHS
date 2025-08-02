package stepdefinitions;

import hooks.Base;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.NhsJobSearchPage;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class NhsJobSearchSteps extends Base{
    WebDriver driver = Base.getWebdriver("chrome");

    private String jobTitle;
    private String city;
    NhsJobSearchPage nhsJobSearchPage;

    @Given("I am on the NHS Jobs search page")
    public void iAmOnTheNHSJobsSearchPage() throws InterruptedException {
        nhsJobSearchPage = new NhsJobSearchPage(driver);
        Thread.sleep(1000);
        driver.get("https://www.jobs.nhs.uk/candidate/search");

    }

    @When("I enter preferences such as {string} in the Job Title and {string} in the Location")
    public void iEnterPreferencesSuchAsInTheJobTitleAndInTheLocation(String jobTitle, String location) {
        nhsJobSearchPage.enterSearchData(jobTitle, location);
        this.jobTitle = jobTitle;
        this.city = location;
    }

    @And("I enter the distance as {string} in the distance")
    public void iEnterTheDistanceAsInTheDistance(String distance) {
        nhsJobSearchPage.selectDistance(distance);

    }

    @And("I click the search button")
    public void iClickTheSearchButton() throws InterruptedException {
        Thread.sleep(1000);
        nhsJobSearchPage.clickSearchBtn();
    }

    @Then("I should see a list of job results that match my preferences")
    public void iShouldSeeAListOfJobResultsThatMatchMyPreferences() {

        if (jobTitle != null) {
            List<String> results = nhsJobSearchPage.getJobTitlesFromResults(jobTitle);
            assertFalse(results.isEmpty(), "No job results found");

            for (String title : results) {
                System.out.println("Checking title: " + title);
                assertTrue(title.toLowerCase().contains(jobTitle.toLowerCase()),
                        "Job title does not match preference: " + title);
            }
        } else if (city != null) {
            assertTrue(nhsJobSearchPage.resultsMatchPreferredLocation(city), "No location match");
        }
    }

    @And("I should be able to sort results by {string}")
    public void iShouldBeAbleToSortResultsBy(String option) {
        nhsJobSearchPage.sortBy(option);
    }


    @Then("the jobs should be sorted by newest Date Posted")
    public void theJobsShouldBeSortedByNewestDatePosted() {
        List<LocalDate> postedDates = nhsJobSearchPage.getPostedDates();
        assertTrue(nhsJobSearchPage.isSortedDescending(postedDates), "Job results are not sorted by newest date posted");

    }


    @When("I enter {string} in the Job Title field")
    public void iEnterInTheJobTitleField(String jobTitle) {
        nhsJobSearchPage.enterJobTitle(jobTitle);

    }

    @And("I leave the Location field empty")
    public void iLeaveTheLocationFieldEmpty() {
        nhsJobSearchPage.enterLocation("");
    }

    @Then("I should see a list of job results that include {string} in the title")
    public void iShouldSeeAListOfJobResultsThatIncludeInTheTitle(String jobTitle) {
        List<String> results = nhsJobSearchPage.getJobTitlesFromResults(jobTitle); // Now returns List<String>

        assertFalse(results.isEmpty(), "Job results list is empty");

        for (String title : results) {
            System.out.println(title);
            assertTrue(title.toLowerCase().contains(jobTitle.toLowerCase()),
                    "Job title does not contain expected text: " + jobTitle);
        }
    }


    @When("I enter {string} in the Location field")
    public void iEnterInTheLocationField(String location) throws InterruptedException {
        Thread.sleep(1000);
        nhsJobSearchPage.enterLocation(location);
    }


    @When("I enter special characters {string} in the Job title field")
    public void iEnterSpecialCharactersInTheJobTitleField(String specialCharacters) {
        nhsJobSearchPage.enterJobTitle(specialCharacters);
    }

    @Then("I should see a message indicating no results were found")
    public void iShouldSeeAMessageIndicatingNoResultsWereFound() {
        assertTrue(nhsJobSearchPage.isErrorMessageDisplayed(), "Expected 'no results' message was not displayed.");

    }

    @When("I enter non existent title as {string} in the Job title field")
    public void iEnterNonExistentTitleAsInTheJobTitleField(String nonExistentTitle) {
        nhsJobSearchPage.enterJobTitle(nonExistentTitle);
    }

    @When("I click to go to the second page")
    public void iClickToGoToTheSecondPage() {
        nhsJobSearchPage.clickNextBtn();

    }

    @And("I click on a job in the result list")
    public void iClickOnAJobInTheResultList() {
        nhsJobSearchPage.clickOnJob();
    }

    @Then("I should be taken to the job detail page")
    public void iShouldBeTakenToTheJobDetailPage() {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("job") || currentUrl.contains("vacancy"), "Not navigated to job detail page.");
        assertTrue(nhsJobSearchPage.isJobDetailVisible(), "Expected job detail section not visible.");

    }

    @And("I press the browser back button")
    public void iPressTheBrowserBackButton() {
        driver.navigate().back();

    }

    @Then("I should return to the same search results")
    public void iShouldReturnToTheSameSearchResults() {
        assertTrue(driver.getCurrentUrl().contains("search"), "Not returned to search results page.");
        //assertFalse(nhsJobSearchPage.getJobTitlesFromResults().isEmpty(), "Search results not visible after navigating back.");
    }

    @When("I enter {string} repeated {int} times in the Job title field")
    public void iEnterRepeatedTimesInTheJobTitleField(String jobTitle, int number) {
        String longTitle = jobTitle.repeat(number).trim();
        nhsJobSearchPage.enterJobTitle(longTitle);


    }

    @Then("I should see job results filtered by the selected working patterns")
    public void iShouldSeeJobResultsFilteredByTheSelectedWorkingPatterns() {


    }

    @And("I click the Apply filters button")
    public void iClickTheApplyFiltersButton() throws InterruptedException {
        Thread.sleep(1000);
        nhsJobSearchPage.iClickTheApplyFiltersButton();
    }


    @When("I click the Clear filters button")
    public void iClickTheClearFiltersButton() {
        nhsJobSearchPage.iClickTheClearFiltersButton();
    }

    @And("I select {string}:")
    public void iSelectUnder(String category, DataTable table) throws InterruptedException {
        Thread.sleep(4000);

        List<String> label = table.asList();

        //nhsJobSearchPage.selectCheckBox(category);
        nhsJobSearchPage.selectCheckBox(category,label.toArray(new String[0]));

       // nhsJobSearchPage.selectCheckBox(category, "Full time", "Job-share");

    }

    @Then("I should see a list of job results that match my {string}")
    public void iShouldSeeAListOfJobResultsThatMatchMyWokingPatterns(String label) {
        Map<String, List<String>> filters = nhsJobSearchPage.getSelectedFilters();
        List<String> expectedValues = filters.get(label);
        List<String> actualValues = nhsJobSearchPage.getJobDetailsByLabel(label);

        for (String actual : actualValues) {
            boolean match = expectedValues.stream().anyMatch(
                    expected -> actual.equalsIgnoreCase(expected));
            Assert.assertTrue(match,"Unexpected value found: " );
        }
    }
}