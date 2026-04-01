-- Script để thêm sản phẩm mẫu vào database với ảnh
-- Encoding: UTF-8
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;
USE foodstore;

-- Xóa dữ liệu cũ để tránh trùng lặp
DELETE FROM products;

-- Thêm sản phẩm với ảnh có sẵn
INSERT INTO products (name, price, image, detail_desc, short_desc, quantity, source, unit, type, target, customer_target, create_day_time, update_day_time) VALUES

-- Rau củ
('Rau cải xanh', 25000, '1747496680924-Cái bó xôi.jpeg', 'Rau cải xanh tươi ngon, giàu vitamin và chất xơ, tốt cho sức khỏe. Được trồng tại Đà Lạt với tiêu chuẩn an toàn thực phẩm.', 'Rau cải xanh tươi ngon từ Đà Lạt', 100, 'Đà Lạt', 'kg', 'rau', 'Người lớn', 'Tất cả', NOW(), NOW()),

('Cải bắp', 20000, '1747496471293-Cải bắp.png', 'Cải bắp tươi ngon, giòn ngọt, giàu vitamin C và chất xơ. Phù hợp để làm salad, nấu canh hoặc xào.', 'Cải bắp tươi giòn ngọt', 80, 'Đà Lạt', 'kg', 'rau', 'Người lớn', 'Tất cả', NOW(), NOW()),

('Cải xoong', 30000, '1747496803058-Cải xoong.jpeg', 'Cải xoong tươi xanh, có vị cay nhẹ đặc trưng. Giàu vitamin A, C và canxi. Thích hợp làm salad hoặc nấu canh.', 'Cải xoong tươi xanh', 60, 'Đà Lạt', 'kg', 'rau', 'Người lớn', 'Tất cả', NOW(), NOW()),

('Súp lơ', 35000, '1747498293032-Súp lơ.jpeg', 'Súp lơ trắng tươi ngon, giàu vitamin K, C và chất xơ. Có thể luộc, xào hoặc nướng.', 'Súp lơ trắng tươi ngon', 50, 'Đà Lạt', 'kg', 'rau', 'Người lớn', 'Tất cả', NOW(), NOW()),

('Salad rau xanh', 40000, '1747498028458-Salad rau xanh.jpeg', 'Hỗn hợp rau xanh tươi ngon, sẵn sàng để làm salad. Bao gồm xà lách, rau thơm và các loại rau khác.', 'Hỗn hợp rau xanh làm salad', 40, 'Đà Lạt', 'kg', 'rau', 'Người lớn', 'Tất cả', NOW(), NOW()),

-- Củ
('Cà rốt', 30000, '1747497376759-Gạo lứt.jpeg', 'Cà rốt tươi ngon, giàu vitamin A, tốt cho mắt và da. Có thể ăn sống, nấu chín hoặc ép nước.', 'Cà rốt tươi giàu vitamin A', 80, 'Đà Lạt', 'kg', 'cu', 'Tốt cho mắt', 'Tất cả', NOW(), NOW()),

('Khoai lang', 25000, '1747497663680-Khoai lang.jpeg', 'Khoai lang vàng ngọt, giàu chất xơ và beta-carotene. Có thể luộc, nướng hoặc làm chè.', 'Khoai lang vàng ngọt', 70, 'Đà Lạt', 'kg', 'cu', 'Tất cả', 'Tất cả', NOW(), NOW()),

-- Trái cây
('Cam sành', 45000, '1747496933030-Cam.png', 'Cam sành ngọt mát, giàu vitamin C, tăng cường sức đề kháng. Vỏ dày, múi mọng nước.', 'Cam sành ngọt mát', 50, 'Vĩnh Long', 'kg', 'trai-cay', 'Trẻ em', 'Tất cả', NOW(), NOW()),

('Chuối', 30000, '1747497140138-Chuối.webp', 'Chuối chín vàng, ngọt tự nhiên, giàu kali và vitamin B6. Tốt cho tiêu hóa và tim mạch.', 'Chuối chín vàng ngọt', 60, 'Tiền Giang', 'kg', 'trai-cay', 'Tất cả', 'Tất cả', NOW(), NOW()),

