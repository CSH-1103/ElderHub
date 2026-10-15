-- 16 食品信息表

USE elderhub;
DROP TABLE IF EXISTS `food`;
CREATE TABLE `food` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '食品ID',
  `food_name` varchar(30) DEFAULT NULL COMMENT '食品名称',
  `food_type` varchar(30) DEFAULT NULL COMMENT '食品分类',
  `price` decimal(10,2) DEFAULT NULL COMMENT '食品价格',
  `is_halal` int DEFAULT '0' COMMENT '是否清真（0:否 1:是）',
  `food_img` varchar(100) DEFAULT NULL COMMENT '食品图片路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='食品信息表';
