package com.ganesh.weatherapp.binding

import android.view.View
import androidx.databinding.BindingAdapter


class DataUtil {
    var canShowProgressBar: Boolean? = null
    var errorTextVisibilty: Boolean? = null
}


@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean?) {

    if (value == null) {
        view.visibility = View.GONE
    } else {
        view.visibility = (if (value) View.VISIBLE else View.GONE)
    }

}