('Dâu tây', 120000, '1747497322365-Dâu tây.jpeg', 'Dâu tây tươi ngon, ngọt thanh, giàu vitamin C và chất chống oxy hóa. Được trồng tại Đà Lạt.', 'Dâu tây Đà Lạt tươi ngon', 30, 'Đà Lạt', 'kg', 'trai-cay', 'Tất cả', 'Tất cả', NOW(), NOW()),

('Kiwi', 80000, '1747497719855-Kiwi.jpeg', 'Kiwi xanh tươi, chua ngọt, giàu vitamin C và chất xơ. Tốt cho hệ tiêu hóa và miễn dịch.', 'Kiwi xanh tươi chua ngọt', 40, 'Nhập khẩu', 'kg', 'trai-cay', 'Tăng cường miễn dịch', 'Tất cả', NOW(), NOW()),

('Quả bơ', 60000, '1747497909082-Quả bơ.jpeg', 'Bơ sáp dẻo, béo ngậy, giàu chất béo tốt và vitamin E. Thích hợp làm sinh tố hoặc ăn kèm bánh mì.', 'Bơ sáp dẻo béo ngậy', 35, 'Đắk Lắk', 'kg', 'trai-cay', 'Tất cả', 'Tất cả', NOW(), NOW()),

('Việt quất', 150000, '1748011042944-Việt quất.jpeg', 'Việt quất tươi ngon, ngọt thanh, giàu chất chống oxy hóa. Tốt cho não bộ và tim mạch.', 'Việt quất tươi ngon', 25, 'Nhập khẩu', 'kg', 'trai-cay', 'Tốt cho não bộ', 'Tất cả', NOW(), NOW()),

('Trái cây nho', 70000, '1747498723303-Trái cây nho.jpeg', 'Nho tươi ngọt, mọng nước, giàu resveratrol và vitamin K. Tốt cho tim mạch và da.', 'Nho tươi mọng nước', 45, 'Ninh Thuận', 'kg', 'trai-cay', 'Làm đẹp', 'Tất cả', NOW(), NOW()),

-- Thực phẩm giàu protein
('Thịt heo ba chỉ', 120000, '1747498422701-Thịt heo tươi ngon.png', 'Thịt heo ba chỉ tươi ngon, đảm bảo chất lượng. Thích hợp để nướng, kho hoặc chiên.', 'Thịt heo ba chỉ tươi', 30, 'Đồng Nai', 'kg', 'thuc-pham-giau-protein', 'Người lớn', 'Người lớn', NOW(), NOW()),

('Thịt bò nạc', 250000, '1747498354121-Thịt bò nạc.jpeg', 'Thịt bò nạc tươi ngon, giàu protein và sắt. Thích hợp để nướng, xào hoặc làm bít tết.', 'Thịt bò nạc tươi', 20, 'Bình Phước', 'kg', 'thuc-pham-giau-protein', 'Người lớn', 'Người lớn', NOW(), NOW()),

('Ức gà', 150000, '1747498873816-Ức gà.jpeg', 'Ức gà tươi ngon, ít béo, giàu protein. Thích hợp cho người ăn kiêng và tập gym.', 'Ức gà tươi ít béo', 40, 'Đồng Nai', 'kg', 'thuc-pham-giau-protein', 'Tập gym', 'Vận động viên', NOW(), NOW()),

('Cá hồi', 350000, '1747496368238-Cá hồi.jpeg', 'Cá hồi tươi ngon, giàu omega-3 và protein. Tốt cho tim mạch và não bộ.', 'Cá hồi tươi giàu omega-3', 15, 'Nhập khẩu', 'kg', 'thuc-pham-giau-protein', 'Tốt cho tim mạch', 'Tất cả', NOW(), NOW()),

