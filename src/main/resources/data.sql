-- This inserts an admin user with the password 'admin' (pre-hashed)
INSERT INTO users (email, password, first_name, last_name, mobile_number, role) VALUES
    ('admin@example.com', '$2a$10$N0iB65g2b2n9g4C9Y0F0.OKc8s6H.L0.B2.P1c5L3e1a8g3h5i7j9', 'Admin', 'User', '9999999999', 'ROLE_ADMIN');