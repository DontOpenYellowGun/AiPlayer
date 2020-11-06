仿TikTok

![抖音，记录别人的美好生活](https://upload-images.jianshu.io/upload_images/8669504-3293ef3c6d1d27d3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/800)

抖音，可以说目前最火的短视频APP！作为一名 Android 开发，是时候研究一下功能是如何实现的了！目前，也有一些其他的小伙伴实现都有播放的效果，只要是用 ViewPager 来实现的上下滑动。 今天，我们换个思路用我们最常用的控件，RecyclerView 来实现，下面先看看需要实现的功能。

作者：Demo_Yang
链接：https://juejin.im/post/6844903975297220616
来源：掘金
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

大概实现的效果如下
![抖音，记录别人的美好生活](https://user-gold-cdn.xitu.io/2019/10/22/16df14a6cf3296fc?imageslim)

大致有以下几个功能。

1. 上下滑动播放详情页
2. 播放之后自动播放
3. 双击点赞效果，并且点赞
4. 单击暂停，再单击播放