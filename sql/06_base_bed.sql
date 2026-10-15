-- 06 床位信息表

USE elderhub;
DROP TABLE IF EXISTS `bed`;
CREATE TABLE `bed` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '床位ID',
  `bed_no` varchar(20) NOT NULL COMMENT '床位编号',
  `room_no` int NOT NULL COMMENT '所属房间编号',
  `bed_status` int NOT NULL DEFAULT '1' COMMENT '床位状态（1:空闲 2:已入住 3:外出）',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='床位信息表';
