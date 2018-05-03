# Android自定义view之emoji键盘

> 主要还是最近做了一个emoji键盘的需求,感觉可以简单封装一下顺便整理下emoji相关的知识

## Android中的 emoji

有关Android中emoji的文章有很多了,我就不简单罗列相关内容了,在这记录一下开发过程中遇到的一些坑...

1. 首先是TextView首次绘制中emoji的颜色会偏浅,官方的demo中依然存在这种问题...

  ![官方demo](http://ooymoxvz4.bkt.clouddn.com/18-5-3/677120.jpg)

  (gradle需要更新,就直接使用官方demo中的截图了 ,详情可以参考https://github.com/googlesamples/android-EmojiCompat) ,上图中可以看到其中两个Textview的emoji颜色偏浅,但是EditView中就不会出现这个问题...

  解决方法的话暂时有两种,一是针对不能引入EmojiCompat的项目,可以考虑针对textview多次绘制,但是对此Textview设置background的时候会失效...
  二是可以针对可以引入EmojiCompat的项目,但是简单的直接设置EmojiCompat仍然不好用...通过自定义View即demo中的Custom TextView即刻解决这个问题

2. 原生emoji的适配问题

  不同版本的系统中适配的emoji是不同的,一般来说高版本兼容低版本的emoji.

  另一个就是国产rom的问题,部分rom中会将android的emoji图标替换成ios的emoji.会给人一种也没什么差别的错觉...但是,好歹都适配了啊,部分图标还是android的emoji风格,就很迷

3. emoji的大小问题

  emoji的大小...是跟随TextView的textSize,所以要想调整emoji的大小,需要更改textView的textSize值.

4. EditText的删除问题

  键盘嘛,需要对editText进行操作,其中就包括了删除的操作,然而,emoji有的占两个字节,有的占一个自己.所以再删除的时候需要验证内容再删除对应的字节
