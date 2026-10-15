-- 19 客户膳食配置表

USE elderhub;
DROP TABLE IF EXISTS `customer_meal`;
CREATE TABLE `customer_meal` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `customer_id` int DEFAULT NULL COMMENT '客户ID',
  `week_day` varchar(10) DEFAULT NULL COMMENT '星期几',
  `meal_type` int DEFAULT NULL COMMENT '餐次类型（1:早餐 2:午餐 3:晚餐）',
  `food_id` int DEFAULT NULL COMMENT '食品ID',
  `is_deleted` int DEFAULT '0' COMMENT '逻辑删除（0:正常 1:删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户膳食配置表';
