package com.jqueue.wanandroid.login

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.jqueue.wanandroid.MainActivity
import com.jqueue.wanandroid.R
import com.jqueue.wanandroid.base.BaseActivity
import com.jqueue.wanandroid.viewmodel.WanViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initListener()
        window.decorView.setPadding(0, 0, 0, 0)
    }

    private fun initListener() {
        loginBtn.setOnClickListener {
            ViewModelProviders.of(this).get(WanViewModel::class.java)
                .login(userNameEdit.text.toString().trim(), pwdEdit.text.toString()).observe(this,
                    Observer {
                        if (it.data == null) {
                            Toast.makeText(this, it.errorMsg, Toast.LENGTH_SHORT).show()
                        } else {
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                    })
        }
    }
}
