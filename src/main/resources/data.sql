-- This inserts an admin user with the password 'admin' (pre-hashed)
INSERT INTO users (email, password, first_name, last_name, mobile_number, role) VALUES
    ('admin@example.com', '$2a$10$hI.bvHShJkxdhBefxsMEzualjcMiyBwK1buhMFcfL0zzt3ULTRJJy', 'Admin', 'User', '9999999999', 'ROLE_ADMIN');

INSERT INTO facility (facility_name, facility_location, facility_type) VALUES
                                                                           ('Central Library', 'Main Campus, Block A', 1),
                                                                           ('Basketball Court', 'Sports Complex', 2),
                                                                           ('Computer Lab 5', 'Engineering Block, 3rd Floor', 3);