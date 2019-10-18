package com.ganesh.weatherapp.view


import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ganesh.data.model.ErrorMessage
import com.ganesh.di.DaggerViewModelFactory
import com.ganesh.weatherapp.WeatherApplication
import com.ganesh.weatherapp.R
import com.ganesh.weatherapp.databinding.ActivityMainBinding
import com.ganesh.weatherapp.binding.DataUtil
import com.ganesh.weatherapp.binding.WeatherButtonCallback
import com.ganesh.weatherapp.binding.WeatherData
import com.ganesh.weatherapp.view_model.WeatherViewModel
import com.tamil.galassignment.data.model.CityWeatherModel
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
            setErrorMessage()
            setValueToViews(it)
        })

        viewModel.showLoadingLiveData.observe(this, androidx.lifecycle.Observer {

            setProgressbar(it)
        })

        viewModel.errorResponseLiveData.observe(this, androidx.lifecycle.Observer {

            if (!isNetworkConnected()) {
                setErorrMessage(getString(R.string.internet_connection))
            } else {
                setErorrMessage(it)
            }


        })

    }


    private fun setProgressbar(visibility: Boolean) {

        binding!!.dataUitll!!.canShowProgressBar = visibility
        binding!!.dataUitll = utilData

    }

    fun setErorrMessage(message: String) {

        val modifiedMessage = ErrorMessage.getMessage(message)
        binding!!.errorMessage = modifiedMessage
        binding!!.dataUitll!!.errorTextVisibilty = true
        setEmptyValues()

    }

    private fun setErrorMessage() {

        binding!!.errorMessage = ""
        binding!!.dataUitll!!.errorTextVisibilty = false

    }


    fun setValueToViews(cityWeatherData: CityWeatherModel) {

        weatherData!!.setValueToFields(cityWeatherData)
        binding!!.weatherinfo = weatherData

    }


    private fun setEmptyValues() {

        weatherData!!.setEmptyValues()
        binding!!.weatherinfo = weatherData

    }


}
