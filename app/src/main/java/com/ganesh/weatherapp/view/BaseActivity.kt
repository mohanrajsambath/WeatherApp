package com.ganesh.weatherapp.view


import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


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


    @Suppress("DEPRECATION")
    fun isNetworkConnected(): Boolean {
        var result = false

        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }

        } else {

            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }

        }

        return result
    }


}