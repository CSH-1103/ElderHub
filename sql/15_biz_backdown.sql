-- 15 退住登记表

USE elderhub;
DROP TABLE IF EXISTS `backdown`;
CREATE TABLE `backdown` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '登记ID',
  `customer_id` int NOT NULL COMMENT '客户ID',
  `retreattime` datetime NOT NULL COMMENT '退住时间',
  `retreattype` int NOT NULL COMMENT '退住类型（0:正常退住 1:死亡退住 2:保留床位）',
  `retreatreason` varchar(100) DEFAULT NULL COMMENT '退住原因',
  `auditstatus` int NOT NULL DEFAULT '0' COMMENT '审批状态（0:已提交 1:同意 2:拒绝）',
  `auditperson` varchar(100) DEFAULT NULL COMMENT '审批人姓名',
  `audittime` datetime DEFAULT NULL COMMENT '审批时间',
  `remarks` varchar(100) DEFAULT NULL COMMENT '备注',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除（0:正常 1:删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退住登记表';
