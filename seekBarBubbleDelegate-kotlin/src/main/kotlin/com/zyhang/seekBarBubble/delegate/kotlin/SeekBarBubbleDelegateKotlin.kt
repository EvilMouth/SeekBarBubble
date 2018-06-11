package com.zyhang.seekBarBubble.delegate.kotlin

import android.widget.SeekBar
import com.zyhang.seekBarBubble.delegate.SeekBarBubbleDelegate

/**
 * ProjectName:SeekBarBubble
 * Description:
 * Created by zyhang on 2017/6/30.23:35
 * Modify by:
 * Modify time:
 * Modify remark:
 */

fun SeekBarBubbleDelegate.setDefaultListener(onStartTrackingTouch: (SeekBar) -> Unit = {},
                                             onStopTrackingTouch: (SeekBar) -> Unit = {},
                                             onProgressChanged: (SeekBar, Int, Boolean) -> Unit) {
    setDefaultListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            onProgressChanged(seekBar, progress, fromUser)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            onStartTrackingTouch(seekBar)
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            onStopTrackingTouch(seekBar)
        }
    })
}
