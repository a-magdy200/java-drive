package org.anchorz.java_drive;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;

public class CredentialsTests {
    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;
    @FindBy(id = "add_new_credentials")
    private WebElement addNewCredentialFormButton;
    @FindBy(id = "addCredentialsBtn")
    private WebElement addCredentialBtn;
    @FindBy(id = "username")
    private WebElement usernameField;
    @FindBy(id = "password")
    private WebElement passwordField;
    @FindBy(id = "url")
    private WebElement urlField;
    @FindBy(className = "credentials-row")
    private List<WebElement> credentials;
    @FindBy(css = ".credentials-row .btn-info")
    private List<WebElement> editBtns;
    @FindBy(css = ".credentials-row .btn-danger")
    private List<WebElement> deleteBtns;
    @FindBy(css = ".credentials-row .username")
    private List<WebElement> credentialsUsernames;
    @FindBy(css = ".credentials-row .key")
    private List<WebElement> credentialsPasswords;
    @FindBy(css = ".credentials-row .url")
    private List<WebElement> credentialsUrls;
    private WebDriver webDriver;
    public CredentialsTests(WebDriver driver) {
        webDriver = driver;
        PageFactory.initElements(driver, this);
    }
    public void changeTab() {
        navCredentialsTab.click();
        new FluentWait<WebDriver>(webDriver).withTimeout(Duration.ofMillis(400)).until(ExpectedConditions.elementToBeClickable(addNewCredentialFormButton));
    }
    public void createCredential(String username, String password, String url) {
        addNewCredentialFormButton.click();
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        urlField.sendKeys(url);
        addCredentialBtn.click();
    }
    public String getCredentialUsername(int credentialIndex) {
        return credentialsUsernames.get(credentialIndex).getText();
    }
    public String getCredentialPassword(int credentialIndex) {
        return credentialsPasswords.get(credentialIndex).getText();
    }
    public String getCredentialUrl(int credentialIndex) {
        return credentialsUrls.get(credentialIndex).getText();
    }
    public void editCredential(int credentialIndex, String username, String password, String url) {
        changeTab();
        editBtns.get(credentialIndex).click();
        usernameField.clear();
        usernameField.sendKeys(username);
        passwordField.clear();
        passwordField.sendKeys(password);
        urlField.clear();
        urlField.sendKeys(url);
        addCredentialBtn.click();
    }
    public void deleteCredential(int credentialIndex) {
        changeTab();
        deleteBtns.get(credentialIndex).click();
    }
    public int credentialsCount() {
        return credentials.size();
    }
}
