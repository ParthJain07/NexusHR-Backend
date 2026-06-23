# NexusHR API Testing Guide

This document contains all the endpoints necessary to validate the current backend implementation using Postman, cURL, or any other API client.

---

## 1. Auth Service (Port: 8080)

### Register a New Employee
* **URL:** `POST http://localhost:8080/api/auth/signup`
* **Body (JSON):**
  ```json
  {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@zidio.in",
      "password": "securepassword",
      "department": "Engineering",
      "designation": "Software Engineer"
  }
  ```

### Login (Generate JWT Token)
* **URL:** `POST http://localhost:8080/api/auth/login`
* **Body (JSON):**
  ```json
  {
      "email": "john.doe@zidio.in",
      "password": "securepassword"
  }
  ```
* **Note:** Copy the token from the response. You will need it in the `Authorization: Bearer <token>` header for protected routes.

---

## 2. Employee Service (Port: 8081)

### Get All Employees
* **URL:** `GET http://localhost:8081/api/employees`
* **Note:** This endpoint is cached via Redis for instant retrieval.

### Get Employee By ID
* **URL:** `GET http://localhost:8081/api/employees/{id}`

### Delete Employee (Requires HR/Admin Role)
* **URL:** `DELETE http://localhost:8081/api/employees/{id}`
* **Headers:** `Authorization: Bearer <Your_JWT_Token>`

---

## 3. Attendance Management (Port: 8081)

### Clock In
* **URL:** `POST http://localhost:8081/api/attendance/clock-in`
* **Body (JSON):**
  ```json
  {
      "employeeId": 1
  }
  ```

### Clock Out
* **URL:** `POST http://localhost:8081/api/attendance/clock-out`
* **Body (JSON):**
  ```json
  {
      "employeeId": 1
  }
  ```

### View Attendance History
* **URL:** `GET http://localhost:8081/api/attendance/{employeeId}`

---

## 4. Leave Management (Port: 8081)

### Submit a Leave Request
* **URL:** `POST http://localhost:8081/api/leaves/request`
* **Body (JSON):**
  ```json
  {
      "employeeId": 1,
      "startDate": "2026-07-01",
      "endDate": "2026-07-05",
      "leaveType": "VACATION",
      "reason": "Family trip"
  }
  ```

### View Pending Leave Requests (Requires HR/Admin Role)
* **URL:** `GET http://localhost:8081/api/leaves/pending`
* **Headers:** `Authorization: Bearer <Your_JWT_Token>`

### Approve/Reject a Leave Request (Requires HR/Admin Role)
* **URL:** `PUT http://localhost:8081/api/leaves/{leaveId}/status?status=APPROVED`
* **Headers:** `Authorization: Bearer <Your_JWT_Token>`
