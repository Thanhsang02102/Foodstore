-- Script để thêm users vào database
-- Encoding: UTF-8
USE foodstore;

-- Xóa dữ liệu cũ để tránh trùng lặp (nếu cần)
-- DELETE FROM users WHERE email = 'nguyenthanhsang02102@gmail.com';

-- Thêm user admin
-- Password: 123456 (BCrypt hash)
-- BCrypt hash cho "123456" với strength 10
INSERT INTO users (email, password, full_name, role_id, provider, address, phone, avatar, create_day_time, update_day_time) 
VALUES 
('nguyenthanhsang02102@gmail.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'NguyenThanhSang', 1, 'LOCAL', 'daklak', '0336666666', '', NOW(), NOW());

-- Thêm user thường (nếu cần)
-- Password: 123456 (BCrypt hash)
-- INSERT INTO users (email, password, full_name, role_id, provider, address, phone, avatar, create_day_time, update_day_time) 
-- VALUES 
-- ('user@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'User Test', 2, 'LOCAL', 'HCM', '0123456789', '', NOW(), NOW());
