package com.it.elderhub.utils;

/**
 * 星期格式工具：统一为 "星期X" 存储（与前端一致）
 * 兼容 周一 / 周1 / 星期一 / 礼拜一 / 一 / 1 等写法
 */
public class WeekDayUtils {

    private static final String[] NAMES = {"一", "二", "三", "四", "五", "六", "日"};

    /**
     * 归一化星期字符串，非法返回 null
     */
    public static String normalize(String weekDay) {
        if (weekDay == null) {
            return null;
        }
        String s = weekDay.trim()
                .replace("星期", "")
                .replace("礼拜", "")
                .replace("周", "");
        if (s.isEmpty()) {
            return null;
        }
        char c = s.charAt(0);
        if (c >= '1' && c <= '7') {
            return "星期" + NAMES[c - '1'];
        }
        if ("一二三四五六".indexOf(c) >= 0) {
            return "星期" + c;
        }
        if (c == '日' || c == '天') {
            return "星期日";
        }
        return null;
    }
}
