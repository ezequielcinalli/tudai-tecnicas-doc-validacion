package web.automation;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Eternet {
    private final WebDriver driver;

    public Eternet(WebDriver driver) {
        this.driver = driver;
    }
    
    @Test
    public void loadPage(){
        driver.get("https://www.eternet.com.ar");
    }
    
    @Test
    public void clearLocalStorage(){
        ((ChromiumDriver) driver).getLocalStorage().clear();
    }
    
    @Test
    public void clickElement(String xPath){
        WebElement element = driver.findElement(By.xpath(xPath));
        element.click();
    }
    
    @Test
    public void scrollTo(String inputSearchXPath){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        boolean elementFoundOrBottom = false;
        long lastHeight = (long) js.executeScript("return document.body.scrollHeight");

        while (elementFoundOrBottom == false) {
            js.executeScript("window.scrollBy(0,700)", "");
            long newHeight = (long) js.executeScript("return document.body.scrollHeight");
            if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(inputSearchXPath))) != null || lastHeight == newHeight) {
                elementFoundOrBottom = true;
            }
        }
    }
    
    @Test
    public void writeInInput(String inputXPath, String text){
        WebElement encodedInput = driver.findElement(By.xpath(inputXPath));
        encodedInput.clear();
        encodedInput.sendKeys(text);
        encodedInput.sendKeys(Keys.ENTER);
        encodedInput.submit();
    }
    
}
