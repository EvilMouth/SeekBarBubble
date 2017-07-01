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

import java.util.ArrayList;
import java.util.List;

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
    private boolean mIsDragging;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private Rect mRect;
    private float mSeekBarProgressWidth;
    private int mStatusBarHeight;
    private List<SeekBar.OnSeekBarChangeListener> mListeners;

    public SeekBarBubbleDelegate(Context context, View bubble) {
        mBubble = bubble;

        mIsDragging = false;

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

        mListeners = new ArrayList<>();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mIsDragging && mBubble.getMeasuredWidth() > 0) {
            float x = mSeekBarProgressWidth / seekBar.getMax() * progress + mRect.left + seekBar.getPaddingLeft() - (mBubble.getWidth() / 2);
            mLayoutParams.x = (int) x;
            mLayoutParams.y = mRect.top - mStatusBarHeight - mBubble.getHeight();

            mBubble.setVisibility(View.VISIBLE);
            mWindowManager.updateViewLayout(mBubble, mLayoutParams);
        }

        for (SeekBar.OnSeekBarChangeListener listener : mListeners) {
            listener.onProgressChanged(seekBar, progress, fromUser);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mIsDragging = true;
        if (mSeekBarProgressWidth == 0F) {
            mSeekBarProgressWidth = seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight();
        }

        seekBar.getGlobalVisibleRect(mRect);
        if (mBubble.getWidth() <= 0) {
            mBubble.setVisibility(View.INVISIBLE);
        }
        mWindowManager.addView(mBubble, mLayoutParams);

        for (SeekBar.OnSeekBarChangeListener listener : mListeners) {
            listener.onStartTrackingTouch(seekBar);
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mIsDragging = false;
        mWindowManager.removeViewImmediate(mBubble);

        for (SeekBar.OnSeekBarChangeListener listener : mListeners) {
            listener.onStopTrackingTouch(seekBar);
        }
    }

    public View getBubble() {
        return mBubble;
    }

    public boolean isDragging() {
        return mIsDragging;
    }

    public SeekBarBubbleDelegate setDragging(boolean dragging) {
        mIsDragging = dragging;
        return this;
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

    public void addOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener l) {
        mListeners.add(l);
    }

    public void removeOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener l) {
        mListeners.remove(l);
    }

    public void clearOnSeekBarChangeListener() {
        mListeners.clear();
    }
}
