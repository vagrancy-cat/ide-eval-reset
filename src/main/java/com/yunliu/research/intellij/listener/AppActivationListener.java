package com.yunliu.research.intellij.listener;

import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.application.ApplicationActivationListener;
import com.intellij.openapi.wm.IdeFrame;
import com.yunliu.research.intellij.helper.NotificationHelper;
import com.yunliu.research.intellij.helper.ResetTimeHelper;
import com.yunliu.research.intellij.common.Reset;
import com.yunliu.research.intellij.helper.BrokenPlugins;
import com.yunliu.research.intellij.helper.Constants;
import org.jetbrains.annotations.NotNull;

/**
 * @author yunliu
 */
public class AppActivationListener implements ApplicationActivationListener {
    private boolean tipped = false;

    @Override
    public void applicationActivated(@NotNull IdeFrame ideFrame) {
        BrokenPlugins.fix();

        if (tipped || !ResetTimeHelper.overResetPeriod()) {
            return;
        }

        tipped = true;
        AnAction action = ActionManager.getInstance().getAction(Constants.RESET_ACTION_ID);
        NotificationType type = NotificationType.INFORMATION;
        String message = "It has been a long time since the last reset!\nWould you like to reset it again?";
        if (Reset.isAutoReset()) {
            action = ActionManager.getInstance().getAction(Constants.RESTART_ACTION_ID);
            type = NotificationType.WARNING;
        }

        NotificationHelper.show(null, message, type, action);
    }

    @Override
    public void applicationDeactivated(@NotNull IdeFrame ideFrame) {
        applicationActivated(ideFrame);
    }


}
