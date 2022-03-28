package com.yunliu.research.intellij.ui.dialog;

import com.intellij.openapi.ui.DialogWrapper;
import com.yunliu.research.intellij.ui.form.MainForm;

import javax.swing.*;

/**
 * @author yunliu
 */
public class MainDialog extends DialogWrapper {
    public MainDialog(String title) {
        super(true);
        init();
        setTitle(title);
    }

    @Override
    protected JComponent createCenterPanel() {
        MainForm mainForm = new MainForm(getDisposable(), this);

        return mainForm.getContent(getDisposable());
    }

    @Override
    protected JComponent createSouthPanel() {
        return null;
    }
}
