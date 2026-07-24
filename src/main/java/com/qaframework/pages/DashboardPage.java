package com.qaframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object for the OrangeHRM Dashboard (landing page after login).
 */
public class DashboardPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
    private final By userDropdown = By.cssSelector(".oxd-userdropdown-tab");
    private final By logoutLink = By.xpath("//a[text()='Logout']");
    private final By widgets = By.cssSelector(".oxd-grid-item--gutters");
    private final By sidebarMenuItems = By.cssSelector(".oxd-main-menu-item");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public boolean isDashboardDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardHeader)).isDisplayed();
    }

    public int getWidgetCount() {
        wait.until(ExpectedConditions.presenceOfElementLocated(widgets));
        return driver.findElements(widgets).size();
    }

    public List<WebElement> getSidebarMenuItems() {
        wait.until(ExpectedConditions.presenceOfElementLocated(sidebarMenuItems));
        return driver.findElements(sidebarMenuItems);
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(userDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }
}
