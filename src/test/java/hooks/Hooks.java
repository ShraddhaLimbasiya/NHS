package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void setUp() {
        Base.getWebdriver("edge");  // initializes driver
    }

    @After
    public void tearDown() {
        Base.tearDown();  // quits driver
    }
}
