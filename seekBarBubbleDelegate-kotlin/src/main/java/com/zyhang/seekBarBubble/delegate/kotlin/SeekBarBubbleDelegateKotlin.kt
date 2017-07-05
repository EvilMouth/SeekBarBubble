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

fun SeekBarBubbleDelegate.attachToSeekBar(sb: SeekBar, onProgressChanged: (SeekBar, Int, Boolean) -> Unit) {
    sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            onProgressChanged(seekBar, progress, fromUser)
            this@attachToSeekBar.onProgressChanged(seekBar, progress, fromUser)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            this@attachToSeekBar.onStartTrackingTouch(seekBar)
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            this@attachToSeekBar.onStopTrackingTouch(seekBar)
        }
    })
}