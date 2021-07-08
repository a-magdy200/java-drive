package org.anchorz.java_drive;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
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
	private NotesTests notesTests;
	private CredentialsTests credentialsTests;
	private final String testUsername = "username";
	private final String testPassword = "password";
	@AfterAll
	public static void afterAll() {
		driver.quit();
	}
	@BeforeEach
	public void beforeEach() {
		driver.get(baseUrl + port + "/login");
		loginPage = new LoginPageTests(driver);
		registerPage = new RegisterPageTests(driver);
		notesTests = new NotesTests(driver);
		credentialsTests = new CredentialsTests(driver);
	}
//	Login Tests
	@Test
	public void testLoginFail() {
		loginPage.login(testUsername, "password5");
		String expectedUrl = this.baseUrl + port + "/home";
		String actualUrl = driver.getCurrentUrl();
		Assertions.assertNotEquals(expectedUrl, actualUrl);
	}
//	@Test
//	public void testLoginSuccess() {
//		loginPage.login(testUsername, testPassword);
//		String expectedUrl = this.baseUrl + port + "/home";
//		String actualUrl = driver.getCurrentUrl();
//		Assertions.assertEquals(expectedUrl, actualUrl);
//	}
//	SignUp Tests
	@Test
	public void testSignUpExistedUser() {
		driver.get(baseUrl + port + "/signup");
		registerPage.signUp(testUsername, testPassword, "test", "user", testPassword);
		String expectedUrl = this.baseUrl + port + "/home";
		String actualUrl = driver.getCurrentUrl();
		Assertions.assertNotEquals(expectedUrl, actualUrl);
	}
	@Test
	public void testHomeNotAccessible() {
		String homeUrl = this.baseUrl + port + "/home";
		driver.get(homeUrl);
		String actualUrl = driver.getCurrentUrl();
		Assertions.assertNotEquals(homeUrl, actualUrl);
	}
	@Test
	public void testSignUpSuccess() {
		driver.get(baseUrl + port + "/signup");
		String username = "testuser2";
		String password = testPassword;
		registerPage.signUp(username, password, "test", "user", password);
		loginPage.login(username, password);
		String homeUrl = this.baseUrl + port + "/home";
		String actualUrl = driver.getCurrentUrl();
		Assertions.assertEquals(homeUrl, actualUrl);
		// test logout
		registerPage.logout();
		actualUrl = driver.getCurrentUrl();
		Assertions.assertNotEquals(homeUrl, actualUrl);
		driver.get(homeUrl);
		actualUrl = driver.getCurrentUrl();
		Assertions.assertNotEquals(homeUrl, actualUrl);
	}
	@Test
	public void testSignUpNotMatchPassword() {
		driver.get(baseUrl + port + "/signup");
		registerPage.signUp("testuser2", testPassword, "test", "user", "password2");
		String expectedUrl = this.baseUrl + port + "/home";
		String actualUrl = driver.getCurrentUrl();
		Assertions.assertNotEquals(expectedUrl, actualUrl);
	}
	// Notes Tests
	@Test
	public void createNoteTest() {
		driver.get(baseUrl + port + "/signup");
		registerPage.signUp(testUsername, testPassword, "test", "user", testPassword);
		driver.get(baseUrl + port + "/login");

		loginPage.login(testUsername, testPassword);
		notesTests.changeTab();
		int notesCountBefore = notesTests.notesCount();
		String desiredTitle = "new note";
		String desiredDescription = "description";
		notesTests.createNote(desiredTitle, desiredDescription);
		notesTests.changeTab();
		int notesCountAfter = notesTests.notesCount();
		String actualTitle = notesTests.getNoteTitle(notesCountBefore);
		String actualDescription = notesTests.getNoteDescription(notesCountBefore);
		registerPage.logout();
		Assertions.assertEquals(desiredTitle, actualTitle);
		Assertions.assertEquals(desiredDescription, actualDescription);
		Assertions.assertEquals(notesCountBefore + 1, notesCountAfter);
	}
	@Test
	public void deleteNoteTest() {
		driver.get(baseUrl + port + "/signup");
		registerPage.signUp(testUsername, testPassword, "test", "user", testPassword);
		driver.get(baseUrl + port + "/login");
		loginPage.login(testUsername, testPassword);

		int i;
		for (i = 0; i < 5; i++) {
			notesTests.changeTab();
			String desiredTitle = "new note".concat(Integer.toString(i));
			String desiredDescription = "description".concat(Integer.toString(i));
			notesTests.createNote(desiredTitle, desiredDescription);
		}
		notesTests.changeTab();
		int notesCountBefore = notesTests.notesCount();
		notesTests.deleteNote(i - 1);
		notesTests.changeTab();
		int notesCountAfter = notesTests.notesCount();
		registerPage.logout();
		Assertions.assertEquals(notesCountBefore - 1, notesCountAfter);
	}
	@Test
	public void editNoteTest() {
		driver.get(baseUrl + port + "/signup");
		registerPage.signUp(testUsername, testPassword, "test", "user", testPassword);
		driver.get(baseUrl + port + "/login");
		loginPage.login(testUsername, testPassword);
		notesTests.changeTab();
		String originalTitle = "new note";
		String originalDescription = "description";
		notesTests.createNote(originalTitle, originalDescription);
		notesTests.changeTab();
		String editedTitle = "edited note title";
		String editedDescription = "edited description";
		notesTests.editNote(0, editedTitle, editedDescription);
		String actualTitle = notesTests.getNoteTitle(0);
		String actualDescription = notesTests.getNoteDescription(0);
		registerPage.logout();
		Assertions.assertNotEquals(actualTitle, originalTitle);
		Assertions.assertNotEquals(actualDescription, originalDescription);
		Assertions.assertEquals(editedTitle, actualTitle);
		Assertions.assertEquals(editedDescription, actualDescription);
	}
	// Credentials Tests
	@Test
	public void createCredentialTest() {
		driver.get(baseUrl + port + "/signup");
		registerPage.signUp(testUsername, testPassword, "test", "user", testPassword);
		driver.get(baseUrl + port + "/login");
		loginPage.login(testUsername, testPassword);
		credentialsTests.changeTab();
		int credentialsCountBefore = credentialsTests.credentialsCount();
		String desiredUsername = "username";
		String desiredPassword = "password";
		String desiredUrl = "https://www.google.com";
		credentialsTests.createCredential(desiredUsername, desiredPassword, desiredUrl);
		credentialsTests.changeTab();
		int credentialsCountAfter = credentialsTests.credentialsCount();
		String actualUsername = credentialsTests.getCredentialUsername(credentialsCountBefore);
		String actualUrl = credentialsTests.getCredentialUrl(credentialsCountBefore);
		registerPage.logout();
		Assertions.assertEquals(desiredUsername, actualUsername);
		Assertions.assertEquals(desiredUrl, actualUrl);
		Assertions.assertEquals(credentialsCountBefore + 1, credentialsCountAfter);
	}
	@Test
	public void deleteCredentialTest() {
		driver.get(baseUrl + port + "/signup");
		registerPage.signUp(testUsername, testPassword, "test", "user", testPassword);
		driver.get(baseUrl + port + "/login");

		loginPage.login(testUsername, testPassword);

		int i;
		for (i = 0; i < 5; i++) {
			credentialsTests.changeTab();
			String desiredUsername = "username".concat(Integer.toString(i));
			String desiredPassword = "password".concat(Integer.toString(i));
			String desiredUrl = "https://www.google".concat(Integer.toString(i)).concat(".com");
			credentialsTests.createCredential(desiredUsername, desiredPassword, desiredUrl);
		}
		credentialsTests.changeTab();
		int credentialsCountBefore = credentialsTests.credentialsCount();
		credentialsTests.deleteCredential(i - 1);
		credentialsTests.changeTab();
		int credentialsCountAfter = credentialsTests.credentialsCount();
		registerPage.logout();
		Assertions.assertEquals(credentialsCountBefore - 1, credentialsCountAfter);
	}
	@Test
	public void editCredentialTest() {
		driver.get(baseUrl + port + "/signup");
		registerPage.signUp(testUsername, testPassword, "test", "user", testPassword);
		driver.get(baseUrl + port + "/login");

		loginPage.login(testUsername, testPassword);
		credentialsTests.changeTab();
		String originalUsername = "username";
		String originalPassword = "password";
		String originalUrl = "https://www.google.com";
		credentialsTests.createCredential(originalUsername, originalPassword, originalUrl);
		credentialsTests.changeTab();
		String editedUsername = "username2";
		String editedPassword = "edited_password";
		String editedUrl = "https://www.yahoo.com";
		credentialsTests.editCredential(0, editedUsername, editedPassword, editedUrl);
		credentialsTests.changeTab();
		String actualUsername = credentialsTests.getCredentialUsername(0);
		String actualUrl = credentialsTests.getCredentialUrl(0);
		registerPage.logout();
		Assertions.assertNotEquals(actualUsername, originalUsername);
		Assertions.assertNotEquals(actualUrl, originalUrl);
		Assertions.assertEquals(editedUsername, actualUsername);
		Assertions.assertEquals(editedUrl, actualUrl);
	}
	@Test
	void contextLoads() {
	}

}
