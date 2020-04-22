package com.jqueue.wanandroid

import androidx.appcompat.widget.AppCompatImageView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jqueue.wanandroid.bean.DataX
import com.jqueue.wanandroid.fragment.LOAD_URL
import com.jqueue.wanandroid.fragment.TITLE

class HomeAdapter(list: List<DataX> = arrayListOf()) :
    BaseQuickAdapter<DataX, BaseViewHolder>(R.layout.list_home_item, list) {
    override fun convert(helper: BaseViewHolder, item: DataX?) {
        val autherOrSharedUser =
            if (item!!.author.isEmpty()) "作者:${item.author}" else "分享人:${item.shareUser}"
        val subDescTxt =
            "$autherOrSharedUser  分类:${item.superChapterName}/${item.chapterName}  时间:${item.niceShareDate}"
        helper.setText(R.id.titleTxt, item?.title)
            .setText(R.id.subDescTxt, subDescTxt)
            .getView<AppCompatImageView>(R.id.collectImg).isSelected = item?.collect

        helper.itemView.setOnClickListener {
            helper.itemView.findNavController().navigate(
                R.id.action_homeFragment_to_webFragment,
                bundleOf(
                    LOAD_URL to item?.link,
                    TITLE to item?.title
                )
            )
        }
    }
}