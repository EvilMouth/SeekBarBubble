package com.zyhang.seekBarBubble;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zyhang.seekBarBubble.delegate.SeekBarBubbleDelegate;

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

        super.setOnSeekBarChangeListener(this);
    }

    /**
     * @deprecated please use addOnSeekBarChangeListener instead
     * See {@link SeekBarBubble#addOnSeekBarChangeListener(OnSeekBarChangeListener)}
     */
    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
    }

    public void addOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        mDelegate.addOnSeekBarChangeListener(l);
    }

    public void removeOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        mDelegate.removeOnSeekBarChangeListener(l);
    }

    public void clearOnSeekBarChangeListener() {
        mDelegate.clearOnSeekBarChangeListener();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        ((TextView) mDelegate.getBubble().findViewById(R.id.seekBar_bubble_tv)).setText(String.format("%s''", progress));
        mDelegate.onProgressChanged(seekBar, progress, fromUser);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mDelegate.onStartTrackingTouch(seekBar);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mDelegate.onStopTrackingTouch(seekBar);
    }
}
