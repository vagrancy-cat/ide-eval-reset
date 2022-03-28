package com.yunliu.research.intellij.listener;

import com.intellij.ide.AppLifecycleListener;
import com.yunliu.research.intellij.common.Reset;
import com.yunliu.research.intellij.helper.BrokenPlugins;
import com.yunliu.research.intellij.helper.ResetTimeHelper;

/**
 * @author yunliu
 */
public class AppEventListener implements AppLifecycleListener {
    @Override
    public void projectFrameClosed() {

    }

    @Override
    public void projectOpenFailed() {

    }

    @Override
    public void welcomeScreenDisplayed() {

    }

    @Override
    public void appClosing() {
        BrokenPlugins.fix();
        Reset.touchLicenses();

        if (!Reset.isAutoReset()) {
            return;
        }

        Reset.reset(Reset.getEvalRecords());
        ResetTimeHelper.resetLastResetTime();
    }
}