('Trứng', 35000, '1747498795092-Trứng.jpeg', 'Trứng gà tươi ngon, giàu protein và vitamin D. Đảm bảo chất lượng và an toàn.', 'Trứng gà tươi ngon', 200, 'Đồng Nai', 'chục', 'thuc-pham-giau-protein', 'Bổ sung dinh dưỡng', 'Tất cả', NOW(), NOW()),

('Phô mai', 80000, '1747497829523-phomat.png', 'Phô mai béo ngậy, giàu canxi và protein. Thích hợp để ăn kèm bánh mì hoặc làm pizza.', 'Phô mai béo ngậy', 50, 'Nhập khẩu', 'kg', 'thuc-pham-giau-protein', 'Tốt cho xương', 'Trẻ em', NOW(), NOW()),

-- Thực phẩm chứa tinh bột
('Gạo ST25', 35000, '1747497376759-Gạo lứt.jpeg', 'Gạo ST25 thơm ngon, dẻo cơm, đảm bảo chất lượng. Gạo đặc sản Sóc Trăng.', 'Gạo ST25 thơm dẻo', 200, 'Sóc Trăng', 'kg', 'thuc-pham-chua-tinh-bot', 'Tất cả', 'Tất cả', NOW(), NOW()),

('Gạo lứt', 40000, '1747497376759-Gạo lứt.jpeg', 'Gạo lứt giàu chất xơ và vitamin B. Tốt cho sức khỏe và người ăn kiêng.', 'Gạo lứt giàu chất xơ', 150, 'An Giang', 'kg', 'thuc-pham-chua-tinh-bot', 'Giảm cân', 'Người ăn kiêng', NOW(), NOW()),

('Bột yến mạch', 120000, '1747496280880-Bột yến mạch.jpeg', 'Bột yến mạch nguyên chất, giàu chất xơ và protein. Thích hợp cho bữa sáng lành mạnh.', 'Bột yến mạch nguyên chất', 80, 'Nhập khẩu', 'kg', 'thuc-pham-chua-tinh-bot', 'Giảm cân', 'Người ăn kiêng', NOW(), NOW()),

('Bánh mỳ', 20000, '1747496175426-Bánh mỳ.jpeg', 'Bánh mỳ tươi nóng, giòn bên ngoài, mềm bên trong. Làm từ bột mì chất lượng cao.', 'Bánh mỳ tươi nóng', 100, 'TP.HCM', 'ổ', 'thuc-pham-chua-tinh-bot', 'Tất cả', 'Tất cả', NOW(), NOW()),

-- Thức uống
('Nước ép cam', 25000, '1747497775219-Nước ép cần tây.jpeg', 'Nước ép cam tươi nguyên chất, không đường, giàu vitamin C. Tốt cho sức khỏe và da.', 'Nước ép cam tươi nguyên chất', 40, 'TP.HCM', 'chai', 'thuc-uong', 'Tăng cường miễn dịch', 'Tất cả', NOW(), NOW()),

('Sữa chua', 30000, '1747498085334-Sữa chua.jpeg', 'Sữa chua tự nhiên, giàu probiotics, tốt cho hệ tiêu hóa. Không đường, ít béo.', 'Sữa chua tự nhiên', 60, 'TP.HCM', 'hộp', 'thuc-uong', 'Tốt cho tiêu hóa', 'Tất cả', NOW(), NOW()),

('Sữa công thức', 180000, '1747498155953-Sữa công thức.jpeg', 'Sữa công thức cho trẻ em, giàu DHA và các dưỡng chất thiết yếu. Đảm bảo an toàn.', 'Sữa công thức cho trẻ', 30, 'Nhập khẩu', 'hộp', 'thuc-uong', 'Bổ sung dinh dưỡng', 'Trẻ em', NOW(), NOW()),

('Sữa ít đường', 35000, '1747498221958-Sữa ít đường.jpeg', 'Sữa tươi ít đường, giàu canxi và protein. Phù hợp cho người ăn kiêng.', 'Sữa tươi ít đường', 50, 'TP.HCM', 'hộp', 'thuc-uong', 'Giảm cân', 'Người ăn kiêng', NOW(), NOW()),

