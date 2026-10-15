-- 11 护理等级项目关联表

USE elderhub;
DROP TABLE IF EXISTS `nurse_level_item`;
CREATE TABLE `nurse_level_item` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `level_id` int NOT NULL COMMENT '护理等级ID',
  `item_id` int NOT NULL COMMENT '护理项目ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='护理等级项目关联表';
