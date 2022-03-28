package com.yunliu.research.intellij.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.yunliu.research.intellij.helper.BrokenPlugins;
import com.yunliu.research.intellij.helper.Constants;
import com.yunliu.research.intellij.helper.NotificationHelper;
import com.yunliu.research.intellij.listener.ListenerConnector;
import com.yunliu.research.intellij.ui.dialog.MainDialog;

/**
 * @author yunliu
 */
public class ResetAction extends AnAction implements DumbAware {
    private static final Logger LOG = Logger.getInstance(BrokenPlugins.class);

    public ResetAction() {
        super(Constants.ACTION_NAME, "Reset my ide information", AllIcons.General.Reset);

        AnAction optionsGroup = ActionManager.getInstance().getAction("WelcomeScreen.Options");
        if ((optionsGroup instanceof DefaultActionGroup)) {
            ((DefaultActionGroup) optionsGroup).add(this);
        }

        ListenerConnector.setup();
    }


    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();

        NotificationHelper.checkAndExpire(e);

        if (project == null) {
            MainDialog mainDialog = new MainDialog(Constants.ACTION_NAME);
            mainDialog.show();
            return;
        }

        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow(Constants.ACTION_NAME);
        if (toolWindow != null) {
            toolWindow.show();
        } else {
            LOG.error(project.getName() + "失败");
        }

    }
}
