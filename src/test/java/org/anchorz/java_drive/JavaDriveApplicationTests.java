package org.anchorz.java_drive;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JavaDriveApplicationTests {

	protected static WebDriver driver;
	@LocalServerPort
	private int port;

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
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPageTests(driver);
		registerPage = new RegisterPageTests(driver);
	}
//	Login Tests
	@Test
	public void testLogin() {
		loginPage.login("testuser", "password");
	}
//	SignUp Tests
	@Test
	public void testSignUpExistedUser() {
		registerPage.signUp("testuser", "password", "test", "user", "password");
	}
	@Test
	public void testSignUpSuccess() {
		registerPage.signUp("testuser2", "password", "test", "user", "password");
	}
	@Test
	public void testSignUpNotMatchPassword() {
		registerPage.signUp("testuser2", "password", "test", "user", "password2");
	}


	@Test
	void contextLoads() {
	}

}
