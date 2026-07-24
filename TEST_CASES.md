# Test Case Document — OrangeHRM Selenium Automation Framework

**Application Under Test:** OrangeHRM Demo (https://opensource-demo.orangehrmlive.com)
**Modules Covered:** Login, Dashboard, PIM (Employee Information Management)
**Total Test Cases:** 11
**Automation Status:** All 11 automated (Selenium + Java + TestNG)

---

## Module 1: Login

| TC ID | Test Scenario | Precondition | Test Steps | Expected Result | Priority | Automated |
|-------|---------------|--------------|------------|------------------|----------|-----------|
| TC_LOGIN_01 | Verify successful login with valid credentials | User is on the Login page | 1. Enter valid username (Admin)<br>2. Enter valid password<br>3. Click Login button | User is redirected to the Dashboard page | High | ✅ |
| TC_LOGIN_02 | Verify error message on invalid credentials | User is on the Login page | 1. Enter invalid username<br>2. Enter invalid password<br>3. Click Login button | An "Invalid credentials" error message is displayed; user remains on Login page | High | ✅ |
| TC_LOGIN_03 | Verify validation errors on empty form submission | User is on the Login page | 1. Leave username and password fields empty<br>2. Click Login button | Required-field validation messages appear under both fields | Medium | ✅ |
| TC_LOGIN_04 | Verify user can log out successfully | User is logged in and on the Dashboard | 1. Click user profile dropdown<br>2. Click Logout | User is redirected back to the Login page | High | ✅ |
| TC_LOGIN_05 | Verify password field masks entered characters | User is on the Login page | 1. Click on the password field<br>2. Type any value | Entered characters are masked (shown as dots), not plain text | Low | ✅ |

## Module 2: Dashboard

| TC ID | Test Scenario | Precondition | Test Steps | Expected Result | Priority | Automated |
|-------|---------------|--------------|------------|------------------|----------|-----------|
| TC_DASH_01 | Verify Dashboard loads with widgets after login | User has logged in successfully | 1. Observe the Dashboard page after login | Dashboard header is visible and at least one summary widget is rendered | High | ✅ |
| TC_DASH_02 | Verify sidebar navigation contains core modules | User is on the Dashboard page | 1. Inspect the left sidebar menu | Sidebar includes core modules: Admin, PIM, Dashboard (among others) | Medium | ✅ |
| TC_DASH_03 | Verify sidebar menu has the expected minimum module count | User is on the Dashboard page | 1. Count the number of items in the sidebar menu | Sidebar contains at least 6 core modules | Low | ✅ |

## Module 3: PIM (Employee Information Management)

| TC ID | Test Scenario | Precondition | Test Steps | Expected Result | Priority | Automated |
|-------|---------------|--------------|------------|------------------|----------|-----------|
| TC_PIM_01 | Verify searching for an existing employee returns results | User is logged in and on the PIM > Employee List page | 1. Enter a known employee name in the Employee Name search field<br>2. Click Search | Search executes successfully and returns matching employee record(s) | High | ✅ |
| TC_PIM_02 | Verify searching for a non-existent employee shows no results | User is logged in and on the PIM > Employee List page | 1. Enter a name that does not exist in the system<br>2. Click Search | "No Records Found" message is displayed | High | ✅ |
| TC_PIM_03 | Verify Reset button clears the applied search filter | User has performed an employee search | 1. Enter a name and search<br>2. Click the Reset button | Search filter is cleared and the full employee list reloads | Medium | ✅ |

---

## Notes

- **Priority** reflects business impact if the scenario were to fail in production (High = core user flow, Medium = secondary flow, Low = cosmetic/edge behavior).
- **CI Scope:** Login and Dashboard modules (TC_LOGIN_01–05, TC_DASH_01–03) run automatically on every push via GitHub Actions. PIM module tests (TC_PIM_01–03) are validated locally — see `README.md` for details on running the full suite (`testng-full.xml`) and the reasoning behind this CI scoping decision.
- Test case IDs map directly to automated test methods in `src/test/java/com/qaframework/tests/` for full traceability between manual test design and automation code.
