package com.statepage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout


abstract class AbsStatePage(context: Context, layoutRes: Int) {

    private val frameLayout: FrameLayout = FrameLayout(context)
    private var _contentView: View

    init {
        val layoutParams = FrameLayout.LayoutParams(0,0)
        frameLayout.layoutParams = layoutParams
        _contentView = LayoutInflater.from(context).inflate(layoutRes, frameLayout, true)
    }

    fun setViewParams(startX: Float, startY: Float, width: Int, height: Int) {
        val params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(width, height)
        frameLayout.layoutParams = params
        frameLayout.x = startX
        frameLayout.y = startY
    }

    fun toViewGroup() = frameLayout
}