('Trà xanh không đường', 15000, '1747498629424-Trà xanh không đường.jpeg', 'Trà xanh tự nhiên, không đường, giàu chất chống oxy hóa. Tốt cho sức khỏe và giảm cân.', 'Trà xanh không đường', 80, 'Thái Nguyên', 'chai', 'thuc-uong', 'Giảm cân', 'Người ăn kiêng', NOW(), NOW()),

-- Thực phẩm khác
('Dầu oliu', 120000, '1747497194990-Dầu oliu.jpeg', 'Dầu oliu nguyên chất, giàu chất béo tốt và vitamin E. Thích hợp để nấu ăn và làm salad.', 'Dầu oliu nguyên chất', 40, 'Nhập khẩu', 'chai', 'khac', 'Tốt cho tim mạch', 'Tất cả', NOW(), NOW()),

('Hạnh nhân', 200000, '1747497422589-Hạnh Nhân.jpeg', 'Hạnh nhân rang, giòn béo, giàu protein và chất béo tốt. Tốt cho tim mạch.', 'Hạnh nhân rang giòn', 25, 'Nhập khẩu', 'kg', 'khac', 'Tốt cho tim mạch', 'Tất cả', NOW(), NOW()),

('Hạt', 150000, '1747497616934-Hạt.jpg', 'Hỗn hợp hạt dinh dưỡng, giàu protein và chất béo tốt. Bao gồm hạnh nhân, óc chó, hạt điều.', 'Hỗn hợp hạt dinh dưỡng', 30, 'Nhập khẩu', 'kg', 'khac', 'Tất cả', 'Tất cả', NOW(), NOW()),

('Cháo bột gạo', 30000, '1747496999896-Cháo bột gạo.jpeg', 'Cháo bột gạo cho trẻ em, mềm mịn, dễ tiêu hóa. Giàu dinh dưỡng cho bé.', 'Cháo bột gạo cho trẻ', 50, 'TP.HCM', 'hộp', 'khac', 'Trẻ em', 'Trẻ em', NOW(), NOW()),

('Rau củ nghiền', 40000, '1747497974884-Rau củ nghiền.jpeg', 'Rau củ nghiền cho trẻ em, giàu vitamin và khoáng chất. Dễ tiêu hóa và an toàn.', 'Rau củ nghiền cho trẻ', 40, 'TP.HCM', 'hộp', 'khac', 'Trẻ em', 'Trẻ em', NOW(), NOW()),

-- Thêm sản phẩm để tăng tính đa dạng cho lọc
-- Rau củ bổ sung
('Rau muống', 15000, '1747496680924-Cái bó xôi.jpeg', 'Rau muống tươi xanh, giòn ngon, giàu chất xơ và vitamin. Thích hợp để xào hoặc luộc.', 'Rau muống tươi xanh', 90, 'Đồng bằng sông Cửu Long', 'kg', 'rau', 'Người lớn', 'Tất cả', NOW(), NOW()),

('Rau dền', 18000, '1747496680924-Cái bó xôi.jpeg', 'Rau dền đỏ tươi ngon, giàu sắt và canxi. Tốt cho máu và xương.', 'Rau dền đỏ tươi ngon', 85, 'Miền Bắc', 'kg', 'rau', 'Người lớn', 'Tất cả', NOW(), NOW()),

('Rau mồng tơi', 20000, '1747496680924-Cái bó xôi.jpeg', 'Rau mồng tơi tươi xanh, mát lành, giàu vitamin A và chất xơ. Thích hợp nấu canh.', 'Rau mồng tơi tươi xanh', 75, 'Miền Bắc', 'kg', 'rau', 'Người lớn', 'Tất cả', NOW(), NOW()),

