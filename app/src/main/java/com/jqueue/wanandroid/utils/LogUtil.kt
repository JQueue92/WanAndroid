package com.jqueue.wanandroid.utils

import android.util.Log
import com.jqueue.wanandroid.BuildConfig

object LogUtil {
    fun d(s: String) {
        if (BuildConfig.DEBUG) {
            Log.d("WanAndroid", s)
        }
    }
}