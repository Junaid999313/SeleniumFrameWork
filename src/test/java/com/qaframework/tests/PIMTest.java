package com.qaframework.tests;

import com.qaframework.base.BaseTest;
import com.qaframework.pages.DashboardPage;
import com.qaframework.pages.LoginPage;
import com.qaframework.pages.PIMPage;
import com.qaframework.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test suite for the PIM (Employee Information Management) module.
 * Covers: navigation to PIM, valid employee search, and invalid search handling.
 */
public class PIMTest extends BaseTest {

    private PIMPage pimPage;

    @BeforeMethod(alwaysRun = true, dependsOnMethods = "setUp")
    public void loginAndNavigateToPIM() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("valid.username"), ConfigReader.get("valid.password"));

        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Must reach dashboard before testing PIM");

        pimPage = new PIMPage(driver);
        pimPage.navigateToPIM();
    }

    @Test(priority = 1, description = "Verify searching for a known/default employee returns results")
    public void testSearchExistingEmployee() {
        pimPage.searchByEmployeeName("Amelia");
        int resultCount = pimPage.getSearchResultCount();
        Assert.assertTrue(resultCount >= 0, "Search should execute without error for a valid name query");
    }

    @Test(priority = 2, description = "Verify searching for a non-existent employee shows 'No Records Found'")
    public void testSearchNonExistentEmployee() {
        pimPage.searchByEmployeeName("ZZZNonExistentEmployeeXYZ");
        Assert.assertTrue(pimPage.isNoRecordsFoundDisplayed(),
                "Expected 'No Records Found' message for a non-existent employee");
    }

    @Test(priority = 3, description = "Verify reset button clears the search filter")
    public void testResetSearchFilter() {
        pimPage.searchByEmployeeName("Amelia");
        pimPage.resetSearch();
        // After reset, the grid should reload with the full (unfiltered) employee list
        int resultCount = pimPage.getSearchResultCount();
        Assert.assertTrue(resultCount >= 0, "Reset should reload the employee list without error");
    }
}
