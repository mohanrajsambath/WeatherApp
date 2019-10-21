package com.ganesh.weatherapp.view_model


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


open class BaseViewModel : ViewModel() {

    var errorResponseLiveData = MutableLiveData<String>()
    var showLoadingLiveData = MutableLiveData<Boolean>()

    val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}