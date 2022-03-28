package com.yunliu.research.intellij.listener;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.PreloadingActivity;
import com.intellij.openapi.progress.ProgressIndicator;
import com.yunliu.research.intellij.helper.Constants;
import org.jetbrains.annotations.NotNull;

/**
 * @author yunliu
 */
public class AppInitActivity extends PreloadingActivity {
    @Override
    public void preload(@NotNull ProgressIndicator indicator) {
        ApplicationManager.getApplication().executeOnPooledThread(() -> {
            ActionManager.getInstance().getAction(Constants.RESET_ACTION_ID);
        });
    }
}
