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

    /**
     * 气泡
     */
    private View mBubble;
    private boolean mIsDragging;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    /**
     * 气泡移动范围
     */
    private Rect mRect;
    /**
     * 状态栏高度
     */
    private int mStatusBarHeight;
    private List<SeekBar.OnSeekBarChangeListener> mListeners;

    public SeekBarBubbleDelegate(Context context, View bubble) {
        mBubble = bubble;
        mBubble.setVisibility(View.INVISIBLE);

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
        if (XiaoMiUtils.isMIUI() || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        }

        mRect = new Rect();

        mStatusBarHeight = getStatusBarHeight();

        mListeners = new ArrayList<>();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int bubbleWidth = mBubble.getWidth();
        if (mIsDragging && bubbleWidth > 0) {
            float x = mRect.left + ((float) mRect.width() / seekBar.getMax() * progress) - (bubbleWidth / 2);
            mLayoutParams.x = (int) x;
            mLayoutParams.y = mRect.top - mStatusBarHeight - mBubble.getHeight();

            //更新气泡位置
            mWindowManager.updateViewLayout(mBubble, mLayoutParams);
            mBubble.setVisibility(View.VISIBLE);
        }

        for (SeekBar.OnSeekBarChangeListener listener : mListeners) {
            listener.onProgressChanged(seekBar, progress, fromUser);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mIsDragging = true;
        //获取整个SeekBar在屏幕的位置
        seekBar.getGlobalVisibleRect(mRect);
        //重复赋值left right为气泡移动范围
        int offset = seekBar.getThumb().getIntrinsicWidth() / 2 - seekBar.getThumbOffset();
        mRect.left = mRect.left + seekBar.getPaddingLeft() + offset;
        mRect.right = mRect.right - seekBar.getPaddingRight() - offset;
        //将气泡加入window
        mWindowManager.addView(mBubble, mLayoutParams);

        for (SeekBar.OnSeekBarChangeListener listener : mListeners) {
            listener.onStartTrackingTouch(seekBar);
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mIsDragging = false;
        removeBubble();

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

    public void removeBubble() {
        try {
            mWindowManager.removeViewImmediate(mBubble);
        } catch (Exception e) {
            //do nothing
        }
    }
}
