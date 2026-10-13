-- 08 在住客户/老人表
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '客户ID',
  `customer_name` varchar(20) NOT NULL COMMENT '老人姓名',
  `customer_age` int NOT NULL COMMENT '年龄',
  `customer_sex` int NOT NULL COMMENT '性别（0:男 1:女）',
  `idcard` varchar(20) NOT NULL COMMENT '身份证号',
  `room_no` varchar(20) NOT NULL COMMENT '房间号',
  `building_no` varchar(11) NOT NULL COMMENT '所属楼栋',
  `bed_id` int NOT NULL COMMENT '床位ID',
  `checkin_date` date NOT NULL COMMENT '入住日期',
  `expiration_date` date NOT NULL COMMENT '合同到期日期',
  `contact_tel` varchar(20) NOT NULL COMMENT '联系电话',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `height` varchar(20) DEFAULT NULL COMMENT '身高',
  `weight` varchar(20) DEFAULT NULL COMMENT '体重',
  `blood_type` varchar(20) NOT NULL COMMENT '血型',
  `psychosomatic_state` varchar(255) DEFAULT NULL COMMENT '身心状况描述',
  `attention` varchar(255) DEFAULT NULL COMMENT '护理注意事项',
  `filepath` varchar(50) NOT NULL COMMENT '头像文件路径',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除（0:正常 1:删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='在住客户/老人表';
