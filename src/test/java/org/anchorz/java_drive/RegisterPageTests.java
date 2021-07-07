package org.anchorz.java_drive;




import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPageTests {

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "firstname")
    private WebElement firstnameField;

    @FindBy(id = "lastname")
    private WebElement lastnameField;

    @FindBy(id = "confirmPassword")
    private WebElement confirmPasswordField;

    @FindBy(id = "registerBtn")
    private WebElement registerBtn;

    @FindBy(id = "logoutBtn")
    private WebElement logoutBtn;

    public RegisterPageTests(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
    public void logout() {
        logoutBtn.click();
    }
    public void signUp(String username, String password, String firstname, String lastname, String confirmPassword) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        firstnameField.sendKeys(firstname);
        lastnameField.sendKeys(lastname);
        confirmPasswordField.sendKeys(confirmPassword);
        registerBtn.click();
    }

}
