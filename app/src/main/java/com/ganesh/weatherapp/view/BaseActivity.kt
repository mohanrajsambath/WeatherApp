package com.ganesh.weatherapp.view


import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import android.net.ConnectivityManager


abstract class BaseActivity : FragmentActivity() {

    val requesCode = 11

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        initDI()

    }

    abstract fun initDI()

    fun showMessage(message: String) {

        Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_SHORT
        ).show()

    }


    fun isNetworkConnected(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
    }


}