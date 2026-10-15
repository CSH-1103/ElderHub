-- 01 系统用户表

USE elderhub;
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `nickname` varchar(20) NOT NULL COMMENT '真实姓名',
  `username` varchar(20) NOT NULL COMMENT '登录账号',
  `password` varchar(100) NOT NULL COMMENT '登录密码',
  `sex` int NOT NULL COMMENT '性别（0:女 1:男）',
  `email` varchar(254) DEFAULT NULL COMMENT '邮箱',
  `phone_number` varchar(20) NOT NULL COMMENT '联系电话',
  `role_id` int NOT NULL COMMENT '角色ID（1:管理员 2:健康管家）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` int NOT NULL COMMENT '创建人ID',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` int DEFAULT NULL COMMENT '更新人ID',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除（0:正常 1:删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';
