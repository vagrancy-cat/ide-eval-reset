package com.yunliu.research.intellij.listener;

import com.intellij.ide.AppLifecycleListener;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationActivationListener;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Disposer;
import com.intellij.util.messages.MessageBusConnection;
import com.yunliu.research.intellij.helper.CustomProperties;
import com.yunliu.research.intellij.helper.CustomRepository;
import com.yunliu.research.intellij.helper.ReflectionHelper;
import com.yunliu.research.intellij.helper.BrokenPlugins;

import java.lang.reflect.Method;

/**
 * @author yunliu
 */
public class ListenerConnector {
    private static Disposable disposable;

    public static void setup() {
        dispose();

        CustomProperties.fix();
        BrokenPlugins.fix();
        CustomRepository.checkAndAdd();

        Application app = ApplicationManager.getApplication();
        disposable = Disposer.newDisposable();
        Disposer.register(app, disposable);
        MessageBusConnection connection = app.getMessageBus().connect(disposable);
        connection.subscribe(AppLifecycleListener.TOPIC, new AppEventListener());
        connection.subscribe(ApplicationActivationListener.TOPIC, new AppActivationListener());

        callPluginInstallListenerMethod("setup");
    }

    public static void dispose() {
        if (null == disposable || Disposer.isDisposed(disposable)) {
            return;
        }

        callPluginInstallListenerMethod("remove");
        Disposer.dispose(disposable);
        disposable = null;
    }

    private static void callPluginInstallListenerMethod(String methodName) {    // reflection for old versions
        Class<?> klass = ReflectionHelper.getClass("com.intellij.ide.plugins.PluginStateListener");
        if (null == klass) {
            return;
        }

        String className = ListenerConnector.class.getPackage().getName() + ".PluginInstallListener";
        Method method = ReflectionHelper.getMethod(className, methodName);
        if (null == method) {
            return;
        }

        try {
            method.invoke(null);
        } catch (Exception e) {
            // ignored
        }
    }
}
