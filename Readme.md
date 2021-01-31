[TOC]



# StatePage

## 具体功能如下：（目标）

- [x] 无需在使用的布局添加视图代码
- [x] 可显示自定义状态视图,任意拓展
- [x] 可用于 Activity、Fragment、或指定的 View
- [x] 自定义重新请求监听
- [x] 可动态更新视图样式
- [x] 可结合第三方控件使用
- [ ] 支持状态回调监听（TODO）
- [ ] 提供部分简易默认的statePage调用（TODO）


##	原理

> 其实是利用传入的targetView，获取其父布局targetViewGroup以及targetView的targetViewLayoutParams
>
> 然后利用targetViewLayoutParams获取到的宽width 高height作为statePage的宽高
>
> 最后在调用了show方法后，将statePage添加到targetViewGroup，实现了覆盖targetView的效果
>
>  
>
> 相比较于remove targetView，这种做法相对不会影响到targetView（例如你已经remove了targetView，但在调用此对象某些方法时候，会检测到targetView没有了viewGroup，会到程序异常）