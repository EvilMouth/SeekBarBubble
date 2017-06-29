package com.zyhang.seekBarBubble.delegate;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.SeekBar;

/**
 * ProjectName:SeekBarBubble
 * Description:
 * Created by zyhang on 2017/6/29.下午2:35
 * Modify by:
 * Modify time:
 * Modify remark:
 */

public class SeekBarBubbleDelegate implements SeekBar.OnSeekBarChangeListener {

    private View mBubble;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private Rect mRect;
    private float mSeekBarProgressWidth;
    private int mStatusBarHeight;

    public SeekBarBubbleDelegate(Context context, View bubble) {
        mBubble = bubble;

        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.gravity = Gravity.START | Gravity.TOP;
        mLayoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mLayoutParams.format = PixelFormat.TRANSLUCENT;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        }

        mRect = new Rect();

        mSeekBarProgressWidth = 0F;

        mStatusBarHeight = getStatusBarHeight();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float x = mSeekBarProgressWidth / seekBar.getMax() * progress + mRect.left + seekBar.getPaddingLeft() - (mBubble.getWidth() / 2);
        mLayoutParams.x = (int) x;
        mLayoutParams.y = mRect.top - mStatusBarHeight - mBubble.getHeight();

        mBubble.setVisibility(View.VISIBLE);
        mWindowManager.updateViewLayout(mBubble, mLayoutParams);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (mSeekBarProgressWidth == 0F) {
            mSeekBarProgressWidth = seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight();
        }

        seekBar.getGlobalVisibleRect(mRect);
        if (mBubble.getWidth() <= 0) {
            mBubble.setVisibility(View.INVISIBLE);
        }
        mWindowManager.addView(mBubble, mLayoutParams);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mWindowManager.removeViewImmediate(mBubble);
    }

    public View getBubble() {
        return mBubble;
    }

    private int getStatusBarHeight() {
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
