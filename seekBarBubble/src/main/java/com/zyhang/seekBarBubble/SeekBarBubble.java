package com.zyhang.seekBarBubble;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zyhang.seekBarBubble.delegate.SeekBarBubbleDelegate;

/**
 * ProjectName:SeekBarBubble
 * Description:
 * Created by zyhang on 2017/6/29.14:50
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

        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SeekBarBubble);
        boolean alwaysShow = ta.getBoolean(R.styleable.SeekBarBubble_sbb_alwaysShow, false);
        ta.recycle();

        mDelegate = new SeekBarBubbleDelegate(getContext(),
                this,
                LayoutInflater.from(getContext()).inflate(R.layout.seekbar_bubble, null));
        mDelegate.setDefaultListener(this);
        if (alwaysShow) {
            mDelegate.showBubble(true);
            ((TextView) mDelegate.getBubble().findViewById(R.id.seekBar_bubble_tv)).setText(String.format("%s''", getProgress()));
        }
    }

    public SeekBarBubbleDelegate getDelegate() {
        return mDelegate;
    }

    /**
     * call this method will disable the delegate
     * {@link SeekBarBubble#getDelegate()}
     * {@link SeekBarBubbleDelegate#addSeekBarChangeListener(OnSeekBarChangeListener)}
     *
     * @deprecated please use addOnSeekBarChangeListener instead
     */
    @Deprecated
    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        super.setOnSeekBarChangeListener(l);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        ((TextView) mDelegate.getBubble().findViewById(R.id.seekBar_bubble_tv)).setText(String.format("%s''", progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
