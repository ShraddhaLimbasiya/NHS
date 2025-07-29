package hooks;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;

public class Base {

    private static WebDriver driver;

    public static WebDriver getWebdriver() {
        if (driver == null) {

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
