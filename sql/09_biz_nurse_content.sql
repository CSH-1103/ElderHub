-- 09 护理项目内容表
DROP TABLE IF EXISTS `nurse_content`;
CREATE TABLE `nurse_content` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '护理项目ID',
  `serial_number` varchar(20) NOT NULL COMMENT '项目编号',
  `nursing_name` varchar(55) NOT NULL COMMENT '护理项目名称',
  `service_price` decimal(10,2) NOT NULL COMMENT '服务价格',
  `message` varchar(100) DEFAULT NULL COMMENT '项目描述',
  `execution_cycle` varchar(20) DEFAULT NULL COMMENT '执行周期',
  `execution_times` varchar(20) DEFAULT NULL COMMENT '执行次数',
  `status` int NOT NULL DEFAULT '1' COMMENT '状态（1:启用 2:停用）',
  `is_deleted` int DEFAULT '0' COMMENT '逻辑删除（0:正常 1:删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='护理项目内容表';
