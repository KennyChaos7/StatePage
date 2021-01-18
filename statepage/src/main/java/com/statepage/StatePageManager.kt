package com.statepage

import android.app.Activity
import java.util.*
import kotlin.collections.HashMap

object StatePageManager {

    private val hashMap = HashMap<String, LinkedList<AbsStatePage>>()

    fun <T: AbsStatePage> show(statePage: T) : T {
        hashMap[statePage.getBindKey()].also {
            if (it == null)
                hashMap[statePage.getBindKey()] = LinkedList()
        }?.let {
            if (!it.contains(statePage)) it.add(statePage)
        }
        return statePage
    }

    fun free(activity: Activity) {
        hashMap[activity.localClassName]?.iterator()?.let {
            while (it.hasNext()) it.remove()
        }
    }
}