# NHSBSA - NHS Job Search Automation Test Suite

This repository contains an automated functional acceptance test suite for validating the **NHS Jobs Search functionality** using BDD principles.

## 👤 Candidate: Shraddha Limbasiya  

---

## Project Objective

As a jobseeker on the NHS Jobs website, I want to search for a job with my preferences so that I can get recently posted job results.

This project automates the search functionality of the NHS Jobs website:
🔗 https://www.jobs.nhs.uk/candidate/search

---
## Cross-Browser Compatibility

Supports Chrome, Firefox and Edge

WebDriver binaries managed via WebDriverManager

No need to install or maintain local driver binaries

Change browser by modifying the parameter in:

Base.getWebdriver("chrome")

### Command Line Execution

To run all tests (default browser is Chrome):

mvn clean test


## Acceptance Criteria Covered

- Search with job title and location preferences
- Filter by distance
- Sorting by "Date Posted (newest)"
- Partial and blank field searches
- Invalid input handling
- Pagination support
- Click-through to job details and navigation back
- Filtering by:
  - Working pattern
  - Contract type
  - Salary range
  - Staff group
  - Job reference number
- Combined filter application
- Clearing all filters

---

## ⚙Tech Stack
```bash
| Tool/Framework     | Version          |
|--------------------|------------------|
| Java               | 21               |
| Maven              | 3.9+             |
| Selenium WebDriver | Latest stable    |
| Cucumber           | Latest stable    |
| WebDriverManager   | Latest stable    |
| Browsers           | Chrome, Firefox  |
| Build Tool         | Maven            |
| Test Framework     | TestNG (or JUnit)|
```
---

## Project Structure
```bash
src/
├── main/
│   └── java/
│       └── pages/                 # Page Object Model
│
└── test/
    ├── java/
    │   ├── hooks/                 # WebDriver setup (Base.java)
    │   ├── stepdefinitions/       # Step Definitions
    │   └── runners/               # Cucumber Test Runner
    └── resources/
        └── features/              # Gherkin feature files

---
```
### Prerequisites

- Java 21 installed
- Maven installed and added to PATH
- Chrome and Firefox browsers installed
- Internet connection (to access NHS Jobs site and for WebDriverManager)



## Test Reports

Cucumber HTML reports are generated after test execution.

Default output location:

target/cucumber-reports.html

Open the file in any browser to view test results.

## Future Improvements
Add CI integration (GitHub Actions, Jenkins)

Parameterize browser via command-line or config

Integrate accessibility testing (e.g., axe-core)

Add edge cases and additional negative test scenarios

Enable parallel test execution to speed up runs


## Presentation

https://www.canva.com/design/DAGvBPH6xFI/FlCDUnv0l3f9NHGzLqeLgA/view?utm_content=DAGvBPH6xFI&utm_campaign=designshare&utm_medium=link2&utm_source=uniquelinks&utlId=h5045494adb





