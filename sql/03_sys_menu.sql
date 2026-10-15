-- 03 菜单路由表

USE elderhub;
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menus_index` varchar(5) DEFAULT NULL COMMENT '一级菜单索引',
  `title` varchar(50) NOT NULL COMMENT '菜单标题',
  `icon` varchar(50) NOT NULL COMMENT '菜单图标',
  `path` varchar(100) DEFAULT NULL COMMENT '前端路由路径',
  `parent_id` int DEFAULT '0' COMMENT '父菜单ID（0为一级菜单）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单路由表';
