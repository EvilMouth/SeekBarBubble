package com.zyhang.seekbarbubble.delegate;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * ProjectName:SeekBarBubble
 * Description:
 * Created by zyhang on 2017/6/29.14:35
 * Modify by:
 * Modify time:
 * Modify remark:
 */

public class SeekBarBubbleDelegate implements SeekBar.OnSeekBarChangeListener {

    private SeekBar mSeekBar;
    /**
     * 气泡
     */
    private View mBubble;
    /**
     * 气泡是否已经添加到窗口
     */
    private boolean mBubbleAdded;
    /**
     * 永久显示气泡
     */
    private boolean mAlwaysShow;
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
    /**
     * 固定监听，不被clear清除
     * {@link SeekBarBubbleDelegate#clearSeekBarChangeListener()}
     */
    @Nullable
    private SeekBar.OnSeekBarChangeListener mDefaultListener;
    private List<SeekBar.OnSeekBarChangeListener> mAddListeners;

    public SeekBarBubbleDelegate(@NonNull Context context, @NonNull SeekBar seekBar, @NonNull View bubble) {
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

        bindSeekBar(seekBar);

        setBubble(bubble);

        mRect = new Rect();

        mStatusBarHeight = StatusBarUtils.getStatusBarHeight();

        mAddListeners = new ArrayList<>();
    }

    public void bindSeekBar(@NonNull SeekBar seekBar) {
        if (mSeekBar != null) {
            mSeekBar.setOnSeekBarChangeListener(null);
        }
        mSeekBar = seekBar;
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    public SeekBar getSeekBar() {
        return mSeekBar;
    }

    public void setBubble(@NonNull View bubble) {
        if (mBubble != null) {
            removeBubble();
        }
        mBubble = bubble;
        if (mBubble.getVisibility() == View.VISIBLE) {
            mBubble.setVisibility(View.INVISIBLE);
        }
    }

    public View getBubble() {
        return mBubble;
    }

    /**
     * 设置固定监听
     */
    public void setDefaultListener(@Nullable SeekBar.OnSeekBarChangeListener defaultListener) {
        mDefaultListener = defaultListener;
    }

    public void addSeekBarChangeListener(SeekBar.OnSeekBarChangeListener l) {
        mAddListeners.add(l);
    }

    public void removeSeekBarChangeListener(SeekBar.OnSeekBarChangeListener l) {
        mAddListeners.remove(l);
    }

    public void clearSeekBarChangeListener() {
        mAddListeners.clear();
    }

    /**
     * 立即显示气泡
     *
     * @param always 是否永久显示
     */
    public void showBubble(boolean always) {
        if (mBubbleAdded) {
            return;
        }

        mAlwaysShow = always;

        if (!always) {
            return;
        }

        if (mSeekBar.getWidth() <= 0) {
            mSeekBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    showBubble(mSeekBar);
                    mSeekBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        } else {
            showBubble(mSeekBar);
        }
    }

    private void showBubble(final SeekBar seekBar) {
        if (mBubbleAdded) {
            return;
        }

        // 获取整个SeekBar在屏幕的位置
        seekBar.getGlobalVisibleRect(mRect);
        // 重复赋值left right为气泡移动范围
        int offset = 0;
        if (seekBar.getThumb() != null) {
            offset = seekBar.getThumb().getIntrinsicWidth() / 2 - seekBar.getThumbOffset();
        }
        mRect.left = mRect.left + seekBar.getPaddingLeft() + offset;
        mRect.right = mRect.right - seekBar.getPaddingRight() - offset;

        // 将气泡添加到窗口
        mWindowManager.addView(mBubble, mLayoutParams);
        mBubbleAdded = true;

        // 更新气泡位置
        int bubbleWidth = mBubble.getWidth();
        if (bubbleWidth <= 0) {
            mBubble.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    updateBubblePosition(seekBar, seekBar.getProgress(), mBubble.getWidth(), mBubble.getHeight());
                    mBubble.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        } else {
            updateBubblePosition(seekBar, seekBar.getProgress(), bubbleWidth, mBubble.getHeight());
        }

        // 显示气泡
        mBubble.setVisibility(View.VISIBLE);
    }

    private void updateBubblePosition(SeekBar seekBar, int progress, int bubbleWidth, int bubbleHeight) {
        if (!mBubbleAdded || bubbleWidth <= 0 || bubbleHeight <= 0) {
            return;
        }
        float x = mRect.left + ((float) mRect.width() / seekBar.getMax() * progress) - (bubbleWidth / 2);
        mLayoutParams.x = (int) x;
        mLayoutParams.y = mRect.top - mStatusBarHeight - bubbleHeight;

        // 更新气泡位置
        mWindowManager.updateViewLayout(mBubble, mLayoutParams);
    }

    public void removeBubble() {
        if (!mBubbleAdded || mAlwaysShow) {
            return;
        }
        mWindowManager.removeViewImmediate(mBubble);
        mBubbleAdded = false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mDefaultListener != null) {
            mDefaultListener.onProgressChanged(seekBar, progress, fromUser);
        }

        onDelegateProgressChanged(seekBar, progress, fromUser);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (mDefaultListener != null) {
            mDefaultListener.onStartTrackingTouch(seekBar);
        }

        onDelegateStartTrackingTouch(seekBar);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mDefaultListener != null) {
            mDefaultListener.onStopTrackingTouch(seekBar);
        }

        onDelegateStopTrackingTouch(seekBar);
    }

    private void onDelegateProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        for (SeekBar.OnSeekBarChangeListener listener : mAddListeners) {
            listener.onProgressChanged(seekBar, progress, fromUser);
        }

        if (mAlwaysShow || fromUser) {
            updateBubblePosition(seekBar, progress, mBubble.getWidth(), mBubble.getHeight());
        }
    }

    private void onDelegateStartTrackingTouch(SeekBar seekBar) {
        for (SeekBar.OnSeekBarChangeListener listener : mAddListeners) {
            listener.onStartTrackingTouch(seekBar);
        }

        showBubble(seekBar);
    }

    private void onDelegateStopTrackingTouch(SeekBar seekBar) {
        for (SeekBar.OnSeekBarChangeListener listener : mAddListeners) {
            listener.onStopTrackingTouch(seekBar);
        }

        removeBubble();
    }
}
