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

val SeekBar.mListeners: ArrayList<SeekBar.OnSeekBarChangeListener>
    get() = arrayListOf()

fun SeekBar.addOnSeekBarChangeListener(l: SeekBar.OnSeekBarChangeListener) {
    mListeners.add(l)
}

fun SeekBar.removeOnSeekBarChangeListener(l: SeekBar.OnSeekBarChangeListener) {
    mListeners.remove(l)
}

fun SeekBar.clearOnSeekBarChangeListener() {
    mListeners.clear()
}

fun SeekBarBubbleDelegate.attachToSeekBar(sb: SeekBar) {
    sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            sb.mListeners.forEach {
                it.onProgressChanged(seekBar, progress, fromUser)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
//            sb.mIsDragging = true
//            this@attachToSeekBar.onStartTrackingTouch(seekBar)
            sb.mListeners.forEach {
                it.onStartTrackingTouch(seekBar)
            }
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
//            sb.mIsDragging = false
//            this@attachToSeekBar.onStopTrackingTouch(seekBar)
            sb.mListeners.forEach {
                it.onStopTrackingTouch(seekBar)
            }
        }
    })
}