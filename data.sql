CREATE TABLE `users` (
  `user_id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255),
  `email` VARCHAR(255),
  `password` VARCHAR(255),
  `address` TEXT,
  `role` VARCHAR(255),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `product` (
  `product_id` integer PRIMARY KEY,
  `name` varchar(255),
  `description` text,
  `price` decimal,
  `features` text,
  `average_rating` decimal,
  `created_at` timestamp
);

CREATE TABLE `order` (
  `order_id` integer PRIMARY KEY,
  `user_id` integer,
  `total_amount` decimal,
  `order_status` varchar(255),
  `payment_status` varchar(255),
  `created_at` timestamp
);

CREATE TABLE `order_item` (
  `order_item_id` integer PRIMARY KEY,
  `order_id` integer,
  `product_id` integer,
  `quantity` integer,
  `price` decimal
);

CREATE TABLE `payment` (
  `payment_id` integer PRIMARY KEY,
  `order_id` integer,
  `payment_method` varchar(255),
  `payment_amount` decimal,
  `payment_date` timestamp
);

CREATE TABLE `inventory` (
  `product_id` integer PRIMARY KEY,
  `quantity` integer,
  `updated_at` timestamp
);

CREATE TABLE `reviews` (
  `review_id` integer PRIMARY KEY,
  `product_id` integer,
  `user_id` integer,
  `rating` integer COMMENT 'Rating out of 5',
  `review_text` text,
  `created_at` timestamp
);

ALTER TABLE `payment_information` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `reviews` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `reviews` ADD FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`);

ALTER TABLE `order` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `order_item` ADD FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`);

ALTER TABLE `order_item` ADD FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`);

ALTER TABLE `payment` ADD FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`);

ALTER TABLE `inventory` ADD FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`);