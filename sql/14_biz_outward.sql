-- 14 外出登记表

USE elderhub;
DROP TABLE IF EXISTS `outward`;
CREATE TABLE `outward` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '登记ID',
  `customer_id` int NOT NULL COMMENT '客户ID',
  `outgoingreason` varchar(100) NOT NULL COMMENT '外出事由',
  `outgoingtime` datetime NOT NULL COMMENT '外出时间',
  `expectedreturntime` datetime NOT NULL COMMENT '预计回院时间',
  `actualreturntime` datetime DEFAULT NULL COMMENT '实际回院时间',
  `escorted` varchar(50) DEFAULT NULL COMMENT '陪同人姓名',
  `relation` varchar(50) DEFAULT NULL COMMENT '陪同人与老人关系',
  `escortedtel` varchar(50) DEFAULT NULL COMMENT '陪同人联系电话',
  `auditstatus` int NOT NULL DEFAULT '0' COMMENT '审批状态（0:已提交 1:同意 2:拒绝）',
  `auditperson` varchar(50) DEFAULT NULL COMMENT '审批人姓名',
  `audittime` datetime DEFAULT NULL COMMENT '审批时间',
  `remarks` varchar(100) DEFAULT NULL COMMENT '备注',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除（0:正常 1:删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='外出登记表';
