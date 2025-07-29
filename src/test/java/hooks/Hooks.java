package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void setUp() {
        Base.getWebdriver();  // initializes driver
    }

    @After
    public void tearDown() {
        Base.getWebdriver();  // quits driver
    }
}
