-- 12 客户已购护理条目表
DROP TABLE IF EXISTS `customer_nurse_item`;
CREATE TABLE `customer_nurse_item` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '条目ID',
  `customer_id` int NOT NULL COMMENT '客户ID',
  `item_id` int NOT NULL COMMENT '护理项目ID',
  `level_id` int DEFAULT NULL COMMENT '护理等级ID',
  `nurse_number` int NOT NULL COMMENT '购买服务次数',
  `buy_time` date NOT NULL COMMENT '购买日期',
  `maturity_time` date NOT NULL COMMENT '服务到期日期',
  `is_deleted` int DEFAULT '0' COMMENT '逻辑删除（0:正常 1:删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户已购护理条目表';
