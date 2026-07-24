package com.qaframework.tests;

import com.qaframework.base.BaseTest;
import com.qaframework.pages.DashboardPage;
import com.qaframework.pages.LoginPage;
import com.qaframework.utils.ConfigReader;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Test suite for the Dashboard module.
 * Covers: widget presence, sidebar navigation menu, and key menu items.
 */
public class DashboardTest extends BaseTest {

    private DashboardPage dashboardPage;

    @BeforeMethod(alwaysRun = true, dependsOnMethods = "setUp")
    public void loginBeforeEachTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("valid.username"), ConfigReader.get("valid.password"));
        dashboardPage = new DashboardPage(driver);
    }

    @Test(priority = 1, description = "Verify dashboard loads with expected widgets")
    public void testDashboardWidgetsDisplayed() {
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard header should be visible");
        int widgetCount = dashboardPage.getWidgetCount();
        Assert.assertTrue(widgetCount > 0, "Dashboard should render at least one widget");
    }

    @Test(priority = 2, description = "Verify sidebar navigation menu contains expected core modules")
    public void testSidebarMenuItemsPresent() {
        List<WebElement> menuItems = dashboardPage.getSidebarMenuItems();
        List<String> menuTexts = menuItems.stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertTrue(menuTexts.contains("Admin"), "Sidebar should contain 'Admin' menu item");
        Assert.assertTrue(menuTexts.contains("PIM"), "Sidebar should contain 'PIM' menu item");
        Assert.assertTrue(menuTexts.contains("Dashboard"), "Sidebar should contain 'Dashboard' menu item");
    }

    @Test(priority = 3, description = "Verify sidebar menu has more than 5 core modules")
    public void testSidebarMenuItemCount() {
        int count = dashboardPage.getSidebarMenuItems().size();
        Assert.assertTrue(count >= 6, "Expected at least 6 sidebar modules, found: " + count);
    }
}
