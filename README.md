# Android自定义view之emoji键盘

> 主要还是最近做了一个emoji键盘的需求,感觉可以简单封装一下顺便整理下emoji相关的知识

## Android中的 emoji

有关Android中emoji的文章有很多了,我就不简单罗列相关内容了,在这记录一下开发过程中遇到的一些坑...

1. 首先是TextView首次绘制中emoji的颜色会偏浅,官方的demo中依然存在这种问题...

  ![官方demo](http://ooymoxvz4.bkt.clouddn.com/18-5-5/6609105.jpg)

  (详情可以参考https://github.com/googlesamples/android-EmojiCompat) ,上图中可以看到其中两个Textview的emoji颜色偏浅,但是EditView中就不会出现这个问题...

  解决方法的话暂时有两种,一是针对不能引入EmojiCompat的项目,可以考虑针对textview多次绘制,但是对此Textview设置background的时候会失效...
  二是可以针对可以引入EmojiCompat的项目,但是简单的直接设置EmojiCompat仍然不好用...通过自定义View即demo中的Custom TextView即刻解决这个问题

2. 原生emoji的适配问题

  不同版本的系统中适配的emoji是不同的,一般来说高版本兼容低版本的emoji.

  另一个就是国产rom的问题,部分rom中会将android的emoji图标替换成ios的emoji.会给人一种也没什么差别的错觉...但是,好歹都适配了啊,部分图标还是android的emoji风格,就很迷

3. emoji的大小问题

  emoji的大小...是跟随TextView的textSize,所以要想调整emoji的大小,需要更改textView的textSize值.

4. EditText的删除问题

  键盘嘛,需要对editText进行操作,其中就包括了删除的操作,然而,emoji有的占两个字节,有的占一个自己.所以再删除的时候需要验证内容再删除对应的字节

简单来说,及时更新设备或者开发工具的话emoji展示并不是什么问题,愿望是美好的,现实却总是不进如人意.(只有我觉得Android的果冻人emoji看着还可以么...)

## Emoji键盘
先展示效果图,

![完成效果图](http://ooymoxvz4.bkt.clouddn.com/18-5-5/96101267.jpg)

一个emoji键盘的主要功能都实现了,包括了emoji的输入及删除,多个emoji标签页之间的切换及底部滑动指示器的更改.需要展示底部标签页超过屏幕范围的效果,所以展示的部分数据是重复的.

### 设计过程
  最初的设计中是没有底部多个emoji标签页的,感觉功能有点单薄,所以后期加入了底部过个item切换的功能

  整个View设计如下,

  ![整个View设计](http://ooymoxvz4.bkt.clouddn.com/18-5-4/47317517.jpg)

  1. emoji展示与选择区域: 主要是用于展示emoji的内容,这里使用了ViewPager,在初始化的时候会对emoji展示列表进行计算,最终填充到ViewPager中.对于整个View来说,在初始化ViewPager的Adapter的时候就将所有的emoji填入,在滑动或者选择底部item的时候会根据初始化中计算好的各个页面的展示信息控制指示器及底部item.
  2. ViewPager的指示器: 实际上是一个LinearLayout,根据设置展示对应的数量及位置,此处展示的是当前item的相关指示器,当选择新的item或者滑动到新的item后,指示器的数量会进行更新,而当上面的ViewPager滑动却没有切换到新的item的时候只是更新指示器指示的内容.
  3. emoji标签部分: 使用了一个RecycleView,是因为当标签比较多时候滑动效果和定位展示比较方便.

#### 设计缺陷
  * 暂只支持emoji选择,不能提供自定义图片输入(感觉更改完了就不只是emoji输入器了,下个就做这个了)
  * 底部指示器现在更新的过程中是全部移除view再根据新参数设置新view没有动画效果
  * 底部emoji标签页再当选择的item手动滑动超出屏幕显示外后,再次滑动顶部ViewPager页面,底部item复位动画有点僵,仍有优化的余地

### 代码实现
  1. emoji展示与选择区域:

  主要是针对顶部ViewPager的Adapter处理

  ![emoji展示与选择区域](http://ooymoxvz4.bkt.clouddn.com/18-5-5/55341826.jpg)

  2. EditText文本控制

  再将EditText绑定到view中后,对view进行操作后会更改EditText的内容

  ![EditText文本控制](http://ooymoxvz4.bkt.clouddn.com/18-5-5/245055.jpg)

  需要注意的是再删除的时候是要先验证后两位是否是一个完整emoji,如果是则删除后两个字节,反之则只删除一个字节.

  3. 底部标签页指示器

  简单的一个LinearLayout来展示指示器,没有切换的动画效果,可以优化一波.

  ![底部标签页指示器](http://ooymoxvz4.bkt.clouddn.com/18-5-5/32648287.jpg)

  4. 底部标签选择器

  通过一个RecycleView来实现的底部标签选择器

  ![底部标签选择器](http://ooymoxvz4.bkt.clouddn.com/18-5-5/48727751.jpg)

  5. 监听ViewPager滑动及底部标签选择

  添加ViewPager的addOnPageChangeListener方法来监听滑动及底部标签选择的点击回调

  ![监听ViewPager滑动及底部标签选择](http://ooymoxvz4.bkt.clouddn.com/18-5-5/18227363.jpg)

相关代码可以访问我的[GitHub](https://github.com/clwater/EmojiKeyboard)来获取,欢迎大家start或者提供建议.
