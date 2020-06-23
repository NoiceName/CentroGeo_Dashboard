package seleniumTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

//Test cases in this class should be executed once the server is running
public class FrondEndTest {
    //Set this to the directory of the location of your geckodriver + firefox should be installed on your system
    static String dir = "C:/WebDriver/bin/geckodriver.exe";
    static WebDriver driver;

    @BeforeAll
    static public void setup(){
        System.setProperty("webdriver.gecko.driver", dir);
        driver = new FirefoxDriver();
        driver.get("http://localhost:8080/CentroGeo/login/log_in.html");
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("userpassword"));
        usernameInput.sendKeys("user1");
        passwordInput.sendKeys("123456" + Keys.ENTER);
    }

    @Test
    public void loginTest(){

    }

    @AfterAll
    static public void finalise(){
        driver.quit();
    }






}
