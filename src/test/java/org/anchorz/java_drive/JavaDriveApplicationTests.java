package org.anchorz.java_drive;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JavaDriveApplicationTests {

	protected static WebDriver driver;
	@LocalServerPort
	private int port;
	private final String baseUrl = "http://localhost:";
	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}
	private LoginPageTests loginPage;
	private RegisterPageTests registerPage;
	@AfterAll
	public static void afterAll() {
		driver.quit();
	}
	@BeforeEach
	public void beforeEach() {
		driver.get(baseUrl + port + "/login");
		loginPage = new LoginPageTests(driver);
		registerPage = new RegisterPageTests(driver);
	}
//	Login Tests
	@Test
	public void testLoginFail() {
		loginPage.login("testuser", "password5");
		String expectedUrl = this.baseUrl + port + "/home";
		String actualUrl = driver.getCurrentUrl();
		Assertions.assertNotEquals(expectedUrl, actualUrl);
	}
//	Login Tests
	@Test
	public void testLoginSuccess() {
		loginPage.login("testuser", "password");
		String expectedUrl = this.baseUrl + port + "/home";
		String actualUrl = driver.getCurrentUrl();
		Assertions.assertEquals(expectedUrl, actualUrl);
	}
//	SignUp Tests
	@Test
	public void testSignUpExistedUser() {
		driver.get(baseUrl + port + "/signup");
		registerPage.signUp("testuser", "password", "test", "user", "password");
		String expectedUrl = this.baseUrl + port + "/home";
		String actualUrl = driver.getCurrentUrl();
		Assertions.assertNotEquals(expectedUrl, actualUrl);
	}
	@Test
	public void testSignUpSuccess() {
		driver.get(baseUrl + port + "/signup");
		String username = "testuser2";
		String password = "password";
		registerPage.signUp(username, password, "test", "user", password);
		loginPage.login(username, password);
		String homeUrl = this.baseUrl + port + "/home";
		String actualUrl = driver.getCurrentUrl();
		Assertions.assertEquals(homeUrl, actualUrl);
		// test logout
		registerPage.logout();
		actualUrl = driver.getCurrentUrl();
		Assertions.assertNotEquals(homeUrl, actualUrl);
		driver.get(baseUrl + port + "/home");
		actualUrl = driver.getCurrentUrl();
		Assertions.assertNotEquals(homeUrl, actualUrl);

	}
	@Test
	public void testSignUpNotMatchPassword() {
		driver.get(baseUrl + port + "/signup");
		registerPage.signUp("testuser2", "password", "test", "user", "password2");
		String expectedUrl = this.baseUrl + port + "/home";
		String actualUrl = driver.getCurrentUrl();
		Assertions.assertNotEquals(expectedUrl, actualUrl);
	}


	@Test
	void contextLoads() {
	}

}
