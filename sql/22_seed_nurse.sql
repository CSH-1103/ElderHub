-- 22 基础数据（示例）：护理级别与护理项目
-- 说明：也可以在系统「护理管理-护理级别 / 护理项目」页面自行新增，不执行本脚本不影响系统运行。
-- 若表中已有数据，可先执行：DELETE FROM nurse_level_item; DELETE FROM nurse_content; DELETE FROM nurse_level;

USE elderhub;

INSERT INTO nurse_level (id, level_name, level_status, is_deleted) VALUES
(1, '一级护理', 1, 0),
(2, '二级护理', 1, 0);

INSERT INTO nurse_content (id, serial_number, nursing_name, service_price, message, execution_cycle, execution_times, status, is_deleted) VALUES
(1, 'HL001', '晨间护理', 30.00, '起床、洗漱、整理床铺', '每日', '1次/天', 1, 0),
(2, 'HL002', '口腔护理', 20.00, '早晚口腔清洁', '每日', '2次/天', 1, 0),
(3, '008', '临床护理', 30.00, '', '每日', '3次/天', 1, 0);

-- 二级护理包含：口腔护理
INSERT INTO nurse_level_item (id, level_id, item_id) VALUES
(1, 2, 2);
