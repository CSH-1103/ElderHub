-- 10 护理等级表
DROP TABLE IF EXISTS `nurse_level`;
CREATE TABLE `nurse_level` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '护理等级ID',
  `level_name` varchar(20) NOT NULL COMMENT '等级名称',
  `level_status` int DEFAULT '1' COMMENT '状态（1:启用 2:停用）',
  `is_deleted` int DEFAULT '0' COMMENT '逻辑删除（0:正常 1:删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='护理等级表';
