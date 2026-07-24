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

    private final By pimMenuLink = By.xpath("//span[text()='PIM']");
    private final By employeeNameInput = By.cssSelector("input[placeholder='Type for hints...']");
    private final By searchButton = By.cssSelector("button[type='submit']");
    private final By resetButton = By.xpath("//button[normalize-space()='Reset']");
    private final By resultTableRows = By.cssSelector(".oxd-table-card");
    private final By noRecordsFound = By.xpath("//span[text()='No Records Found']");

    public PIMPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void navigateToPIM() {
        wait.until(ExpectedConditions.elementToBeClickable(pimMenuLink)).click();
        // Wait for the SPA route to actually change to the Employee List page
        // before any subsequent step tries to interact with it — this is the
        // step that was timing out under GitHub Actions network latency.
        wait.until(ExpectedConditions.urlContains("viewEmployeeList"));
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
