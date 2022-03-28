package com.yunliu.research.intellij.helper;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

/**
 * @author yunliu
 */
public class NotificationHelper {
    public static void checkAndExpire(AnActionEvent e) {
        DataKey<Notification> notificationKey = DataKey.create("Notification");
        Notification notification = notificationKey.getData(e.getDataContext());
        if (null != notification) {
            notification.expire();
        }
    }

    public static void show(@Nullable Project project, String content, NotificationType type) {
        show(project, content, type, new AnAction[0]);
    }

    public static void show(@Nullable Project project, String content, NotificationType type, AnAction action) {
        show(project, content, type, new AnAction[]{action});
    }

    public static void show(@Nullable Project project, String content, NotificationType type, AnAction[] actions) {
        Notification notification = NotificationGroupManager.getInstance().getNotificationGroup(Constants.PLUGIN_ID_STR)
                .createNotification(content, type);
        for (AnAction action : actions) {
            notification.addAction(action);
        }
        notification.notify(project);

    }

    public static void showError(@Nullable Project project, String content) {
        show(project, content, NotificationType.ERROR);
    }

    public static void showInfo(@Nullable Project project, String content) {
        show(project, content, NotificationType.INFORMATION);
    }
}
