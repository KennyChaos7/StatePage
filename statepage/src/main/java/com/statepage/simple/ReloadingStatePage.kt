package com.statepage.simple

import android.content.Context
import com.statepage.AbsStatePage
import com.statepage.R

class ReloadingStatePage(context: Context, listener: () -> Unit) : AbsStatePage(context, R.layout.layout_reloading_statepage)
{
    init {
        findView(R.id.ivReload).setOnClickListener { listener.invoke() }
    }
}
