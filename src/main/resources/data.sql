-- This inserts an admin user with the password 'admin' (pre-hashed)
INSERT INTO users (email, password, first_name, last_name, mobile_number, role) VALUES
    ('admin@example.com', '$2a$10$kFbhgI1Nopjqfzv68RbaHOQiMDbSkLbDjTv0aA7CcKVnCBhQ00xbe', 'Admin', 'User', '9999999999', 'ROLE_ADMIN');