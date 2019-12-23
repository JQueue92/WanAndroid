package com.jqueue.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.jqueue.wanandroid.R
import com.jqueue.wanandroid.base.BaseFragment
import com.jqueue.wanandroid.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.title_layout.*

class MineFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setPadding(0, 0, 0, 0)
        titleLayout.setPadding(0, StatusBarUtil.getStatusBarHeight(context!!), 0, 0)
        titleLayout.apply {
            layoutParams.height += StatusBarUtil.getStatusBarHeight(context)
        }
        titleTxt.text = "我的"
        backView.visibility = View.GONE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Glide.with(headImg).load(R.mipmap.header_img).circleCrop().into(headImg)
    }
}