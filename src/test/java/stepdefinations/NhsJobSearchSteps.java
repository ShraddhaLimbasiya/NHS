package stepdefinations;

import hooks.Base;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.NhsJobSearchPage;

public class NhsJobSearchSteps extends Base {
    WebDriver driver;
    NhsJobSearchPage nhsJobSearchPage;

    @Given("I am on the NHS Jobs search page")
    public void iAmOnTheNHSJobsSearchPage() {
        driver.get("https://www.jobs.nhs.uk/candidate/search");
    }

    @When("I enter preferences such as {string} in the Job Title and {string} in the Location")
    public void iEnterPreferencesSuchAsInTheJobTitleAndInTheLocation(String jobTitle, String location) {
        nhsJobSearchPage.enterSearchData(jobTitle,location);
    }

    @And("I click the search button")
    public void iClickTheSearchButton() {
        nhsJobSearchPage.clickSearchBtn();
    }

    @Then("I should see a list of job results that match my preferences")
    public void iShouldSeeAListOfJobResultsThatMatchMyPreferences() {
       // assert nhsJobSearchPage.resultsMatchPreferences(jobTitle, city);

    }

    @And("I should be able to sort results by {string}")
    public void iShouldBeAbleToSortResultsBy(String sortBy) {
        nhsJobSearchPage.sortBy(sortBy);
    }
}
