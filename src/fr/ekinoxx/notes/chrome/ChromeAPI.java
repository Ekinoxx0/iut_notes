package fr.ekinoxx.notes.chrome;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.rapidoid.u.U;

public class ChromeAPI {

	private static ChromeOptions options = new ChromeOptions()
			.addArguments("--headless")
			.addArguments("--no-sandbox")
			.addArguments("--disable-dev-shm-usage")
			.addArguments("--whitelisted-ips=\"127.0.0.1\"");
	
	static {
		System.setProperty("webdriver.chrome.driver", "ChromeDriver");
	}

	public static ChromeDriver setup(String username, String password) throws IllegalAccessException {
		ChromeDriver driver = new ChromeDriver(options);
		
		driver.navigate().to("https://notes.info.iut-tlse3.fr/visuNotes.php");
		int numberOfTry = 0;
		try {
			while (!driver.findElements(By.cssSelector("h1")).isEmpty() &&
					!driver.findElement(By.cssSelector("h1")).getText().contains("tudiants")) {
				numberOfTry++;

				if (numberOfTry > 20) {
					throw new IllegalAccessException("Never finded first title page...");
				}

				U.sleep(50);
			}
		} catch (IllegalAccessException e) {
			throw e;
		} catch (Exception e) {
			driver.close();
			e.printStackTrace();
			return null;
		}

		WebElement identifiant = driver.findElement(By.name("identifiant"));
		identifiant.sendKeys(username);

		WebElement pass = driver.findElement(By.name("pass"));
		pass.sendKeys(password);

		pass.sendKeys(Keys.ENTER);

		numberOfTry = 0;
		try {
			while (!driver.findElements(By.cssSelector("a")).isEmpty() &&
					!driver.findElement(By.cssSelector("a")).getText().contains("connecter")) {
				numberOfTry++;

				if (numberOfTry > 20) {
					throw new IllegalAccessException("Never finded panel page, probably invalid user.");
				}

				U.sleep(50);
			}
		} catch (IllegalAccessException e) {
			throw e;
		} catch (Exception e) {
			driver.close();
			e.printStackTrace();
			return null;
		}

		return driver;
	}

}
