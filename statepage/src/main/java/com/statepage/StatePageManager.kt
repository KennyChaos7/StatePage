package com.statepage

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import java.util.concurrent.CopyOnWriteArraySet

object StatePageManager {
    private val binderCache: CopyOnWriteArraySet<StatePageBinder> = CopyOnWriteArraySet()

    fun <T: AbsStatePage> bind(activity: Activity, statePage: T) {
        val targetView = activity.findViewById<ViewGroup>(android.R.id.content)
        addStatePage(targetView, statePage)
    }


    fun <T: AbsStatePage> bind(targetView: View, statePage: T) {
        addStatePage(targetView, statePage)
    }

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

    private fun <T: AbsStatePage> addStatePage(targetView: View, statePage: T) {
        val binder = StatePageBinder(
                targetView.context.javaClass.simpleName,
                targetView.javaClass.name,
                targetView,
                getViewGroup(targetView),
                statePage
        )
        LogUtil.E("addStatePage = $binder")
        if (!binderCache.contains(binder)) {
            binderCache.add(binder)
        }
    }

    private fun getViewGroup(targetView: View) : ViewGroup {
        var targetViewGroup = targetView.parent as ViewGroup?
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