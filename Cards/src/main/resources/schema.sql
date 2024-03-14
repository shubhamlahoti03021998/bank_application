CREATE TABLE IF NOT EXISTS `cards` (
  `card_id` int NOT NULL AUTO_INCREMENT,
  `mobile_number` varchar(10) NOT NULL,
  `card_number` varchar(100) NOT NULL,
  `card_type` varchar(100) NOT NULL,
  `total_limit` int NOT NULL,
  `amount_used` int NOT NULL,
  `available_amount` int NOT NULL,
  `created_at` date,
  `created_by` varchar(20),
  `updated_at` date DEFAULT NULL,
  `updated_by` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`card_id`)
);