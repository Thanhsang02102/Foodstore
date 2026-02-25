-- Script để thêm users vào database
-- Encoding: UTF-8
USE foodstore;

-- Thêm user admin
-- Lưu ý: password sẽ được đồng bộ lại ở runtime trong FoodStoreApplication
-- để luôn đúng với cấu hình mặc định hiện tại.
INSERT INTO users (email, password, full_name, role_id, provider, address, phone, avatar, create_day_time, update_day_time) 
SELECT 
  'nguyenthanhsang02102@gmail.com',
  '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
  'NguyenThanhSang',
  (SELECT id FROM roles WHERE name = 'ADMIN' LIMIT 1),
  'LOCAL',
  'daklak',
  '0336666666',
  '',
  NOW(),
  NOW()
WHERE EXISTS (SELECT 1 FROM roles WHERE name = 'ADMIN')
  AND NOT EXISTS (SELECT 1 FROM users WHERE email = 'nguyenthanhsang02102@gmail.com');

-- Thêm user thường (nếu cần)
-- INSERT INTO users (email, password, full_name, role_id, provider, address, phone, avatar, create_day_time, update_day_time) 
-- VALUES 
-- ('user@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'User Test', (SELECT id FROM roles WHERE name='USER' LIMIT 1), 'LOCAL', 'HCM', '0123456789', '', NOW(), NOW());
