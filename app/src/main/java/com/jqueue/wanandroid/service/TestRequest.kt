package com.jqueue.wanandroid.service

import com.jqueue.wanandroid.BuildConfig
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.lang.NullPointerException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

fun main() = runBlocking<Unit> {
    val cookieStore = LinkedHashMap<String, List<Cookie>>()
    val retrofit = Retrofit.Builder().client(
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).addInterceptor {
                val request = it.request()
                val response = it.proceed(request)
                println("networkResponse:${response.networkResponse}")
                println("catcheResponse:${response.cacheResponse}")
                response
            }
            .cookieJar(object : CookieJar {
                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    return cookieStore[url.host]?.let { cookieStore[url.host] }
                        ?: ArrayList<Cookie>()
                }

                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    cookies.forEachIndexed { index, cookie -> println("Index:${index}\tCookie:${cookie}") }
                    cookieStore[url.host] = cookies
                }

            })
            .cache(Cache(File("C:/apk"), 4 * 1024 * 1024))
            .build()
    ).baseUrl(BuildConfig.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //loginUserName=JQK
    //token_pass=461194bd0e823bc6330847a1d570627a
    println(retrofit.create(WanService::class.java).login("JQK", "xushangiuo@390"))

    println("------------------------------")
    println(retrofit.create(WanService::class.java).getUserCoin())
}