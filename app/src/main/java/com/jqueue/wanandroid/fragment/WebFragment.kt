package com.jqueue.wanandroid.fragment


import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.navigation.NavHostController
import androidx.navigation.fragment.findNavController

import com.jqueue.wanandroid.R
import com.jqueue.wanandroid.base.BaseFragment
import com.jqueue.wanandroid.utils.LogUtil
import kotlinx.android.synthetic.main.fragment_web.*
import kotlinx.android.synthetic.main.title_layout.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val LOAD_URL = "loadUrl"
const val TITLE = "title"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WebFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var loadUrl: String? = null
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            loadUrl = it.getString(LOAD_URL)
            title = it.getString(TITLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    override fun onStart() {
        super.onStart()
        webView.settings.apply {
            setSupportZoom(true)
            displayZoomControls = false
            builtInZoomControls = true
            javaScriptEnabled = true
            useWideViewPort = true
            domStorageEnabled = true
        }

        webView.apply {
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    progressBar.progress = newProgress
                    progressBar.visibility = if (newProgress != 100) View.VISIBLE else View.GONE
                    LogUtil.d("progress:${newProgress}")
                }

                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                    titleTxt.text = title
                    LogUtil.d("title:$title")
                }
            }
            webViewClient = object : WebViewClient() {

            }
        }

        webView.loadUrl(loadUrl)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                findNavController().navigateUp()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        backView.setOnClickListener { findNavController().navigateUp() }
    }

    override fun onStop() {
        super.onStop()
        webView.apply {
            webChromeClient = null
            webViewClient = null
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param loadUrl Parameter 1.
         * @param title Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(loadUrl: String, title: String) =
            WebFragment().apply {
                arguments = Bundle().apply {
                    putString(LOAD_URL, loadUrl)
                    putString(TITLE, title)
                }
            }
    }
}
