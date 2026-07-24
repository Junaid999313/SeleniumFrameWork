package com.qaframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for the PIM (Employee Information) module — search functionality.
 */
public class PIMPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final WebDriverWait shortWait;

    private final By pimMenuLink = By.xpath("//span[text()='PIM']");
    // Using (…)[1] guards against any hidden duplicate/template node matching the same placeholder.
    private final By employeeNameInput = By.xpath("(//input[@placeholder='Type for hints...'])[1]");
    private final By searchButton = By.cssSelector("button[type='submit']");
    private final By resetButton = By.xpath("//button[normalize-space()='Reset']");
    private final By resultTableRows = By.cssSelector(".oxd-table-card");
    private final By noRecordsFound = By.xpath("//span[text()='No Records Found']");
    private final By loadingSpinner = By.cssSelector(".oxd-loading-spinner, .oxd-loading-spinner-container");

    public PIMPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void navigateToPIM() {
        wait.until(ExpectedConditions.elementToBeClickable(pimMenuLink)).click();
        // Wait for the SPA route to actually change to the Employee List page.
        wait.until(ExpectedConditions.urlContains("viewEmployeeList"));
        // The page shows a brief loading spinner while it fetches data — if present, wait it out.
        try {
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(loadingSpinner));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
        } catch (Exception ignored) {
            // Spinner may finish too fast to ever be caught visible — that's fine, continue.
        }
        // Presence first (element exists in DOM), then visibility (fully rendered/displayed).
        wait.until(ExpectedConditions.presenceOfElementLocated(employeeNameInput));
        wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput));
    }

    public void searchByEmployeeName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput)).sendKeys(name);
        driver.findElement(searchButton).click();
    }

    public void resetSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(resetButton)).click();
    }

    public int getSearchResultCount() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".oxd-table")));
        return driver.findElements(resultTableRows).size();
    }

    public boolean isNoRecordsFoundDisplayed() {
        try {
            return driver.findElement(noRecordsFound).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
