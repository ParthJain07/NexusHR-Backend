CREATE TABLE attendance (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    date DATE NOT NULL,
    clock_in_time TIME NOT NULL,
    clock_out_time TIME,
    status VARCHAR(20),
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);