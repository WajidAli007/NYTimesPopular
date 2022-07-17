package com.wajid.nytimespopular.utils

import android.content.Context
import android.net.ConnectivityManager

class ConnectivityUtilImpl(private val context: Context) : ConnectivityUtil {

    override fun isConnectedToInternet(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = connectivityManager?.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}

interface ConnectivityUtil {
    fun isConnectedToInternet(): Boolean
}