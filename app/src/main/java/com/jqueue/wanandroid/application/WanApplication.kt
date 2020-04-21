package com.jqueue.wanandroid.application

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.multidex.MultiDex
import com.jqueue.wanandroid.BuildConfig
import com.jqueue.wanandroid.cookie.CookieManger
import com.jqueue.wanandroid.network.CheckNetworkIntercepter
import com.jqueue.wanandroid.utils.LogUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WanApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
        LogUtil.d(context.cacheDir.absolutePath)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    companion object {
        @Volatile
        private var retrofit: Retrofit? = null
        private lateinit var context: Context
        val mainHandler = Handler(Looper.getMainLooper())
        fun getRetrofit(): Retrofit {
            if (retrofit == null) {
                synchronized(this) {
                    if (retrofit == null) {
                        retrofit = Retrofit.Builder().client(
                            OkHttpClient.Builder()
                                .addInterceptor(HttpLoggingInterceptor().apply {
                                    level = HttpLoggingInterceptor.Level.BODY
                                })
                                .cookieJar(CookieManger(context))
                                .addInterceptor(CheckNetworkIntercepter(context))
                                .cache(Cache(context.cacheDir, 6 * 1024 * 1024))
                                .build()
                        ).baseUrl(BuildConfig.baseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                    }
                }
            }
            return retrofit!!
        }
    }
}