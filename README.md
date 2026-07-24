# OrangeHRM Selenium TestNG Automation Framework

A Selenium WebDriver automation framework built in **Java**, using **TestNG** and the
**Page Object Model (POM)** design pattern, targeting the public
[OrangeHRM demo application](https://opensource-demo.orangehrmlive.com/).

This project demonstrates end-to-end automation framework design — from folder
structure and configuration management to CI/CD execution — applying the same
principles used in enterprise QA automation.

## 🧱 Tech Stack

| Layer | Tool |
|---|---|
| Language | Java 11 |
| Build Tool | Maven |
| Test Framework | TestNG |
| Automation Library | Selenium WebDriver 4 |
| Driver Management | WebDriverManager (Bonigarcia) |
| Design Pattern | Page Object Model (POM) |
| CI/CD | GitHub Actions |
| Reporting | TestNG Surefire Reports |

## 📁 Project Structure

```
src/
├── main/java/com/qaframework/
│   ├── base/        → BaseTest (WebDriver setup/teardown)
│   ├── pages/        → Page Object classes (LoginPage, DashboardPage, PIMPage)
│   └── utils/       → ConfigReader (externalized config)
├── test/java/com/qaframework/tests/
│   ├── LoginTest.java
│   ├── DashboardTest.java
│   └── PIMTest.java
└── test/resources/
    └── config.properties
testng.xml            → Test suite orchestration
.github/workflows/     → CI/CD pipeline (auto-run on every push)
```

## ✅ Test Coverage (11 test cases across 3 modules)

**Login Module**
- Valid login
- Invalid credentials error message
- Empty-field validation
- Logout flow
- Password field masking

**Dashboard Module**
- Widget rendering after login
- Sidebar menu items present (Admin, PIM, Dashboard, etc.)
- Sidebar menu item count check

**PIM Module**
- Search for an existing employee
- Search for a non-existent employee (negative case)
- Reset search filter

## ▶️ How to Run

```bash
# Clone the repo
git clone https://github.com/<your-username>/orangehrm-selenium-framework.git
cd orangehrm-selenium-framework

# Run the full suite
mvn clean test
```

Reports are generated at `target/surefire-reports/` after each run.

## ⚙️ CI/CD

Every push to `main`/`master` automatically triggers the test suite via
**GitHub Actions** (see `.github/workflows/run-tests.yml`), running headless
Chrome on Ubuntu and uploading the test report as a build artifact.

## 🔑 Key Design Decisions

- **Externalized configuration** (`config.properties`) — URL, credentials, and
  timeouts are never hardcoded in test/page classes, so the suite can point at
  a different environment with a one-line change.
- **Page Object Model** — locators and low-level actions live in `pages/`,
  keeping test classes focused purely on business-readable steps.
- **Headless execution** — configured for CI; comment out the
  `--headless=new` argument in `BaseTest.java` to watch tests run locally.

## 🚀 Next Steps (in progress)

- Migrating select scenarios to **Playwright** to compare execution speed and
  auto-waiting behavior against Selenium.
- Exploring **AI-assisted test design** — using LLMs to brainstorm edge cases
  and generate test data during planning.
