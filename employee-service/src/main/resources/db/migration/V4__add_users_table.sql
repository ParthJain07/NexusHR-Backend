CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    employee_id BIGINT UNIQUE REFERENCES employees(id)
);
-- Remove old auth columns from employees table
ALTER TABLE employees DROP COLUMN IF EXISTS password;
ALTER TABLE employees DROP COLUMN IF EXISTS role;