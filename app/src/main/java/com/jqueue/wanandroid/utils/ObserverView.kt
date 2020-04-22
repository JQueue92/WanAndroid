package com.jqueue.wanandroid.utils

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jqueue.wanandroid.base.Loading
import java.lang.ref.WeakReference

class ObserverView<T : View>(vararg views: WeakReference<T>) {
    private val viewArray: List<WeakReference<T>> = views.toList()

    fun handleViewState(t: Any?) {
        LogUtil.d(t.toString())
        when (t) {
            Loading.START -> {
                viewArray.forEach {
                    it.get()?.isEnabled = false
                    (it.get() as? SwipeRefreshLayout)?.isRefreshing = true
                }
            }
            Loading.COMPLETED, Loading.ERROR -> {
                viewArray.forEach {
                    it.get()?.isEnabled = true
                    (it.get() as? SwipeRefreshLayout)?.isRefreshing = false
                }
            }
        }
    }
}