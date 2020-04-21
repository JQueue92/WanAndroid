package com.jqueue.wanandroid.utils

import android.view.View
import com.jqueue.wanandroid.base.Loading
import java.lang.ref.WeakReference

class ObserverView<T : View>(vararg views: WeakReference<T>) {
    private val viewArray: List<WeakReference<T>> = views.toList()

    fun handleViewState(t: Any?) {
        LogUtil.d(t.toString())
        when (t) {
            Loading.START -> {
                viewArray.forEach {
                    (it.get() as? View)?.isEnabled = false
                }
            }
            Loading.COMPLETED, Loading.ERROR -> {
                viewArray.forEach {
                    (it.get() as? View)?.isEnabled = true
                }
            }
        }
    }
}