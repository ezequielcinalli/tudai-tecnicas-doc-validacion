package web.automation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebAutomationTests {

	private WebDriver driver;
	private WebDriverWait wait;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/seleniumexample/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(4));
	}
	
	public void testGooglePage() throws InterruptedException {
		driver.get("https://www.google.com");
		WebElement searchbox = driver.findElement(By.name("q"));
		searchbox.clear();
		searchbox.sendKeys("Youtube");
		searchbox.submit();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
		
		assertEquals("Youtube - Buscar con Google", driver.getTitle());
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
	
	@After
	public void tearDown() {
		driver.quit();
	}
	
}
