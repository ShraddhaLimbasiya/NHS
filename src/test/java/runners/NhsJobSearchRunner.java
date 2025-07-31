package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/NhsJobSearch.feature",
        glue = {"stepdefinitions", "hooks/Hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber.json"
        },
        //tags = "@only" ,
        monochrome = true
)
public class NhsJobSearchRunner extends AbstractTestNGCucumberTests {

}
