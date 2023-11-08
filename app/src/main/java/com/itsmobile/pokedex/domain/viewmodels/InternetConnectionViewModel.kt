package com.itsmobile.pokedex.domain.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InternetConnectionViewModel: ViewModel() {

    var isConnected = MutableLiveData<Boolean>()

    fun isNetworkAvailable(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        isConnected.value = activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}