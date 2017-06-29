package com.zyhang.seekBarBubble.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import com.zyhang.seekBarBubble.SeekBarBubble;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBarBubble seekBarBubble = (SeekBarBubble) findViewById(R.id.seekBar);
        seekBarBubble.addOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.i(TAG, "onProgressChanged: progress === " + progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.i(TAG, "onStartTrackingTouch: ");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.i(TAG, "onStopTrackingTouch: ");
    }
}
