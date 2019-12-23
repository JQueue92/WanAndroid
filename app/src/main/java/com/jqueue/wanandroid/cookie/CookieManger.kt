package com.jqueue.wanandroid.cookie

import android.content.Context
import android.util.Log
import com.jqueue.wanandroid.BuildConfig
import com.jqueue.wanandroid.cookie.entity.CookieWrapper
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class CookieManger(val context: Context) : CookieJar {

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        WanDatabase.getDatabase(context).cookieDao().apply {
            clearCookie(url.host)
            saveCookie(cookies.mapIndexed { index, cookie -> wrapperCookie(index, url, cookie) })
            if (BuildConfig.DEBUG) {
                cookies.forEach { Log.d("CookieManager", "Cookie:${it}") }
            }
        }
    }


    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return WanDatabase.getDatabase(context).cookieDao().getCookie(url.host).apply {
            if (BuildConfig.DEBUG) {
                forEach { Log.d("CookieManager", "Cookie:${it}") }
            }
        }.map { it.cookie() }
    }

    private fun wrapperCookie(id: Int, url: HttpUrl, cookie: Cookie): CookieWrapper {
        return CookieWrapper(
            "${url.host}-$id",
            cookie.name,
            cookie.value,
            cookie.expiresAt,
            cookie.domain,
            cookie.path,
            cookie.secure,
            cookie.httpOnly,
            cookie.persistent,
            cookie.hostOnly,
            url.host
        )
    }
}