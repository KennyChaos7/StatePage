package com.statepage

import android.view.View
import android.view.ViewGroup

data class StatePageBinder(
        val contextName: String,
        val bindName: String,
        val targetView: View,
        val targetViewParent: ViewGroup,
        val absStatePage: AbsStatePage
)
{
    override fun toString(): String {
        return "StatePageBinder(contextName='$contextName', bindName='$bindName', targetView=$targetView, absStatePage=$absStatePage)"
    }
}