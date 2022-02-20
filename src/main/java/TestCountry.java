
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestCountry {

    private final ChromeDriver driver;

    public TestCountry(){
        // Manual load of a driver, check if version is compatible
        System.setProperty("webdriver.chrome.driver","webDrivers/Chrome/chromedriver.exe");
        driver = new ChromeDriver();
    }

    public void runTest(){
        // Navigate to website
        driver.get("https://www.westernunion.com/lt/en/home.html");
        driver.manage().window().maximize();

        dealWithCookies();
        changeCountryToUSA();

        try {
            // This causes test to wait until sign up appears
            removeSignUpOverlay();
        }
        catch (Exception e){
            // If sign up overlay does not show up, log into console
            System.out.println("Sign up overlay did not show up");
        }

        openUsaSettings();

        String result = driver.findElement(By.cssSelector("#Question")).getAttribute("value");

        assert !result.equals("object:252") : "Country was not switched to USA";
        System.out.println("TEST PASSED");

        driver.quit();

    }

    private void dealWithCookies(){
        // Give it 10 seconds to load cookies in case internet is slower
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));

        // Accepting cookies
        driver.manage().deleteAllCookies();
        driver.findElement(By.id("onetrust-accept-btn-handler")).click();
    }

    private void changeCountryToUSA(){
        // Open burger menu
        driver.findElement(By.id("menuicon")).click();

        // Open settings
        driver.findElement(By.cssSelector("li[amplitude-id='menu-settings']")).click();

        // Open country list
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#Question")));
        driver.findElement(By.cssSelector("#Question")).click();

        // Select United States
        driver.findElement(By.cssSelector("option[value='object:252']")).click();

        // Confirm selection
        driver.findElement(By.cssSelector("button[ng-click='redirect();'] translate[class='ng-scope']")).click();
    }

    private void removeSignUpOverlay(){
        // Wait until sign up overlay shows up, can take a while then close it
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("signupOverlayIframe")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#closeOverlay")));
        driver.findElement(By.cssSelector("#closeOverlay")).click();
    }

    private void openUsaSettings(){
        // "Hover" over burger menu
        Actions action = new Actions(driver);
        WebElement burgerMenu = driver.findElement(By.cssSelector(".wu-icon.wu-icon-heading__xl.icon-0124_menu.action-light.icon-attached"));
        action.moveToElement(burgerMenu).build().perform();

        // Find settings
        WebElement settingsUSA = driver.findElement(By.linkText("Settings"));

        // Settings seem to not be clickable, extracting href instead and just opening it
        String settingsLink = settingsUSA.getAttribute("href");
        driver.get(settingsLink);
    }
}
