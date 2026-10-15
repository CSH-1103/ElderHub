-- 05 房间信息表

USE elderhub;
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '房间ID',
  `room_floor` varchar(10) NOT NULL COMMENT '所在楼层',
  `room_no` int NOT NULL COMMENT '房间编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房间信息表';
