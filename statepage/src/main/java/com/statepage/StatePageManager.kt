package com.statepage

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import java.util.concurrent.CopyOnWriteArraySet

/**
 * statePage的统一控制器
 * statePage本身不自带show/dismiss方法，只能通过statePage的bind方法
 * 保存绑定关系的缓存数组binderCache为线程安全
 */
object StatePageManager {
    private val binderCache: CopyOnWriteArraySet<StatePageBinder> = CopyOnWriteArraySet()


    /**
     * 跟要覆盖的view进行绑定关系
     * 针对直接传入activity进行特殊化处理
     */
    @JvmStatic
    fun <T: AbsStatePage> bind(activity: Activity, statePage: T) {
        val targetView = activity.findViewById<ViewGroup>(android.R.id.content)
        addStatePage(targetView, targetView, statePage)
    }

    /**
     * 跟要覆盖的view进行绑定关系
     */
    @JvmStatic
    fun <T: AbsStatePage> bind(targetView: View, statePage: T) {
        addStatePage(targetView, getViewGroup(targetView), statePage)
    }

    /**
     * 解除并回收已经进行了绑定关系的StatePage
     */
    @JvmStatic
    fun <T: AbsStatePage> unbind(statePage: T) {
        LogUtil.E("before binderCache size = ${binderCache.size}")
        binderCache.forEach {
            if (it.absStatePage == statePage)
            {
                binderCache.remove(it)
            }
        }
        LogUtil.E("after binderCache size = ${binderCache.size}")
    }

    /**
     * 显示单个statePage
     * 动态addView方式
     */
    @JvmStatic
    fun <T: AbsStatePage> show(statePage: T) : T? {
        LogUtil.E("binderCache size = ${binderCache.size}")
        binderCache.forEach {
            if (it.absStatePage == statePage) {
                if (it.targetView == null || it.targetViewParent == null)
                    return null
                if (it.targetViewParent.indexOfChild(statePage.toViewGroup()) == -1) {
                    //TODO startx和starty可能需要计算toolbar之类的
                    statePage.setViewParams(
                            it.targetView.x,
                            it.targetView.y,
                            it.targetView.layoutParams.width,
                            it.targetView.layoutParams.height
                    )
                    LogUtil.E("before child list size = ${it.targetViewParent.childCount}")
                    it.targetViewParent.addView(statePage.toViewGroup(), it.targetViewParent.childCount)
                    LogUtil.E("after child list size = ${it.targetViewParent.childCount}")
                }
                return statePage
            }
        }
        return null
    }

    /**
     * 隐藏单个statePage
     */
    @JvmStatic
    fun <T: AbsStatePage> dismiss(statePage: T) : T? {
        LogUtil.E("binderCache size = ${binderCache.size}")
        binderCache.forEach {
            if (it.absStatePage == statePage) {
                if (it.targetView == null || it.targetViewParent == null)
                    return null
                if (it.targetViewParent.indexOfChild(statePage.toViewGroup()) != -1) {
                    LogUtil.E("before child list size = ${it.targetViewParent.childCount}")
                    it.targetViewParent.removeView(statePage.toViewGroup())
                    LogUtil.E("after child list size = ${it.targetViewParent.childCount}")
                }
                return statePage
            }
        }
        return null
    }

    /**
     * 将statePage格式化成一个StatePageBinder对象并保存在binderCache中
     */
    private fun <T: AbsStatePage> addStatePage(targetView: View, targetViewGroup: ViewGroup,statePage: T) {
        val binder = StatePageBinder(
                targetView.context.javaClass.simpleName,
                targetView.javaClass.name,
                targetView,
                targetViewGroup,
                statePage
        )
        LogUtil.E("addStatePage = $binder")
        if (!binderCache.contains(binder)) {
            binderCache.add(binder)
        }
    }

    /**
     * 获取需要被覆盖的view的父布局对象viewGroup
     */
    private fun getViewGroup(targetView: View) : ViewGroup {
        var targetViewGroup = targetView.parent as ViewGroup?
        //  如果此view本身就是整个页面的最外层布局的话，则没有viewGroup
        if (targetViewGroup == null) {
            targetViewGroup = targetView as ViewGroup
        }
        return targetViewGroup
    }

    @Deprecated("更换思路, 不再需要使用")
    private fun findTargetView(targetView: View): Int {
        val viewParent = targetView.parent as ViewGroup?
        viewParent?.let {
            for (i in 0 until viewParent.childCount) {
                if (viewParent.getChildAt(i) == targetView) {
                    return i
                }
            }
        }
        return 0
    }
}