-- This inserts an admin user with the password 'admin' (pre-hashed)
INSERT INTO users (email, password, first_name, last_name, mobile_number, role) VALUES
    ('admin@example.com', '$2a$10$tZMZNIhD2v/swZtL9Pu4o.26jGs.w8HGRhevDABJeRUAvjlDPP.du', 'Admin', 'User', '9999999999', 'ROLE_ADMIN');