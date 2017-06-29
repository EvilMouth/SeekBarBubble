package com.zyhang.seekBarBubble;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zyhang.seekBarBubble.delegate.SeekBarBubbleDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectName:SeekBarBubble
 * Description:
 * Created by zyhang on 2017/6/29.下午2:50
 * Modify by:
 * Modify time:
 * Modify remark:
 */

public class SeekBarBubble extends android.support.v7.widget.AppCompatSeekBar implements SeekBar.OnSeekBarChangeListener {

    private SeekBarBubbleDelegate mDelegate;
    private List<OnSeekBarChangeListener> mListeners;
    private boolean mIsDragging;

    public SeekBarBubble(Context context) {
        this(context, null);
    }

    public SeekBarBubble(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.seekBarStyle);
    }

    public SeekBarBubble(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDelegate = new SeekBarBubbleDelegate(getContext(),
                LayoutInflater.from(getContext()).inflate(R.layout.seekbar_bubble, null));
        mListeners = new ArrayList<>();
        mIsDragging = false;

        setOnSeekBarChangeListener(this);
    }

    /**
     * @deprecated please use addOnSeekBarChangeListener instead
     * See {@link SeekBarBubble#addOnSeekBarChangeListener(OnSeekBarChangeListener)}
     */
    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        super.setOnSeekBarChangeListener(l);
    }

    public void addOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        mListeners.add(l);
    }

    public void removeOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        mListeners.remove(l);
    }

    public void clearOnSeekBarChangeListener() {
        mListeners.clear();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mIsDragging) {
            ((TextView) mDelegate.getBubble().findViewById(R.id.seekBar_bubble_tv)).setText(String.format("%s''", progress));
            mDelegate.onProgressChanged(seekBar, progress, fromUser);
        }
        for (OnSeekBarChangeListener listener : mListeners) {
            listener.onProgressChanged(seekBar, progress, fromUser);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mIsDragging = true;
        mDelegate.onStartTrackingTouch(seekBar);
        for (OnSeekBarChangeListener listener : mListeners) {
            listener.onStartTrackingTouch(seekBar);
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mIsDragging = false;
        mDelegate.onStopTrackingTouch(seekBar);
        for (OnSeekBarChangeListener listener : mListeners) {
            listener.onStopTrackingTouch(seekBar);
        }
    }
}
