package com.ganesh.weatherapp.view_model


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {

    var errorResponseLiveData = MutableLiveData<String>()
    var showLoadingLiveData = MutableLiveData<Boolean>()

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}