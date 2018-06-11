package com.zyhang.seekBarBubble.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.widget.SeekBar
import android.widget.TextView
import com.zyhang.seekBarBubble.delegate.SeekBarBubbleDelegate
import com.zyhang.seekBarBubble.delegate.kotlin.setDefaultListener
import kotlinx.android.synthetic.main.activity_main.*

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
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // view
        seekBarBubble.delegate
                .addSeekBarChangeListener(this)

        // delegate
        val seekBarBubbleDelegate = SeekBarBubbleDelegate(this,
                seekBar,
                LayoutInflater.from(this).inflate(R.layout.seekbar_bubble, null))
        with(seekBarBubbleDelegate) {
            setDefaultListener { _, progress, _ ->
                bubble.findViewById<TextView>(R.id.seekBar_bubble_tv).text = "$progress''"
            }
            // always show
            showBubble(true)
            bubble.findViewById<TextView>(R.id.seekBar_bubble_tv).text = "${seekBar.progress}''"
        }
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