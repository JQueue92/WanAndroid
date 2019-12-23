package com.jqueue.wanandroid.network

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.widget.Toast
import com.jqueue.wanandroid.application.WanApplication
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class CheckNetworkIntercepter(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.apply {
                if (getLinkProperties(activeNetwork) == null) {
                    WanApplication.mainHandler.post {
                        Toast.makeText(context, "无网络连接，请检查网络设置！", Toast.LENGTH_SHORT).show()
                    }
                    return Response.Builder().code(ErrorCode.NO_NETWORK_CONNECTED)
                        .message("No Network Connected")
                        .protocol(Protocol.HTTP_1_1)
                        .body("{\"errorMsg\":\"无网络连接\"}".toResponseBody("text/plain".toMediaType()))
                        .request(chain.request()).build()
                }
            }
        } else {
            if (connectivityManager.activeNetworkInfo == null || !connectivityManager.activeNetworkInfo!!.isConnected) {
                WanApplication.mainHandler.post {
                    Toast.makeText(context, "无网络连接，请检查网络设置！", Toast.LENGTH_SHORT).show()
                }

                return Response.Builder().code(ErrorCode.NO_NETWORK_CONNECTED)
                    .message("No Network Connected")
                    .protocol(Protocol.HTTP_1_1)
                    .body("{\"errorMsg\":\"无网络连接\"}".toResponseBody("text/plain".toMediaType()))
                    .request(chain.request()).build()
            }
        }
        return chain.proceed(chain.request())
    }
}