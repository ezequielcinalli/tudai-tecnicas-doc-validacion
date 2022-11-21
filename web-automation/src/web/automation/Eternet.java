package web.automation;

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
    
    public void loadPage(){
        driver.get("https://www.eternet.com.ar");
    }
    
    public void clearLocalStorage(){
        ((ChromiumDriver) driver).getLocalStorage().clear();
    }
    
    public void clickElement(String xPath){
        WebElement element = driver.findElement(By.xpath(xPath));
        element.click();
    }
    
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
    
    public void writeAndEnter(String inputXPath, String text){
        WebElement element = driver.findElement(By.xpath(inputXPath));
        element.clear();
        element.sendKeys(text);
        element.sendKeys(Keys.ENTER);
    }
    
}
