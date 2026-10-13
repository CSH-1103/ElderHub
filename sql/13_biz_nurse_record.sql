-- 13 护理执行记录表
DROP TABLE IF EXISTS `nurse_record`;
CREATE TABLE `nurse_record` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `customer_id` int NOT NULL COMMENT '客户ID',
  `item_id` int NOT NULL COMMENT '护理项目ID',
  `user_id` int NOT NULL COMMENT '执行护理人员ID',
  `nursing_time` datetime NOT NULL COMMENT '护理执行时间',
  `nursing_content` varchar(255) DEFAULT NULL COMMENT '本次护理内容',
  `nursing_count` int NOT NULL COMMENT '本次执行次数',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除（0:正常 1:删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='护理执行记录表';
