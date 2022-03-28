package com.yunliu.research.intellij.common;

import com.intellij.ide.util.PropertiesComponent;

/**
 * @author yunliu
 */
public class PropertyRecord implements EvalRecord {
    private final String key;
    private final String value;

    public PropertyRecord(String key) {
        this.key = key;
        this.value = PropertiesComponent.getInstance().getValue(key);
    }

    @Override
    public void reset() throws Exception {
        PropertiesComponent.getInstance().unsetValue(key);
    }

    @Override
    public String toString() {
        String type = "Property";
        return type + ": " + key + " = " + (null == value ? "" : value);
    }
}
