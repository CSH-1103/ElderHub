-- 21 基础数据：角色与登录账号（新环境初始化必执行）
-- 密码为 BCrypt 加密存储，初始账号如下：
--   admin  / 123456   （管理员）
--   butler / 123456   （健康管家）
--   LYS    / 1234     （健康管家）
-- 若表中已有数据，可先执行：DELETE FROM user; DELETE FROM role;

USE elderhub;

INSERT INTO role (id, name, is_deleted) VALUES
(1, '管理员', 0),
(2, '健康管家', 0);

INSERT INTO user (id, nickname, username, password, sex, email, phone_number, role_id, create_by, is_deleted) VALUES
(1, 'LYS', 'LYS', '$2a$10$jv0swEvUzmBNVe65KhxL..ZggAZvBtfKwB4N4HdDVJv8boyY8uztm', 0, NULL, '', 2, 0, 0),
(2, '系统管理员', 'admin', '$2b$10$iycO3Jd7xFN1dfFBHHSkteCsG0GFov.G4tDdh7lHPJTsz1WJ6NumK', 1, NULL, '13800000001', 1, 0, 0),
(3, '管家小李', 'butler', '$2b$10$iycO3Jd7xFN1dfFBHHSkteCsG0GFov.G4tDdh7lHPJTsz1WJ6NumK', 0, NULL, '13800000002', 2, 0, 0);
