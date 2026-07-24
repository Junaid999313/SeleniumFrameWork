package com.qaframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for the OrangeHRM Login page.
 * Locators and low-level actions live here; test classes only call
 * these business-readable methods (login, verifyErrorShown, etc.).
 */
public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By errorAlert = By.cssSelector(".oxd-alert-content-text");
    private final By requiredFieldErrors = By.cssSelector(".oxd-input-group .oxd-text--span");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void login(String username, String password) {
        WebElement userBox = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        userBox.clear();
        userBox.sendKeys(username);

        WebElement passBox = driver.findElement(passwordField);
        passBox.clear();
        passBox.sendKeys(password);

        driver.findElement(loginButton).click();
    }

    public void clickLoginWithoutCredentials() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton));
        driver.findElement(loginButton).click();
    }

    public String getInvalidCredentialsError() {
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(errorAlert));
        return alert.getText();
    }

    public int getRequiredFieldErrorCount() {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(requiredFieldErrors, 0));
        return driver.findElements(requiredFieldErrors).size();
    }

    public boolean isLoginPageDisplayed() {
        return driver.findElement(usernameField).isDisplayed();
    }
}
