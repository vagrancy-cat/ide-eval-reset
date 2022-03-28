package com.yunliu.research.intellij.common;

import com.intellij.openapi.util.io.FileUtil;

import java.io.File;

/**
 * @author yunliu
 */
public class NormalFileRecord implements EvalRecord {
    private final String type = "File";
    private final File file;

    public NormalFileRecord(File file) {
        this.file = file;
    }

    @Override
    public void reset() throws Exception {
        if (!FileUtil.delete(file)) {
            throw new Exception("Remove " + type + " failed: " + file.getAbsolutePath());
        }
    }

    @Override
    public String toString() {
        return type + ": " + file.getName();
    }
}
