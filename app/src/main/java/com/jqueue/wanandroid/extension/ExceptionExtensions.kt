package com.jqueue.wanandroid.extension

suspend fun catchException(run: suspend () -> Unit) {
    try {
        run()
    } catch (e:Throwable){
        e.printStackTrace()
    }
}