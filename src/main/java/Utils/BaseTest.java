package Utils;

import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeClass;
import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import com.google.common.io.Files;

public class BaseTest {
	public WebDriver driver;
	public BasePage app;
	
	
	@BeforeClass
	public void setup() {
		ChromeOptions chopt = new ChromeOptions();
		chopt.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(chopt);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://keybooks.ro/");
		
		app = new BasePage();
	}
	
	
	
	@AfterClass
	public void tearDown() throws InterruptedException{
		Thread.sleep(5000);
		driver.quit();
	}
	
	@AfterMethod
	public void recordFailure(ITestResult result) throws IOException {
		if(ITestResult.FAILURE == result.getStatus()) {
			TakesScreenshot poza = (TakesScreenshot)driver;
			File screenshot = poza.getScreenshotAs(OutputType.FILE);
			String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			Files.copy(screenshot, new File("poze/" + result.getName() + timestamp +  "screenshot.png"));
		}
	}
}