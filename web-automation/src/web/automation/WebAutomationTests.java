package web.automation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.Duration;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebAutomationTests {

	private static WebDriver driver;
	private static WebDriverWait wait;
	
	@BeforeClass
	public static void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/web/automation/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(4));
	}
	
	@Test
	public void authorizedPage_shouldRedirectUser_whenUserIsNotAuthorized() throws InterruptedException {
		Eternet eternet = PageFactory.initElements(driver, Eternet.class);
		eternet.loadPage();
		eternet.clearLocalStorage();
		eternet.clickElement("//span[.='Oficina Virtual']");
		eternet.clickElement("//a[@href='oficina-virtual/mis-servicios']");
		assertTrue(wait.until(ExpectedConditions.urlContains("unauthorized")));
	}
	
	@Test
	public void searchInput_shouldNavigateToSearchPage() throws InterruptedException {
		final String inputSearchXPath = "//*[@id=\"footer\"]/div[1]/div/div/div[2]/div[2]/div/form/div/input";
		Eternet eternet = PageFactory.initElements(driver, Eternet.class);
		
		eternet.loadPage();
		eternet.scrollTo(inputSearchXPath);
		eternet.writeInInput(inputSearchXPath, "internet");
		
		assertTrue(wait.until(ExpectedConditions.urlContains("buscar/internet")));
		assertNotNull(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[.='Resultados']"))));
	}
	
	@AfterClass
	public static void tearDown() {
		driver.quit();
	}
	
}
