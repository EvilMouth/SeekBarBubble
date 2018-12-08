# SeekBarBubble
[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Download](https://api.bintray.com/packages/zyhang/maven/seekBarBubbleDelegate/images/download.svg)](https://bintray.com/zyhang/maven/seekBarBubbleDelegate/_latestVersion)
[![API](https://img.shields.io/badge/API-16%2B-blue.svg?style=flat)](https://developer.android.com/about/versions/android-4.1)
[![Author](https://img.shields.io/badge/Author-zyhang-red.svg?style=flat)](https://www.zyhang.com/)

SeekBar滑动跟随气泡辅助

## Delegate

以不侵入的思路为SeekBar增加气泡跟随，依赖SeekBar本身提供的OnSeekBarChangeListener

### Installation
```gradle
implementation 'com.zyhang:seekbarbubbledelegate:<latest-version>'
```

### Usage
具体可以参考[MainActivity.kt](https://github.com/izyhang/SeekBarBubble/blob/master/app/src/main/kotlin/com/zyhang/seekBarBubble/example/MainActivity.kt)
```kotlin
val seekBarBubbleDelegate = SeekBarBubbleDelegate(context, seekBar,
                LayoutInflater.from(this).inflate(R.layout.seekbar_bubble, null))
seekBarBubbleDelegate.setDefaultListener { _, progress, _ ->
    bubble.findViewById<TextView>(R.id.seekBar_bubble_tv).text = "$progress''"
}
```

## Kotlin 扩展

### Installation
```gradle
implementation 'com.zyhang:seekbarbubbledelegate-kotlin:<latest-version>'
```

## SeekBarBubble

继承SeekBar基于SeekBarBubbleDelegate提供的一款简单的气泡跟随SeekBar

### Screenshot
![](screenshot/1.png)

### Installation
```gradle
implementation 'com.zyhang:seekbarbubble:<latest-version>'
```

### Usage
```xml
<com.zyhang.seekBarBubble.SeekBarBubble
            android:id="@+id/seekBarBubble"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            app:sbb_alwaysShow="true"/>
```
