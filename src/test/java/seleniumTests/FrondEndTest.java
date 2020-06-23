package seleniumTests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FrondEndTest {
    //Set this to the directory of the location of your geckodriver + firefox should be installed on your system
    String dir = "C:/WebDriver/bin/geckodriver.exe";

    @Test
    public void testSimulationDelete(){
        System.setProperty("webdriver.gecko.driver", dir);
        WebDriver driver = new FirefoxDriver();
    }



}
