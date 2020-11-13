# 仿TikTok
现在的短视频领域非常的火热，抱着学习的态度，自己也来研究一下如何实现类似抖音的短视频效果，网上很多小伙伴很多用viewpager的方式来实现，这次我们试试recyclerview来实现一下。

效果如下：
![](https://github.com/wildeyess/AiPlayer/blob/master/describe.gif?raw=true)

##技术点：
- 1.Recycler加PagerSnapHelper
- 2.airbnb的开源项目lottie加载json动画，json动画由VUE制作，实现红心效果
- 3.CoordinatorLayout+AppBarLayout折叠布局
- 4.BottomSheetDialogFragment实现评论上拉框
- 5.头像平滑过渡通过5.0共享元素动画实现
- 6.话题#颜色可点击通过spannedstring实现

##原理解析：
###PagerSnapHelper
PagerSnapHelper是25.1.0版本中官方提供的一个类，可以使RecyclerView像ViewPager一样的效果，一次只能滑一页，而且居中显示。这个类使用方式也很简单，只需要创建对象之后调用attachToRecyclerView()附着到对应的RecyclerView对象上就可以了。
```
new LinearSnapHelper().attachToRecyclerView(mRecyclerView);
//或者
new PagerSnapHelper().attachToRecyclerView(mRecyclerView);
```
####Fling操作
首先来了解一个概念，手指在屏幕上滑动RecyclerView然后松手，RecyclerView中的内容会顺着惯性继续往手指滑动的方向继续滚动直到停止，这个过程叫做Fling。Fling操作从手指离开屏幕瞬间被触发，在滚动停止时结束。

####三个抽象方法
SnapHelper是一个抽象类，它有三个抽象方法：
```
public abstract int findTargetSnapPosition(LayoutManager layoutManager, int velocityX, int velocityY)
```
该方法会根据触发Fling操作的速率（参数velocityX和参数velocityY）来找到RecyclerView需要滚动到哪个位置，该位置对应的ItemView就是那个需要进行对齐的列表项。我们把这个位置称为targetSnapPosition，对应的View称为targetSnapView。如果找不到targetSnapPosition，就返回RecyclerView.NO_POSITION。
```
public abstract View findSnapView(LayoutManager layoutManager)
```
该方法会找到当前layoutManager上最接近对齐位置的那个view，该view称为SanpView，对应的position称为SnapPosition。如果返回null，就表示没有需要对齐的View，也就不会做滚动对齐调整。
```
public abstract int[] calculateDistanceToFinalSnap(@NonNull LayoutManager layoutManager, @NonNull View targetView);
```
这个方法会计算第二个参数对应的ItemView当前的坐标与需要对齐的坐标之间的距离。该方法返回一个大小为2的int数组，分别对应x轴和y轴方向上的距离。
![](https://upload-images.jianshu.io/upload_images/148381-cf775ea703e0599c.png?imageMogr2/auto-orient/strip|imageView2/2/w/532/format/webp)
现在来看attachToRecyclerView()这个方法，SnapHelper正是通过该方法附着到RecyclerView上，从而实现辅助RecyclerView滚动对齐操作。

snapToTargetExistingView()
该方法的作用是对SnapView进行滚动调整，以使得SnapView达到对齐效果。