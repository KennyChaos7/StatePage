package com.statepage

import android.util.Log

object LogUtil {

    fun D(text: String) {
        if (BuildConfig.DEBUG) {
            Log.d("StatePage", text)
        }
    }

    fun I(text: String) {
        if (BuildConfig.DEBUG) {
            Log.i("StatePage", text)
        }
    }

    fun E(text: String) {
        if (BuildConfig.DEBUG) {
            Log.e("StatePage", text)
        }
    }
}