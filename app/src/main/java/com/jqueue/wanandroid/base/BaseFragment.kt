package com.jqueue.wanandroid.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.jqueue.wanandroid.utils.StatusBarUtil

open class BaseFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setPadding(0, StatusBarUtil.getStatusBarHeight(context!!), 0, 0)
    }
}