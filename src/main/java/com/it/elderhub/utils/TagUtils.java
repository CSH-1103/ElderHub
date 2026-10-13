package com.it.elderhub.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签字符串工具：数据库以逗号分隔存储，接口以数组返回
 */
public class TagUtils {

    /**
     * 将逗号分隔的标签字符串拆分为列表（支持中英文逗号、顿号、分号）
     */
    public static List<String> splitTags(String text) {
        List<String> list = new ArrayList<>();
        if (text == null || text.trim().isEmpty()) {
            return list;
        }
        for (String tag : text.split("[,，、;；]")) {
            String t = tag.trim();
            if (!t.isEmpty()) {
                list.add(t);
            }
        }
        return list;
    }
}
