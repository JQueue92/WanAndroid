package com.jqueue.wanandroid.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.os.bundleOf
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jqueue.wanandroid.HomeAdapter
import com.jqueue.wanandroid.R
import com.jqueue.wanandroid.application.WanApplication
import com.jqueue.wanandroid.base.BaseFragment
import com.jqueue.wanandroid.bean.ArticleListBean
import com.jqueue.wanandroid.bean.BannerBean
import com.jqueue.wanandroid.bean.BannerPic
import com.jqueue.wanandroid.utils.ObserverView
import com.jqueue.wanandroid.utils.StatusBarUtil
import com.jqueue.wanandroid.viewmodel.WanViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.ref.WeakReference

class HomeFragment : BaseFragment() {
    private var pageIndex: Int = 0
    private lateinit var headViewHolder: BaseViewHolder
    private lateinit var homeAdapter: HomeAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        headViewHolder = BaseViewHolder(inflater.inflate(R.layout.banner_layout, null)).apply {
            itemView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    170F,
                    resources.displayMetrics
                ).toInt() + StatusBarUtil.getStatusBarHeight(context!!)
            )
        }
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setPadding(0, 0, 0, 0)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ViewModelProvider.NewInstanceFactory().create(WanViewModel::class.java).apply {
            MediatorLiveData<Any>().apply {
                val observer = Observer<Any> {
                    ObserverView(views = *arrayOf(WeakReference(swipeRefreshLayout))).handleViewState(it)
                    when (it) {
                        is ArticleListBean -> {
                            bindView(it)
                        }
                        is BannerBean -> {
                            bindHeadView(it)
                        }
                    }
                }
                addSource(getHomeArticleList(pageIndex).apply { observe(viewLifecycleOwner,observer) }, observer)
                addSource(getBanner().apply {
                    observe(viewLifecycleOwner,observer)
                }, observer)
            }
        }
        homeAdapter = HomeAdapter().apply {
        }
    }

    private fun bindView(articleListBean: ArticleListBean) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        homeAdapter.setNewData(articleListBean.data.datas)
        recyclerView.adapter = homeAdapter.apply {
            addHeaderView(headViewHolder.itemView)
            setOnItemChildClickListener { adapter, view, position ->
                view.findNavController().navigate(
                    R.id.action_homeFragment_to_webFragment,
                    bundleOf(LOAD_URL to data[position].link, TITLE to data[position].title)
                )
            }
        }
    }

    private fun bindHeadView(bannerBean: BannerBean) {
        headViewHolder.getView<ViewPager2>(R.id.banner).apply {
            adapter = object : BaseQuickAdapter<BannerPic, BaseViewHolder>(
                R.layout.list_imageview_item,
                bannerBean.data
            ) {
                override fun convert(helper: BaseViewHolder, item: BannerPic?) {
                    helper.getView<AppCompatImageView>(R.id.img).apply {
                        Glide.with(this).load(item?.imagePath).centerCrop().into(this)
                        setOnClickListener {
                            findNavController().navigate(
                                R.id.action_homeFragment_to_webFragment,
                                bundleOf(
                                    LOAD_URL to item?.url,
                                    TITLE to item?.title
                                )
                            )
                        }
                    }
                    helper.setText(R.id.bannerDescTxt, item?.desc)
                    helper.setAlpha(R.id.bannerDescTxt, 0.5F)
                }
            }
        }
    }
}