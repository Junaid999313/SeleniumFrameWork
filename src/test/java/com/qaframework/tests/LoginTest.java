package com.qaframework.tests;

import com.qaframework.base.BaseTest;
import com.qaframework.pages.DashboardPage;
import com.qaframework.pages.LoginPage;
import com.qaframework.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test suite for the Login module.
 * Covers: valid login, invalid login, empty-field validation, and logout.
 */
public class LoginTest extends BaseTest {

    @Test(priority = 1, description = "Verify user can log in with valid credentials")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("valid.username"), ConfigReader.get("valid.password"));

        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard should be displayed after valid login");
    }

    @Test(priority = 2, description = "Verify error message on invalid username/password")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("InvalidUser", "WrongPassword123");

        String errorText = loginPage.getInvalidCredentialsError();
        Assert.assertTrue(errorText.contains("Invalid credentials"),
                "Expected invalid credentials error, but got: " + errorText);
    }

    @Test(priority = 3, description = "Verify validation errors when submitting empty login form")
    public void testEmptyFieldValidation() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLoginWithoutCredentials();

        int errorCount = loginPage.getRequiredFieldErrorCount();
        Assert.assertTrue(errorCount >= 2, "Expected required-field errors for both username and password");
    }

    @Test(priority = 4, description = "Verify user can log out successfully and return to login page")
    public void testLogout() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("valid.username"), ConfigReader.get("valid.password"));

        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Should land on dashboard before logout");

        dashboardPage.logout();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Should return to login page after logout");
    }

    @Test(priority = 5, description = "Verify password field masks input")
    public void testPasswordFieldIsMasked() {
        driver.findElement(org.openqa.selenium.By.name("password")).sendKeys("test123");
        String fieldType = driver.findElement(org.openqa.selenium.By.name("password")).getAttribute("type");
        Assert.assertEquals(fieldType, "password", "Password field should mask input characters");
    }
}
