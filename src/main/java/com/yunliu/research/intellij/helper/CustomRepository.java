package com.yunliu.research.intellij.helper;

import com.intellij.openapi.updateSettings.impl.UpdateSettings;

import java.lang.reflect.Method;

/**
 * @author yunliu
 */
public class CustomRepository {

    public static void checkAndAdd() {
        Method method = ReflectionHelper.getMethod(UpdateSettings.class, "setThirdPartyPluginsAllowed", boolean.class);
        if (method != null) {
            try {
                method.invoke(UpdateSettings.getInstance(), true);
            } catch (Exception e) {
                NotificationHelper.showError(null, "Enable third party plugins failed!");
            }
        }
    }
}
