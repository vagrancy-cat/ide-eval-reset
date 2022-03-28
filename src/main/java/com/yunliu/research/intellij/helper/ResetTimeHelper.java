package com.yunliu.research.intellij.helper;

import com.intellij.ide.Prefs;
import com.yunliu.research.intellij.common.Reset;

/**
 * @author yunliu
 */
public class ResetTimeHelper {
    /**
     * 25 days
     */
    public static final long RESET_PERIOD = 2160000000L;
    private static final String RESET_KEY = Constants.PLUGIN_PREFERENCE_PREFIX + ".reset_time." + Constants.IDE_NAME + Constants.IDE_BASELINE_VERSION;

    public static long getLastResetTime() {
        return Prefs.getLong(RESET_KEY, 0L);
    }

    public static void resetLastResetTime() {
        Prefs.putLong(RESET_KEY, System.currentTimeMillis());
        Reset.syncPreference();
    }

    public static boolean overResetPeriod() {
        return System.currentTimeMillis() - ResetTimeHelper.getLastResetTime() > RESET_PERIOD;
    }

    public static String getLastResetTimeStr() {
        long lastResetTime = getLastResetTime();

        return lastResetTime > 0 ? DateTime.getStringFromTimestamp(lastResetTime) : "Not yet";
    }
}
