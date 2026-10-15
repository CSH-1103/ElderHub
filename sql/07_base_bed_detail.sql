-- 07 床位使用详情表

USE elderhub;
DROP TABLE IF EXISTS `bed_details`;
CREATE TABLE `bed_details` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '详情ID',
  `bed_id` int DEFAULT NULL COMMENT '床位ID',
  `customer_id` int DEFAULT NULL COMMENT '入住客户ID',
  `start_date` date DEFAULT NULL COMMENT '入住开始日期',
  `end_date` date DEFAULT NULL COMMENT '退住结束日期',
  `bed_details` varchar(255) DEFAULT NULL COMMENT '床位详情说明',
  `is_deleted` int DEFAULT '0' COMMENT '逻辑删除（0:正常 1:删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='床位使用详情表';
