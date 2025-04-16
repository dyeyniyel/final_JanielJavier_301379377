# Husky Air Reservation System

## Overview

Husky Air Reservation System is a multi-step online reservation application built with Spring Boot, Spring MVC, Thymeleaf, MongoDB, and Jackson. The project demonstrates the migration of a console-based application to a modern web-based system. Users can make reservations, add customer details, process payments (with dynamic price calculation and tax), and update their information via a dashboard. In addition, REST API endpoints are provided for integration and testing purposes.

## Features

- **Reservation Workflow:**  
  - **Reservation Form:** Capture user inputs such as first name, last name, number of passengers, travel class, phone number, departure time, and date.
  - **Validation:** Server-side bean validation ensures that required fields are correctly filled (e.g., phone number must be exactly 10 digits, no. of passengers is at least 1, etc.).
  - **Data Persistence:** Reservations are saved in MongoDB, with customer and payment IDs linking the records.

- **Customer Management:**  
  - **Customer Form:** Collect additional customer details and automatically link them to the correct reservation.
  - **Update:** A dashboard allows users to update customer details if needed.

- **Payment Processing:**  
  - **Payment Form:** Automatically calculates the total payment based on travel class, number of passengers, and applies a 10% tax. The current date is auto-set.
  - **REST API:** A REST endpoint automatically calculates payment values, using Jackson for JSON conversion.

- **Dashboard:**  
  - **Update Reservation & Customer:** A unified dashboard displays both reservation and customer details, enabling users to update data without losing linked information (such as customerId and paymentId).

- **REST API Endpoints:**  
  - Endpoints for Payments and Customers enable integration with external systems and are testable via Postman.
  - Explicit use of Jackson’s ObjectMapper is demonstrated in the REST controllers for logging JSON output.

## Tech Stack

- **Java 17 or higher**
- **Spring Boot** (Spring MVC, Spring Data MongoDB)
- **Thymeleaf**
- **MongoDB**
- **Jackson** (auto-configured by Spring Boot)
- **Maven**

