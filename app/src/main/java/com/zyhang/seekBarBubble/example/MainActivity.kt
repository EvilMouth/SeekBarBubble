package com.zyhang.seekBarBubble.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.widget.SeekBar
import android.widget.TextView
import com.zyhang.seekBarBubble.SeekBarBubble
import com.zyhang.seekBarBubble.delegate.SeekBarBubbleDelegate
import com.zyhang.seekBarBubble.delegate.kotlin.attachToSeekBar

/**
 * ProjectName:SeekBarBubble
 * Description:
 * Created by zyhang on 2017/7/1.10:55
 * Modify by:
 * Modify time:
 * Modify remark:
 */

class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    companion object {
        private val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //view
        val seekBarBubble = findViewById(R.id.seekBarBubble) as SeekBarBubble
        seekBarBubble.addOnSeekBarChangeListener(this)

        //delegate
        val seekBarBubbleDelegate = SeekBarBubbleDelegate(this,
                LayoutInflater.from(this).inflate(R.layout.seekbar_bubble, null))
        val seekBar = findViewById(R.id.seekBar) as SeekBar
        seekBarBubbleDelegate.attachToSeekBar(seekBar) { _, progress, _ ->
            (seekBarBubbleDelegate.bubble.findViewById(R.id.seekBar_bubble_tv) as TextView).text = "$progress''"
        }
        seekBarBubbleDelegate.addOnSeekBarChangeListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        Log.i(TAG, "onProgressChanged progress === $progress")
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        Log.i(TAG, "onStartTrackingTouch")
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        Log.i(TAG, "onStopTrackingTouch")
    }
}