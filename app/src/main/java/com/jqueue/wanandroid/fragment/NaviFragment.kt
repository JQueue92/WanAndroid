package com.jqueue.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.material.tabs.TabLayoutMediator
import com.jqueue.wanandroid.utils.StatusBarUtil
import com.jqueue.wanandroid.R
import com.jqueue.wanandroid.base.BaseFragment
import com.jqueue.wanandroid.bean.Article
import com.jqueue.wanandroid.bean.Data
import com.jqueue.wanandroid.bean.NaviBean
import com.jqueue.wanandroid.viewmodel.WanViewModel
import kotlinx.android.synthetic.main.fragment_navi.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [NaviFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [NaviFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NaviFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navi, container, false)
    }

    override fun onResume() {
        super.onResume()
        ViewModelProviders.of(activity!!).get(WanViewModel::class.java).getNavi()
            .observe(viewLifecycleOwner,
                Observer {
                    if (it is NaviBean) {
                        bindView(it)
                    }
                })
    }

    private fun bindView(naviBean: NaviBean) {
        //ViewPager2
        viewPager2.adapter = object :
            BaseQuickAdapter<Data, BaseViewHolder>(R.layout.list_navi_item, naviBean.data) {
            override fun convert(helper: BaseViewHolder, item: Data?) {
                helper.getView<RecyclerView>(R.id.recyclerView).apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = object : BaseQuickAdapter<Article, BaseViewHolder>(
                        R.layout.list_text_item,
                        item?.articles
                    ) {
                        override fun convert(helper: BaseViewHolder, item: Article?) {
                            helper.setText(R.id.nameTxt, item?.title)
                            helper.itemView.setOnClickListener {
                                helper.itemView.findNavController().navigate(
                                    R.id.action_naviFragment_to_webFragment,
                                    bundleOf(
                                        LOAD_URL to item?.link,
                                        TITLE to item?.title
                                    )
                                )
                            }
                        }

                    }
                }
            }
        }

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = naviBean.data[position].name
        }.attach()


    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NaviFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