('Đậu cô ve', 35000, '1747496803058-Cải xoong.jpeg', 'Đậu cô ve tươi giòn, giàu protein thực vật và chất xơ. Thích hợp để xào hoặc luộc.', 'Đậu cô ve tươi giòn', 60, 'Đà Lạt', 'kg', 'rau', 'Người lớn', 'Tất cả', NOW(), NOW()),

('Bí đỏ', 25000, '1747497376759-Gạo lứt.jpeg', 'Bí đỏ ngọt bùi, giàu beta-carotene và vitamin A. Tốt cho mắt và da.', 'Bí đỏ ngọt bùi', 55, 'Đà Lạt', 'kg', 'rau', 'Tất cả', 'Tất cả', NOW(), NOW()),

-- Củ bổ sung
('Khoai tây', 22000, '1747497663680-Khoai lang.jpeg', 'Khoai tây tươi ngon, giàu tinh bột và kali. Thích hợp để chiên, nướng hoặc luộc.', 'Khoai tây tươi ngon', 65, 'Đà Lạt', 'kg', 'cu', 'Tất cả', 'Tất cả', NOW(), NOW()),

('Củ cải trắng', 18000, '1747497663680-Khoai lang.jpeg', 'Củ cải trắng tươi giòn, mát lành, giàu vitamin C. Thích hợp để nấu canh hoặc làm dưa.', 'Củ cải trắng tươi giòn', 70, 'Miền Bắc', 'kg', 'cu', 'Người lớn', 'Tất cả', NOW(), NOW()),

('Hành tây', 30000, '1747496471293-Cải bắp.png', 'Hành tây tươi ngon, giàu chất chống oxy hóa. Thích hợp để xào hoặc làm salad.', 'Hành tây tươi ngon', 80, 'Đà Lạt', 'kg', 'cu', 'Người lớn', 'Tất cả', NOW(), NOW()),

('Tỏi', 80000, '1747496471293-Cải bắp.png', 'Tỏi tươi ngon, giàu allicin, tốt cho tim mạch và miễn dịch. Thích hợp làm gia vị.', 'Tỏi tươi ngon', 50, 'Lý Sơn', 'kg', 'cu', 'Người lớn', 'Tất cả', NOW(), NOW()),

-- Trái cây bổ sung
('Xoài', 50000, '1747497140138-Chuối.webp', 'Xoài chín vàng, ngọt thơm, giàu vitamin A và C. Tốt cho da và mắt.', 'Xoài chín vàng ngọt thơm', 45, 'Đồng bằng sông Cửu Long', 'kg', 'trai-cay', 'Tất cả', 'Tất cả', NOW(), NOW()),

('Ổi', 35000, '1747497140138-Chuối.webp', 'Ổi giòn ngọt, giàu vitamin C gấp 4 lần cam. Tốt cho hệ miễn dịch.', 'Ổi giòn ngọt', 55, 'Miền Nam', 'kg', 'trai-cay', 'Tăng cường miễn dịch', 'Tất cả', NOW(), NOW()),

('Thanh long', 40000, '1747497140138-Chuối.webp', 'Thanh long đỏ tươi ngon, mát lành, giàu chất xơ và vitamin C.', 'Thanh long đỏ tươi ngon', 50, 'Bình Thuận', 'kg', 'trai-cay', 'Tất cả', 'Tất cả', NOW(), NOW()),

('Dưa hấu', 25000, '1747497140138-Chuối.webp', 'Dưa hấu mát ngọt, giàu nước và lycopene. Giải nhiệt tốt cho mùa hè.', 'Dưa hấu mát ngọt', 60, 'An Giang', 'kg', 'trai-cay', 'Tất cả', 'Tất cả', NOW(), NOW()),

('Mít', 45000, '1747497140138-Chuối.webp', 'Mít chín thơm, ngọt đậm, giàu vitamin C và chất xơ. Tốt cho tiêu hóa.', 'Mít chín thơm ngọt đậm', 40, 'Miền Nam', 'kg', 'trai-cay', 'Tất cả', 'Tất cả', NOW(), NOW()),

