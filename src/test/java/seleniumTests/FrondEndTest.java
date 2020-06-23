package seleniumTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;


//Test cases in this class should be executed once the server is running
public class FrondEndTest {
    //Set this to the directory of the location of your geckodriver + firefox should be installed on your system
    static String dir = "C:/WebDriver/bin/geckodriver.exe";
    static WebDriver driver;

    /**
     * @requires There is an existing user account with username of 'user1' and password '123456'
     */
    @BeforeAll
    static public void setup(){
        System.setProperty("webdriver.gecko.driver", dir);
        driver = new FirefoxDriver();
        driver.get("http://localhost:8080/CentroGeo/login/log_in.html");
        //Login and obtain correct cookies
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("userpassword"));
        usernameInput.sendKeys("user1");
        passwordInput.sendKeys("123456" + Keys.ENTER);
        //Wait for the homepage to load
        new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.id("editMetadataButton")));
        //Wait some time for the page to load fully
    }

    @BeforeEach
    public void preTest(){
        driver.navigate().refresh();
    }
    /**
     * Test whether the simulation has been edited and changed in the database.
     * @requires There is an existing simulation in the database with an id of 1
     */
    @Test
    public void testMetadataEditAndSelect(){
        driver.findElement(By.id("editMetadataButton")).click();
        driver.findElement(By.id("title")).sendKeys("11");
        driver.findElement(By.id("editor")).sendKeys("New Simulation Name");
        WebElement dateBox = driver.findElement(By.id("date"));
        dateBox.sendKeys("2020-12-23");
                driver.findElement(By.id("description")).sendKeys("New Simulation Description");
        WebElement addTagbtn = driver.findElement(By.id("addTagBtn"));
        addTagbtn.click();
        driver.findElement(By.id("newTag")).sendKeys("amazing tag");
        driver.findElement(By.id("add-new-tag")).click();
        Alert tagAlert = new WebDriverWait(driver, 2).until(ExpectedConditions.alertIsPresent());
        tagAlert.accept();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.id("closeTags")).click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.id("submitMetadata")));
        driver.findElement(By.id("submitMetadata")).click();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Alert alert = new WebDriverWait(driver, 2).until(ExpectedConditions.alertIsPresent());
        alert.accept();
        driver.navigate().refresh();
        WebElement selectSim = new WebDriverWait(driver, 4).until(ExpectedConditions.elementToBeClickable(By.id("selectSimulationButton")));
        selectSim.click();
        driver.findElement(By.id("simulation-filter")).sendKeys("amazing tag");
        driver.findElement(By.xpath("//span[./text() = \"amazing tag\"]"));
        assertTrue(true);
    }

    /**
     * Deletes simulation with a tag of "amazing tag" the previous test gives it that name
     */
    @Test
    public void deleteSimulation(){
        WebElement selectSim = new WebDriverWait(driver, 4).until(ExpectedConditions.elementToBeClickable(By.id("deleteSelectedSimulation")));
        driver.findElement(By.id("selectSimulationButton")).click();
        driver.findElement(By.id("simulation-filter")).sendKeys("amazing tag");
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.id("simulation-container")).click();
        driver.findElement(By.id("closeSelectSimulationMenu")).click();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement deleteSim = new WebDriverWait(driver, 4).until(ExpectedConditions.elementToBeClickable(By.id("deleteSelectedSimulation")));
        deleteSim.click();
        WebElement but = new WebDriverWait(driver, 4).until(ExpectedConditions.elementToBeClickable(By.id("delSim")));
        but.click();
    }


    /**
     * Close the browser
     */
    @AfterAll
    static public void finalise(){
        driver.quit();
    }






}
