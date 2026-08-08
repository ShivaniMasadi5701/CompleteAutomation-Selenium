# Complete Automation Selenium Framework

A Selenium WebDriver automation framework developed using Java,
TestNG and Maven for automating the OrangeHRM web application.

## Tech Stack
- Java
- Selenium WebDriver
- TestNG
- Maven
- Page Object Model
- WebDriverManager
- Extent Reports
- Allure Reports
- Log4j
- GitHub Actions

## Framework Features
- Page Object Model
- Reusable WebDriver utilities
- Explicit waits
- TestNG annotations
- Data-driven testing
- Screenshot capture
- Reporting
- Logging
- CI/CD using GitHub Actions

## Project Structure

src/main
 └── java
     └── com.orangehrm
         ├── base
         ├── login
         ├── admin
         └── myinfo

src/test
 └── java
     └── com.orangehrm
         ├── base
         ├── login
         ├── admin
         └── util

## How to Run

mvn clean test

## CI/CD

Tests are automatically executed using GitHub Actions.
