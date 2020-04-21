package com.jqueue.wanandroid.viewholder

import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.animation.doOnEnd
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jqueue.wanandroid.HomeAdapter
import com.jqueue.wanandroid.R
import com.jqueue.wanandroid.bean.ArticleListBean
import com.jqueue.wanandroid.bean.BannerBean
import com.jqueue.wanandroid.bean.BannerPic
import com.jqueue.wanandroid.fragment.LOAD_URL
import com.jqueue.wanandroid.fragment.TITLE

class HomeViewHolder(childView: View) : BaseViewHolder(childView) {
    val animator = ObjectAnimator.ofFloat(itemView, View.ALPHA, 1f, 0f, 1f).apply {
        repeatCount = INFINITE
        duration = 1000L
        doOnEnd { itemView.alpha = 1f }
    }

    var headViewHolder: BaseViewHolder = BaseViewHolder(
        LayoutInflater.from(itemView.context).inflate(
            R.layout.banner_layout,
            null
        )
    ).apply {
        itemView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                170F,
                itemView.context.resources.displayMetrics
            ).toInt()
        )
    }

    fun showPlaceholder() {
        animator.start()
        getView<ImageView>(R.id.img)
    }

    fun bindView(articleListBean: ArticleListBean) {
        animator.end()
        getView<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = HomeAdapter(articleListBean.data.datas).apply {
                addHeaderView(headViewHolder.itemView)
                setOnItemChildClickListener { adapter, view, position ->
                    view.findNavController().navigate(
                        R.id.action_homeFragment_to_webFragment,
                        bundleOf(LOAD_URL to data[position].link, TITLE to data[position].title)
                    )
                }
            }
        }
    }

    fun bindHeadView(bannerBean: BannerBean) {
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