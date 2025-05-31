import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Login_Page {
WebDriver driver;
@BeforeTest
public void site() {
	WebDriverManager.chromedriver().setup();
	driver=new ChromeDriver();
}
@BeforeMethod
public void url() {
	driver.navigate().to("https://www.saucedemo.com/v1/index.html");
	driver.manage().window().maximize();
	//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	}
@Test
public void testvalidfield() {
	driver.findElement(By.xpath("//*[@id=\"user-name\"]")).sendKeys("standard_user");
	driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(" secret_sauce");
	driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
	String exp1 = "https://www.saucedemo.com/v1/inventory.html";
	String act1 = driver.getCurrentUrl();
	Assert.assertEquals(act1,exp1);
	
}
@Test
public void testinvalidpassword() {
	driver.findElement(By.xpath("//*[@id=\"user-name\"]")).sendKeys("standard_user");
	driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("sauce");
	driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
	String expmessage="Epic sadface: Username and password do not match any user in this service";
	String actmessage = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/h3")).getText();
	Assert.assertEquals(actmessage,expmessage);
	}
@Test
public void emptyfields() throws IOException {
	driver.findElement(By.xpath("//*[@id=\"user-name\"]")).sendKeys("");
	driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("");
	driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
	String expmessage="Epic sadface: Username is required";
	String actmessage = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/h3")).getText();
	Assert.assertEquals(actmessage,expmessage);
	File Screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	  FileUtils.copyFile(Screenshot,new File(".//Screenshot//Swag.png"));
	}
@Test
public void veryfyproductcount() {
	driver.findElement(By.xpath("//*[@id=\"user-name\"]")).sendKeys("standard_user");
	driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(" secret_sauce");
	driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
	
	List<WebElement> products = driver.findElements(By.className("inventory_item"));
	 Assert.assertEquals(products.size(), 6, "Product count should be 6");

 
     for (WebElement product : products) {
         
         Assert.assertTrue(product.findElement(By.className("inventory_item_name")).isDisplayed(), "Name missing");

      
         Assert.assertTrue(product.findElement(By.className("inventory_item_price")).isDisplayed(), "Price missing");

         
         Assert.assertTrue(product.findElement(By.tagName("img")).isDisplayed(), "Image missing");
     }
     }
@Test
public void addtocart() {
	driver.findElement(By.xpath("//*[@id=\"user-name\"]")).sendKeys("standard_user");
	driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(" secret_sauce");
	driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"inventory_container\"]/div/div[3]/div[3]/button")).click();
	
}
@Test
public void removeaddtocart() {
	driver.findElement(By.xpath("//*[@id=\"user-name\"]")).sendKeys("standard_user");
	driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(" secret_sauce");
	driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"inventory_container\"]/div/div[3]/div[3]/button")).click();
	driver.findElement(By.xpath("//*[@id=\"inventory_container\"]/div/div[1]/div[3]/button")).click();
}
}

