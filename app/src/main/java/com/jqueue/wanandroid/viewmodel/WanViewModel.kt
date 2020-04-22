package com.jqueue.wanandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jqueue.wanandroid.application.WanApplication
import com.jqueue.wanandroid.base.Loading
import com.jqueue.wanandroid.extension.catchException
import com.jqueue.wanandroid.service.WanService

class WanViewModel : ViewModel() {

    fun getNavi() = liveData {
        emit(Loading.START)
        try {
            emit(WanApplication.getRetrofit().create(WanService::class.java).getNavi())
            emit(Loading.COMPLETED)
        } catch (t: Throwable) {
            t.printStackTrace()
            emit(Loading.ERROR)
        }
    }

    fun getHomeArticleList(pageIndex: Int = 0) = liveData {
        emit(Loading.START)
        try {
            emit(
                WanApplication.getRetrofit().create(WanService::class.java).getHomeArticleList(
                    pageIndex
                )
            )
            emit(Loading.COMPLETED)
        } catch (t:Throwable){
            t.printStackTrace()
            emit(Loading.ERROR)
        }
    }

    fun getBanner() = liveData {
        catchException {
            emit(WanApplication.getRetrofit().create(WanService::class.java).getBanner())
        }
    }

    fun login(name: String, pwd: String) = liveData {
        emit(Loading.START)
        try {
            emit(WanApplication.getRetrofit().create(WanService::class.java).login(name, pwd))
            emit(Loading.COMPLETED)
        } catch (e:Throwable){
            e.printStackTrace()
            emit(Loading.ERROR)
        }
    }

    fun getUserCoin() = liveData {
        catchException {
            emit(WanApplication.getRetrofit().create(WanService::class.java).getUserCoin())
        }
    }
}