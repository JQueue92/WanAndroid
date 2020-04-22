package com.jqueue.wanandroid.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jqueue.wanandroid.MainActivity
import com.jqueue.wanandroid.R
import com.jqueue.wanandroid.base.BaseActivity
import com.jqueue.wanandroid.bean.LoginBean
import com.jqueue.wanandroid.bean.NaviBean
import com.jqueue.wanandroid.utils.ObserverView
import com.jqueue.wanandroid.viewmodel.WanViewModel
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.ref.WeakReference
import kotlin.properties.Delegates

class LoginActivity : BaseActivity() {

    private var isBtnEnable: Boolean by Delegates.observable(false) { _, oldValue, newValue ->
        Toast.makeText(this, "odlValue:${oldValue}\tnewValue:${newValue}", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initListener()
        window.decorView.setPadding(0, 0, 0, 0)
    }

    private fun initListener() {
        loginBtn.setOnClickListener {
            ViewModelProvider.NewInstanceFactory().create(WanViewModel::class.java)
                .login(userNameEdit.text.toString().trim(), pwdEdit.text.toString()).observe(this,
                    Observer {
                        ObserverView(views = *arrayOf(WeakReference(loginBtn))).handleViewState(it)
                        when (it) {
                            is LoginBean -> {
                                if (it.data == null) {
                                    Toast.makeText(this, it.errorMsg, Toast.LENGTH_SHORT).show()
                                } else {
                                    startActivity(Intent(this, MainActivity::class.java))
                                }
                            }
                        }

                    })
        }
    }

    override fun onResume() {
        super.onResume()
        ViewModelProvider.NewInstanceFactory().create(WanViewModel::class.java)
            .getNavi().observe(this, Observer {
                when (it) {
                    is NaviBean -> {
                        Toast.makeText(this, "load Successful", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }
}
