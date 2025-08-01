# NHSBSA - NHS Job Search Automation Test Suite

This repository contains an automated functional acceptance test suite for validating the **NHS Jobs Search functionality** using BDD principles.

## Candidate: Shraddha Limbasiya

## Prerequisites

- Java 21 installed
- Maven installed and added to PATH
- Chrome, Firefox and Edge browsers installed
- Internet connection (to access NHS Jobs site and for WebDriverManager)

## Command Line Execution

To run all tests (default browser is Chrome):

```bash
mvn clean test

---

## Project Structure

As a jobseeker on the NHS Jobs website, 
I want to search for a job with my preferences 
so that I can get recently posted job results.

This project automates the search functionality of the NHS Jobs website:
🔗 https://www.jobs.nhs.uk/candidate/search  

---

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

## Tech Stack

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

---

## Project Structure
src/
|__main/
|    └──java/
|        └── pages/ 
|
└── test/
      ├── java/
      |   ├── hooks/ 
      |   ├── stepdefinitions/ 
      |   └── runners/ 
      └── resources/
            └── features/ 

---

## Cross-Browser Compatibility

Supports Chrome, Firefox and Edge
WebDriver binaries managed via WebDriverManager
No need to install or maintain local driver binaries
The browser can be changed by modifying the browser parameter in hooks 'Base.getWebdriver("chrome")'.

---

## Test Reports

Cucumber HTML reports are generated after the test run.  
Default output location: `target/cucumber-reports.html`

You can open this file in a browser to view test results.

---

## Future Improvements

- Add CI integration (e.g., GitHub Actions, Jenkins)
- Parameterize browser via command line or config file
- Add accessibility automation (axe-core integration)
- Extend filters and edge cases in test coverage
- Add parallel test execution for faster runs
