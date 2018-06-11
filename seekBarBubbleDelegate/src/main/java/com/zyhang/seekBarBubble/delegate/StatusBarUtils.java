package com.zyhang.seekBarBubble.delegate;

import android.content.res.Resources;

/**
 * Created by zyhang on 2018/6/8.14:37
 */

public class StatusBarUtils {

    /**
     * @return status_bar_height
     */
    public static int getStatusBarHeight() {
        int height = 0;
        try {
            Resources resources = Resources.getSystem();
            height = resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return height;
    }
}
