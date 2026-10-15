-- 17 客户饮食喜好表

USE elderhub;
DROP TABLE IF EXISTS `customer_preference`;
CREATE TABLE `customer_preference` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '喜好ID',
  `customer_id` int DEFAULT NULL COMMENT '客户ID',
  `preferences` varchar(30) DEFAULT NULL COMMENT '饮食喜好',
  `attention` varchar(30) DEFAULT NULL COMMENT '饮食忌口注意事项',
  `remark` varchar(30) DEFAULT NULL COMMENT '备注',
  `is_deleted` int DEFAULT '0' COMMENT '逻辑删除（0:正常 1:删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户饮食喜好表';
