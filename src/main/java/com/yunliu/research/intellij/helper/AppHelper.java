package com.yunliu.research.intellij.helper;

import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.BuildNumber;

/**
 * @author yunliu
 */
public class AppHelper {
    private static final String IDEA = "IDEA";

    public static void restart() {
        ApplicationManager.getApplication().invokeLater(() -> ApplicationManager.getApplication().restart());
    }

    public static String getProductName() {
        String productName = Constants.IDE_NAME;

        if (IDEA.equals(productName)) {
            return productName.toLowerCase();
        }

        return productName;
    }

    public static BuildNumber getBuildNumber() {
        return ApplicationInfo.getInstance().getBuild();
    }
}