('Chôm chôm', 60000, '1747497140138-Chuối.webp', 'Chôm chôm tươi ngon, ngọt thanh, giàu vitamin C và chất chống oxy hóa.', 'Chôm chôm tươi ngon', 35, 'Bến Tre', 'kg', 'trai-cay', 'Tất cả', 'Tất cả', NOW(), NOW()),

-- Thực phẩm giàu protein bổ sung
('Thịt heo nạc', 100000, '1747498422701-Thịt heo tươi ngon.png', 'Thịt heo nạc tươi ngon, ít mỡ, giàu protein. Thích hợp cho người ăn kiêng.', 'Thịt heo nạc tươi', 35, 'Đồng Nai', 'kg', 'thuc-pham-giau-protein', 'Giảm cân', 'Người ăn kiêng', NOW(), NOW()),

('Thịt gà nguyên con', 80000, '1747498873816-Ức gà.jpeg', 'Thịt gà nguyên con tươi ngon, đảm bảo chất lượng. Thích hợp để nấu nhiều món.', 'Thịt gà nguyên con tươi', 25, 'Đồng Nai', 'kg', 'thuc-pham-giau-protein', 'Bổ sung dinh dưỡng', 'Tất cả', NOW(), NOW()),

('Cá basa', 120000, '1747498422701-Thịt heo tươi ngon.png', 'Cá basa tươi ngon, giàu protein và omega-3. Thích hợp để chiên hoặc kho.', 'Cá basa tươi ngon', 30, 'Đồng bằng sông Cửu Long', 'kg', 'thuc-pham-giau-protein', 'Tất cả', 'Tất cả', NOW(), NOW()),

('Tôm tươi', 200000, '1747498354121-Thịt bò nạc.jpeg', 'Tôm tươi ngon, giàu protein và canxi. Tốt cho xương và cơ bắp.', 'Tôm tươi ngon', 20, 'Cà Mau', 'kg', 'thuc-pham-giau-protein', 'Tốt cho xương', 'Vận động viên', NOW(), NOW()),

('Cua biển', 250000, '1747498873816-Ức gà.jpeg', 'Cua biển tươi ngon, giàu protein và canxi. Đặc sản biển Việt Nam.', 'Cua biển tươi ngon', 15, 'Cà Mau', 'kg', 'thuc-pham-giau-protein', 'Người lớn', 'Người lớn', NOW(), NOW()),

('Đậu phụ', 25000, '1747497829523-phomat.png', 'Đậu phụ tươi ngon, giàu protein thực vật và canxi. Thích hợp cho người ăn chay.', 'Đậu phụ tươi ngon', 100, 'TP.HCM', 'kg', 'thuc-pham-giau-protein', 'Bổ sung dinh dưỡng', 'Người ăn chay', NOW(), NOW()),

-- Thực phẩm chứa tinh bột bổ sung
('Mì gói', 15000, '1747496175426-Bánh mỳ.jpeg', 'Mì gói tiện lợi, thơm ngon, đầy đủ dinh dưỡng. Thích hợp cho bữa ăn nhanh.', 'Mì gói tiện lợi', 200, 'TP.HCM', 'gói', 'thuc-pham-chua-tinh-bot', 'Tất cả', 'Tất cả', NOW(), NOW()),

('Bánh bao', 25000, '1747496175426-Bánh mỳ.jpeg', 'Bánh bao tươi nóng, mềm thơm, nhân thịt đậm đà. Thích hợp cho bữa sáng.', 'Bánh bao tươi nóng', 80, 'TP.HCM', 'cái', 'thuc-pham-chua-tinh-bot', 'Tất cả', 'Tất cả', NOW(), NOW()),

('Bánh mì sandwich', 30000, '1747496175426-Bánh mỳ.jpeg', 'Bánh mì sandwich mềm thơm, thích hợp làm bữa sáng hoặc bữa phụ.', 'Bánh mì sandwich', 70, 'TP.HCM', 'ổ', 'thuc-pham-chua-tinh-bot', 'Tất cả', 'Tất cả', NOW(), NOW()),

