package com.ganesh.weatherapp


import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ganesh.weatherapp.di.DaggerViewModelFactory
import com.ganesh.weatherapp.binding.DataUtil
import com.ganesh.weatherapp.binding.WeatherButtonCallback
import com.ganesh.weatherapp.binding.WeatherData
import com.ganesh.weatherapp.databinding.ActivityMainBinding
import com.ganesh.weatherapp.view.BaseActivity
import com.ganesh.weatherapp.view.VoiceInvoker
import com.ganesh.weatherapp.view_model.WeatherViewModel
import com.ganesh.weatherapp.data.model.CityWeatherModel
import javax.inject.Inject


class WeatherActivity : BaseActivity(), WeatherButtonCallback {

    private var binding: ActivityMainBinding? = null

    //visisbilities satus of views
    private var utilData: DataUtil? = null

    // weather info to bind with views
    private var weatherData: WeatherData? = null

    @Inject
    lateinit var viewModel: WeatherViewModel

    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        // init intent to call RECOGNIZE_SPEECH"
        VoiceInvoker.initIntent(applicationContext)

        // init all views
        initUI()

    }


    // to init all user controlls
    private fun initUI() {

        this.utilData = DataUtil()

        weatherData = WeatherData(applicationContext)

        binding!!.buttonCallback = this

        binding!!.dataUitll = utilData

        viewModelObserver()

        //collectWeatherFromServer("Berlin")

    }

    // init dagger2
    override fun initDI() {
        (application as WeatherApplication).initComponet().inject(this)
    }

    // collecting weather infor from viewmodel
    private fun collectWeatherFromServer(cityName: String) {
        viewModel.getWeather(cityName)
    }


    // called when user click the button (to know the weather of a city)
    override
    fun onClick(v: View) {

        callVoiceRecgnier()

    }


    // to invoke activity of voice reconginzer
    private fun callVoiceRecgnier() {

        try {

            startActivityForResult(VoiceInvoker.getIntent(), requesCode)

        } catch (a: ActivityNotFoundException) {

            showMessage(getString(R.string.mobile_not_suppoer))

        }
    }

    override
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            requesCode -> {

                if (resultCode == AppCompatActivity.RESULT_OK && null != data) {

                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

                    if (result!!.isNotEmpty()) {

                        val cityName: String? = result[0] as String

                        if (cityName!!.isNotEmpty())
                        //sending city name to viewmodel
                            collectWeatherFromServer(cityName)

                    }

                }

            }


        }


    }

    // observer for live data used in viewmodel
    private fun viewModelObserver() {

        viewModel.weatherResponseLiveData.observe(this, Observer {
            setErorrMessage("")
            setErrorMessageVisibilties(false)
            setWeatherDataToViews(it)
        })

        viewModel.showLoadingLiveData.observe(this, androidx.lifecycle.Observer {
            setProgressbarVisibilties(it)
        })

        viewModel.errorResponseLiveData.observe(this, androidx.lifecycle.Observer {

            setErrorMessageVisibilties(true)

            if (!isNetworkConnected()) {
                setErorrMessage(getString(R.string.internet_connection))
            } else {
                setErorrMessage(it)
            }


        })

    }

    /**
     * setting progressbar visibilty
     * @param visibility based on boolean values, progressbar is shown
     */
    private fun setProgressbarVisibilties(visibility: Boolean) {
        binding!!.dataUitll!!.canShowProgressBar = visibility
        binding!!.dataUitll = utilData
    }

    /**
     * showing error message
     * @param message error message
     */
    fun setErorrMessage(message: String) {
        binding!!.errorMessage = message
        resetWeatherDataViews()
    }


    /**
     * setting error messge views visibilty
     * @param visibility based on boolean values, progressbar is shown
     */
    private fun setErrorMessageVisibilties(visibility: Boolean) {
        binding!!.dataUitll!!.errorTextVisibilty = visibility
        binding!!.dataUitll = utilData
    }


    /**
     * show weather data to user
     * @param cityWeatherData contains weather info
     */
    fun setWeatherDataToViews(cityWeatherData: CityWeatherModel) {
        weatherData!!.setValueToFields(cityWeatherData)
        binding!!.weatherinfo = weatherData
    }


    /**
     * clear all data
     */
    private fun resetWeatherDataViews() {
        weatherData!!.setEmptyValues()
        binding!!.weatherinfo = weatherData
    }


}
