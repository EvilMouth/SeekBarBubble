# SeekBarBubble
[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)

SeekBar滑动跟随气泡

## LastVersion
`1.0.0`

## SeekBarBubble [![Download](https://api.bintray.com/packages/zyhang/maven/seekBarBubble/images/download.svg) ](https://bintray.com/zyhang/maven/seekBarBubble/_latestVersion)
继承SeekBar基于SeekBarBubbleDelegate提供的一款简单的气泡跟随SeekBar

### Screenshot
![](screenshot/1.png)

### Installation
```
compile 'com.zyhang:seekBarBubble:{LastVersion}'
```

## Delegate [![Download](https://api.bintray.com/packages/zyhang/maven/seekBarBubbleDelegate/images/download.svg) ](https://bintray.com/zyhang/maven/seekBarBubbleDelegate/_latestVersion)

以不侵入的思路为SeekBar增加气泡跟随，依赖SeekBar本身提供的OnSeekBarChangeListener

### Installation
```
compile 'com.zyhang:seekBarBubbleDelegate:{LastVersion}'
```

### Usage
具体可以参考[SeekBarBubble](https://github.com/yuhangjiayou/SeekBarBubble/blob/master/seekBarBubble/src/main/java/com/zyhang/seekBarBubble/SeekBarBubble.java)
```
SeekBarBubbleDelegate delegate = new SeekBarBubbleDelegate(context, bubbleView);
seekBar.setOnSeekBarChangeListener {
  onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    delegate.onProgressChanged(seekBar, progress, fromUser);
  }
  onStartTrackingTouch(SeekBar seekBar) {
    delegate.onStartTrackingTouch(seekBar);
  }
  onStopTrackingTouch(SeekBar seekBar) {
    delegate.onStopTrackingTouch(seekBar);
  }
}
```
