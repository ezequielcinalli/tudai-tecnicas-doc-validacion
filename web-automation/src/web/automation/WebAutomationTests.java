package web.automation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebAutomationTests {

	private WebDriver driver;
	private WebDriverWait wait;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/web/automation/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(4));
	}
	
	@Test
	public void authorizedPage_shouldRedirectUser_whenUserIsNotAuthorized() throws InterruptedException {
		driver.get("https://www.eternet.com.ar");
		((ChromiumDriver) driver).getLocalStorage().clear();
		
		WebElement virtualOffice = driver.findElement(By.xpath("//span[.='Oficina Virtual']"));
		virtualOffice.click();
		WebElement myServices = driver.findElement(By.xpath("//a[@href='oficina-virtual/mis-servicios']"));
		myServices.click();
		
		assertTrue(wait.until(ExpectedConditions.urlContains("unauthorized")));
	}
	
	@Test
	public void searchInput_shouldNavigateToSearchPage() throws InterruptedException {
		final String inputSearchXPath = "//*[@id=\"footer\"]/div[1]/div/div/div[2]/div[2]/div/form/div/input";
		driver.get("https://www.eternet.com.ar");
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
		WebElement encodedInput = driver.findElement(By.xpath(inputSearchXPath));
		encodedInput.clear();
		encodedInput.sendKeys("internet");
		encodedInput.sendKeys(Keys.ENTER);
		encodedInput.submit();
		
		assertTrue(wait.until(ExpectedConditions.urlContains("buscar/internet")));
		assertNotNull(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[.='Resultados']"))));
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}
	
}
