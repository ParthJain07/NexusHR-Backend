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

### Get All Employees (Requires ADMIN, HR, or MANAGER Role)
* **URL:** `GET http://localhost:8081/api/employees`
* **Headers:** `Authorization: Bearer <Your_JWT_Token>`
* **Note:** This endpoint is cached via Redis for instant retrieval. Restricted to managers and administrative roles.

### Get Employee By ID
* **URL:** `GET http://localhost:8081/api/employees/{id}`
* **Headers:** `Authorization: Bearer <Your_JWT_Token>`
* **Note:** Accessible by ADMIN, HR, MANAGER, or the employee viewing their own profile (`VIEW_EMPLOYEE` authority).

### Create Employee (Requires CREATE_EMPLOYEE Authority)
* **URL:** `POST http://localhost:8081/api/employees`
* **Headers:** `Authorization: Bearer <Your_JWT_Token>`
* **Body (JSON):**
  ```json
  {
      "firstName": "Alice",
      "lastName": "Smith",
      "email": "alice.smith@zidio.in",
      "department": "Human Resources",
      "designation": "HR Manager",
      "joiningDate": "2026-06-29"
  }
  ```

### Update Employee (Requires UPDATE_EMPLOYEE Authority)
* **URL:** `PUT http://localhost:8081/api/employees/{id}`
* **Headers:** `Authorization: Bearer <Your_JWT_Token>`
* **Body (JSON):**
  ```json
  {
      "firstName": "Alice",
      "lastName": "Smith-Johnson",
      "email": "alice.smith@zidio.in",
      "department": "Human Resources",
      "designation": "Senior HR Manager"
  }
  ```

### Delete Employee (Requires ADMIN Role / DELETE_EMPLOYEE Authority)
* **URL:** `DELETE http://localhost:8081/api/employees/{id}`
* **Headers:** `Authorization: Bearer <Your_JWT_Token>`

---

## 3. Attendance Management (Port: 8081)

### Clock In
* **URL:** `POST http://localhost:8081/api/attendance/clock-in`
* **Headers:** `Authorization: Bearer <Your_JWT_Token>`
* **Body (JSON):**
  ```json
  {
      "employeeId": 1
  }
  ```

### Clock Out
* **URL:** `POST http://localhost:8081/api/attendance/clock-out`
* **Headers:** `Authorization: Bearer <Your_JWT_Token>`
* **Body (JSON):**
  ```json
  {
      "employeeId": 1
  }
  ```

### View Attendance History
* **URL:** `GET http://localhost:8081/api/attendance/{employeeId}`
* **Headers:** `Authorization: Bearer <Your_JWT_Token>`

---

## 4. Leave Management (Port: 8081)

### Submit a Leave Request (Requires APPLY_LEAVE Authority)
* **URL:** `POST http://localhost:8081/api/leaves/request`
* **Headers:** `Authorization: Bearer <Your_JWT_Token>`
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

### Approve/Reject a Leave Request (Requires APPROVE_LEAVE Authority)
* **URL:** `PUT http://localhost:8081/api/leaves/{leaveId}/status?status=APPROVED`
* **Headers:** `Authorization: Bearer <Your_JWT_Token>`
