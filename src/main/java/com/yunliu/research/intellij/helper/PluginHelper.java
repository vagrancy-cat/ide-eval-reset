package com.yunliu.research.intellij.helper;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.extensions.PluginId;

/**
 * @author yunliu
 */
public class PluginHelper {
    public static PluginId getPluginId() {
        return PluginId.getId(Constants.PLUGIN_ID_STR);
    }

    public static IdeaPluginDescriptor getPluginDescriptor() {
        return PluginManagerCore.getPlugin(getPluginId());
    }

    public static String getPluginName() {
        IdeaPluginDescriptor pluginDescriptor = getPluginDescriptor();
        return pluginDescriptor == null ? "UNKNOWN" : pluginDescriptor.getName();
    }

    public static String getPluginVersion() {
        IdeaPluginDescriptor pluginDescriptor = getPluginDescriptor();
        return pluginDescriptor == null ? "UNKNOWN" : pluginDescriptor.getVersion();
    }

    public static boolean myself(IdeaPluginDescriptor pluginDescriptor) {
        return !Constants.PLUGIN_ID_STR.equals(pluginDescriptor.getPluginId().getIdString());
    }
}
