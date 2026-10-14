-- 18 膳食日历表
DROP TABLE IF EXISTS `meal`;
CREATE TABLE `meal` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `week_day` varchar(10) NOT NULL COMMENT '星期几',
  `meal_type` int DEFAULT NULL COMMENT '餐次类型（1:早餐 2:午餐 3:晚餐）',
  `food_id` int DEFAULT NULL COMMENT '食品ID',
  `taste` varchar(30) DEFAULT NULL COMMENT '口味要求（少糖/少盐等）',
  `status` int DEFAULT '1' COMMENT '状态（1:启用 2:停用）',
  `is_deleted` int DEFAULT '0' COMMENT '逻辑删除（0:正常 1:删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='膳食日历表';
