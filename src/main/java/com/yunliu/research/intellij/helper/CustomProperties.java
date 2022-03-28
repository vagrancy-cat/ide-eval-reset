package com.yunliu.research.intellij.helper;

/**
 * @author yunliu
 */
public class CustomProperties {
    public static void fix() {
        String key = "idea.ignore.disabled.plugins";
        System.clearProperty(key);
    }
}
