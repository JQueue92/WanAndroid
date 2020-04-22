package com.jqueue.wanandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jqueue.wanandroid.application.WanApplication
import com.jqueue.wanandroid.base.LoadState
import com.jqueue.wanandroid.extension.catchException
import com.jqueue.wanandroid.service.WanService

class WanViewModel : ViewModel() {

    fun getNavi() = liveData {
        emit(LoadState.START)
        try {
            emit(WanApplication.getRetrofit().create(WanService::class.java).getNavi())
            emit(LoadState.COMPLETED)
        } catch (t: Throwable) {
            t.printStackTrace()
            emit(LoadState.ERROR)
        }
    }

    fun getHomeArticleList(pageIndex: Int = 0) = liveData {
        emit(LoadState.START)
        try {
            emit(
                WanApplication.getRetrofit().create(WanService::class.java).getHomeArticleList(
                    pageIndex
                )
            )
            emit(LoadState.COMPLETED)
        } catch (t:Throwable){
            t.printStackTrace()
            emit(LoadState.ERROR)
        }
    }

    fun getBanner() = liveData {
        emit(LoadState.START)
        try {
            emit(WanApplication.getRetrofit().create(WanService::class.java).getBanner())
            emit(LoadState.COMPLETED)
        } catch (t:Throwable){
            emit(LoadState.ERROR)
        }
    }

    fun login(name: String, pwd: String) = liveData {
        emit(LoadState.START)
        try {
            emit(WanApplication.getRetrofit().create(WanService::class.java).login(name, pwd))
            emit(LoadState.COMPLETED)
        } catch (e:Throwable){
            e.printStackTrace()
            emit(LoadState.ERROR)
        }
    }

    fun getUserCoin() = liveData {
        catchException {
            emit(WanApplication.getRetrofit().create(WanService::class.java).getUserCoin())
        }
    }
}