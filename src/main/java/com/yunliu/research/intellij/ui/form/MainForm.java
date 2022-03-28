package com.yunliu.research.intellij.ui.form;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.Disposer;
import com.yunliu.research.intellij.common.EvalRecord;
import com.yunliu.research.intellij.common.Reset;
import com.yunliu.research.intellij.helper.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author yunliu
 */
public class MainForm {
    private static final Logger LOG = Logger.getInstance(BrokenPlugins.class);
    private JPanel rootPanel;
    private JButton btnReset;
    private JList<String> lstMain;
    private JLabel lblLastResetTime;
    private JButton btnReload;
    private JLabel lblFound;
    private JLabel lblLastResetTimeLabel;
    private JCheckBox chkResetAuto;
    private JLabel lblVersion;
    private JCheckBox chkAutoLogout;

    private static final String MESSAGE = "Your IDE will restart after reset!\nAre your sure to reset?";

    private DialogWrapper dialogWrapper;
    private DefaultListModel<String> listModel = new DefaultListModel<>();

    public MainForm(Disposable disposable) {
        this(disposable, null);
    }

    public MainForm(Disposable disposable, DialogWrapper wrapper) {
        this.dialogWrapper = wrapper;

        Disposer.register(disposable, () -> {
            rootPanel.removeAll();

            listModel = new DefaultListModel<>();
            dialogWrapper = null;
        });
    }

    private static void boldFont(Component component) {
        Font font = component.getFont();
        component.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
    }

    private static void addActionEventListener(final AbstractButton button, final ActionListener listener, Disposable disposable) {
        button.addActionListener(listener);
        Disposer.register(disposable, () -> button.removeActionListener(listener));
    }

    public JPanel getContent(Disposable disposable) {
        Disposer.register(disposable, () -> rootPanel.removeAll());

        boldFont(lblFound);
        boldFont(lblLastResetTimeLabel);
        reloadLastResetTime();

        lblVersion.setText("v" + PluginHelper.getPluginVersion());

        chkAutoLogout.setSelected(Reset.isAutoLogout());
        addActionEventListener(chkAutoLogout, e -> Reset.setAutoLogout(chkAutoLogout.isSelected()), disposable);

        chkResetAuto.setSelected(Reset.isAutoReset());
        addActionEventListener(chkResetAuto, e -> Reset.setAutoReset(chkResetAuto.isSelected()), disposable);

        lstMain.setModel(listModel);
        reloadRecordItems();

        btnReload.setIcon(AllIcons.Actions.Refresh);
        addActionEventListener(btnReload, e -> {
            reloadLastResetTime();
            reloadRecordItems();
        }, disposable);

        btnReset.setIcon(AllIcons.General.Reset);
        addActionEventListener(btnReset, e -> resetEvalItems(), disposable);

        if (null != dialogWrapper) {
            dialogWrapper.getRootPane().setDefaultButton(btnReset);
            rootPanel.setMinimumSize(new Dimension(640, 260));
        }

        return rootPanel;
    }

    private void reloadLastResetTime() {
        lblLastResetTime.setText(ResetTimeHelper.getLastResetTimeStr());
    }

    private void reloadRecordItems() {
        listModel.clear();
        Reset.touchLicenses();
        List<EvalRecord> recordItemList = Reset.getEvalRecords();
        for (EvalRecord record : recordItemList) {
            listModel.addElement(record.toString());
        }
        listModel.addElement("Reload: " + DateTime.getStringFromTimestamp(System.currentTimeMillis()));
    }

    private void resetEvalItems() {
        if (Messages.YES != Messages.showYesNoDialog(MESSAGE, PluginHelper.getPluginName(), AllIcons.General.Reset)) {
            return;
        }

        Reset.touchLicenses();
        Reset.reset(Reset.getEvalRecords());
        ResetTimeHelper.resetLastResetTime();
        listModel.clear();

        if (null != dialogWrapper) {
            dialogWrapper.close(0);
        }

        AppHelper.restart();
    }
}
