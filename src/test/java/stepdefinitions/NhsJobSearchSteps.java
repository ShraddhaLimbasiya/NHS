package stepdefinitions;

import hooks.Base;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.NhsJobSearchPage;

import java.time.LocalDate;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class NhsJobSearchSteps {
    WebDriver driver = Base.getWebdriver("edge");

    private String jobTitle;
    private String city;
    NhsJobSearchPage nhsJobSearchPage;

    @Given("I am on the NHS Jobs search page")
    public void iAmOnTheNHSJobsSearchPage() {
        nhsJobSearchPage = new NhsJobSearchPage(driver);
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
    public void iClickTheSearchButton() {
        nhsJobSearchPage.clickSearchBtn();
    }

    @Then("I should see a list of job results that match my preferences")
    public void iShouldSeeAListOfJobResultsThatMatchMyPreferences() {
        assertTrue(nhsJobSearchPage.resultsMatchPreferences(jobTitle, city), "No job result matches expected title and location.");

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
        List<String> results = nhsJobSearchPage.getJobTitlesFromResults();
        assertFalse(results.isEmpty(), "Job results list is empty");
        System.out.println("Job titles found: " + results.size());

        for (String resultTitle : results) {
            System.out.println(resultTitle);
            assertTrue(resultTitle.toLowerCase().contains(jobTitle.toLowerCase()), "Job title does not contain expected text");
        }
    }

    @When("I enter {string} in the Location field")
    public void iEnterInTheLocationField(String location) {
        nhsJobSearchPage.enterLocation(location);
    }


    @When("I enter {string} in the Jobtitle field")
    public void iEnterInTheJobtitleField(String title) {
        nhsJobSearchPage.enterJobTitle(title);
    }

    @Then("I should see a message indicating no results were found")
    public void iShouldSeeAMessageIndicatingNoResultsWereFound() {
        nhsJobSearchPage.isErrorMessageDisplayed();

    }
}