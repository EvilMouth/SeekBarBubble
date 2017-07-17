package com.zyhang.seekBarBubble.delegate;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ProjectName:SeekBarBubble
 * Description:
 * Created by zyhang on 2017/7/17.下午5:14
 * Modify by:
 * Modify time:
 * Modify remark:
 */

public class XiaoMiUtils {

    private static Properties sProperties;

    private synchronized static Properties getProperties() {
        if (sProperties == null) {
            sProperties = new Properties();
            try {
                sProperties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sProperties;
    }

    public static boolean isMIUI() {
        return getProperties().containsKey("ro.miui.ui.version.name");
    }
}
