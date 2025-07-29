package stepdefinitions;

import hooks.Base;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.NhsJobSearchPage;

import java.time.LocalDate;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class NhsJobSearchSteps {
    WebDriver driver = Base.getWebdriver();

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

    @And("I click the search button")
    public void iClickTheSearchButton() {
        nhsJobSearchPage.clickSearchBtn();
    }

    @Then("I should see a list of job results that match my preferences")
    public void iShouldSeeAListOfJobResultsThatMatchMyPreferences() {
        assertTrue(nhsJobSearchPage.resultsMatchPreferences(jobTitle, city), "No job result matches expected title and location.");

    }

    @Then("I should be able to sort results by {string}")
    public void iShouldBeAbleToSortResultsBy(String option) {
        nhsJobSearchPage.sortBy(option);
    }


    @Then("the jobs should be sorted by newest Date Posted")
    public void theJobsShouldBeSortedByNewestDatePosted() {
        List<LocalDate> postedDates = nhsJobSearchPage.getPostedDates();
        assertTrue(nhsJobSearchPage.isSortedDescending(postedDates), "Job results are not sorted by newest date posted");

    }
}
