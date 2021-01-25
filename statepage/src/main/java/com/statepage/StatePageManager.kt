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
            if (it.absStatePage == statePage)
            {
                statePage.toViewGroup().visibility = View.VISIBLE
                return statePage
            }
        }
        return null
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

    private fun <T: AbsStatePage> addStatePage(targetView: View, statePage: T) {
        val binder = StatePageBinder(
                targetView.context.javaClass.simpleName,
                targetView.javaClass.name,
                statePage
        )
        if (!binderCache.contains(binder)) {
            var targetViewGroup = targetView.parent as ViewGroup?
            if (targetViewGroup == null) {
                targetViewGroup = targetView as ViewGroup
            }
            statePage.setViewParamsAndHide(targetViewGroup.layoutParams.width, targetViewGroup.layoutParams.height)
            LogUtil.E("before child list size = ${targetViewGroup.childCount}")
            targetViewGroup.addView(statePage.toViewGroup())
            LogUtil.E("after child list size = ${targetViewGroup.childCount}")
            binderCache.add(binder)
        }
    }
}