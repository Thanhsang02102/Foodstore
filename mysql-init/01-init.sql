ALTER DATABASE foodstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS roles (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  description TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  create_day_time VARCHAR(255),
  update_day_time VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  full_name VARCHAR(255) NOT NULL,
  address VARCHAR(255),
  phone VARCHAR(255),
  avatar VARCHAR(255),
  provider VARCHAR(255),
  role_id INT,
  create_day_time VARCHAR(255),
  update_day_time VARCHAR(255),
  CONSTRAINT uk_users_email UNIQUE (email),
  CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS products (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  price DOUBLE NOT NULL,
  image VARCHAR(255),
  detail_desc MEDIUMTEXT NOT NULL,
  short_desc VARCHAR(255) NOT NULL,
  quantity BIGINT NOT NULL,
  source VARCHAR(255) NOT NULL,
  unit VARCHAR(255) NOT NULL,
  target VARCHAR(255),
  type VARCHAR(255),
  customer_target VARCHAR(255),
  create_day_time VARCHAR(255),
  update_day_time VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS carts (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  sum INT NOT NULL DEFAULT 0,
  user_id BIGINT UNIQUE,
  create_day_time VARCHAR(255),
  update_day_time VARCHAR(255),
  CONSTRAINT fk_carts_user FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS cart_detail (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  quantity BIGINT NOT NULL,
  price DOUBLE NOT NULL,
  cart_id BIGINT,
  product_id BIGINT,
  create_day_time VARCHAR(255),
  update_day_time VARCHAR(255),
  CONSTRAINT fk_cart_detail_cart FOREIGN KEY (cart_id) REFERENCES carts(id),
  CONSTRAINT fk_cart_detail_product FOREIGN KEY (product_id) REFERENCES products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS orders (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  total_price DOUBLE NOT NULL,
  receiver_name VARCHAR(255),
  receiver_address VARCHAR(255),
  receiver_phone VARCHAR(255),
  status VARCHAR(255),
  payment_ref VARCHAR(255),
  payment_status VARCHAR(255),
  payment_method VARCHAR(255),
  user_id BIGINT,
  create_day_time VARCHAR(255),
  update_day_time VARCHAR(255),
  CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS order_detail (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  quantity BIGINT NOT NULL,
  price DOUBLE NOT NULL,
  order_id BIGINT,
  product_id BIGINT,
  create_day_time VARCHAR(255),
  update_day_time VARCHAR(255),
  CONSTRAINT fk_order_detail_order FOREIGN KEY (order_id) REFERENCES orders(id),
  CONSTRAINT fk_order_detail_product FOREIGN KEY (product_id) REFERENCES products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS tokens (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  token VARCHAR(255),
  email VARCHAR(255),
  create_day_time VARCHAR(255),
  update_day_time VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
