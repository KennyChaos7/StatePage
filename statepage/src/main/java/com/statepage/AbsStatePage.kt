package com.statepage

import android.app.Activity
import android.view.LayoutInflater
import android.view.View

/**
 * @param layoutRes
 * @param coverView     要显示在哪个view之上, 用于动态设置statePage的大小
 */
abstract class AbsStatePage(private val layoutRes: Int,private val coverView: View, private val listener: RetryListener?) {

    private var contentView: View? = null
    private lateinit var _bindKey: String


    /**
     * 是否遮挡整个页面
     * 如果true, 则显示时候回会添加在android.R.id.content中
     */
//    var isCoverWholeView: Boolean = false
//
//    fun setIsCoverWholeView(isCoverWholeView: Boolean) {
//        this.isCoverWholeView = isCoverWholeView
//    }

    fun bind(activity: Activity) {
        _bindKey = activity.localClassName
        contentView = LayoutInflater.from(activity).inflate(layoutRes, null, false)

    }


    fun dismiss() {

    }

    fun getBindKey() = _bindKey

    interface RetryListener {
        fun startRetry()
    }


}