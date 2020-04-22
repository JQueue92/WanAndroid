package com.jqueue.wanandroid.utils

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jqueue.wanandroid.base.LoadState
import java.lang.ref.WeakReference

class ObserverView<T : View>(vararg views: WeakReference<T>) {
    private val viewArray: List<WeakReference<T>> = views.toList()

    fun handleViewState(t: Any?) {
        LogUtil.d(t.toString())
        when (t) {
            LoadState.START -> {
                viewArray.forEach {
                    it.get()?.isEnabled = false
                    (it.get() as? SwipeRefreshLayout)?.isRefreshing = true
                }
            }
            LoadState.COMPLETED, LoadState.ERROR -> {
                viewArray.forEach {
                    it.get()?.isEnabled = true
                    (it.get() as? SwipeRefreshLayout)?.isRefreshing = false
                }
            }
        }
    }
}