('Khoai tây chiên', 40000, '1747497663680-Khoai lang.jpeg', 'Khoai tây chiên giòn tan, thơm ngon. Thích hợp làm món ăn vặt.', 'Khoai tây chiên giòn', 60, 'TP.HCM', 'gói', 'thuc-pham-chua-tinh-bot', 'Trẻ em', 'Tất cả', NOW(), NOW()),

-- Thức uống bổ sung
('Nước ép dưa hấu', 30000, '1747497775219-Nước ép cần tây.jpeg', 'Nước ép dưa hấu tươi mát, ngọt thanh, giàu vitamin và khoáng chất.', 'Nước ép dưa hấu tươi mát', 45, 'TP.HCM', 'chai', 'thuc-uong', 'Tất cả', 'Tất cả', NOW(), NOW()),

('Nước ép cà rốt', 35000, '1747497775219-Nước ép cần tây.jpeg', 'Nước ép cà rốt tươi nguyên chất, giàu vitamin A. Tốt cho mắt và da.', 'Nước ép cà rốt tươi', 40, 'TP.HCM', 'chai', 'thuc-uong', 'Tốt cho mắt', 'Tất cả', NOW(), NOW()),

('Sữa tươi không đường', 32000, '1747498221958-Sữa ít đường.jpeg', 'Sữa tươi không đường, giàu canxi và protein. Phù hợp cho người ăn kiêng.', 'Sữa tươi không đường', 55, 'TH True Milk', 'hộp', 'thuc-uong', 'Tốt cho xương', 'Người ăn kiêng', NOW(), NOW()),

('Nước dừa tươi', 25000, '1747497775219-Nước ép cần tây.jpeg', 'Nước dừa tươi mát, giàu kali và chất điện giải. Giải nhiệt tốt cho mùa hè.', 'Nước dừa tươi mát', 50, 'Bến Tre', 'trái', 'thuc-uong', 'Tất cả', 'Tất cả', NOW(), NOW()),

('Cà phê đen', 20000, '1747498629424-Trà xanh không đường.jpeg', 'Cà phê đen nguyên chất, không đường, giàu chất chống oxy hóa. Tốt cho sức khỏe.', 'Cà phê đen nguyên chất', 100, 'Buôn Ma Thuột', 'ly', 'thuc-uong', 'Người lớn', 'Người lớn', NOW(), NOW()),

-- Thực phẩm khác bổ sung
('Mật ong', 150000, '1747497194990-Dầu oliu.jpeg', 'Mật ong nguyên chất, giàu enzyme và chất chống oxy hóa. Tốt cho sức khỏe và làm đẹp.', 'Mật ong nguyên chất', 30, 'Tây Nguyên', 'chai', 'khac', 'Làm đẹp', 'Tất cả', NOW(), NOW()),

('Giấm táo', 80000, '1747497194990-Dầu oliu.jpeg', 'Giấm táo nguyên chất, giàu enzyme và acid acetic. Tốt cho tiêu hóa và giảm cân.', 'Giấm táo nguyên chất', 25, 'Nhập khẩu', 'chai', 'khac', 'Giảm cân', 'Người ăn kiêng', NOW(), NOW()),

('Bột nghệ', 100000, '1747496280880-Bột yến mạch.jpeg', 'Bột nghệ nguyên chất, giàu curcumin. Tốt cho dạ dày và chống viêm.', 'Bột nghệ nguyên chất', 40, 'Nghệ An', 'kg', 'khac', 'Tăng cường miễn dịch', 'Tất cả', NOW(), NOW()),

('Yến sào', 500000, '1747498155953-Sữa công thức.jpeg', 'Yến sào cao cấp, giàu protein và acid amin. Tốt cho sức khỏe và làm đẹp da.', 'Yến sào cao cấp', 10, 'Khánh Hòa', 'hộp', 'khac', 'Làm đẹp', 'Tất cả', NOW(), NOW());
