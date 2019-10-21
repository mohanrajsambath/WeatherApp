package com.ganesh.weatherapp


import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.mockito.*
import androidx.arch.core.executor.testing.*
import com.ganesh.weatherapp.data.model.CityWeatherModel
import com.ganesh.weatherapp.data.repo.AppApiHelper
import com.ganesh.weatherapp.view_model.WeatherViewModel
import org.mockito.Mockito.*
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.disposables.Disposable
import io.reactivex.Scheduler
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit


class WeatherViewModeTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var apiService: AppApiHelper

    @InjectMocks
    var viewModel: WeatherViewModel = WeatherViewModel()

    lateinit var spyViewModel: WeatherViewModel

    private var testSingle: Single<CityWeatherModel>? = null


    @Test
    fun successResponse() {

        val data = TestData()

        val result: Single<CityWeatherModel> = Single.just(data.getData())


        `when`(apiService.searchWeather("Berlin", BuildConfig.API_KEY))
            .thenReturn(result)

        spyViewModel.getWeather(
            "Berlin"
        )

        verify(spyViewModel, times(1)).successMessage(data.getData())

        verify(spyViewModel, times(0)).onErrorMessage(Throwable())

    }


    @Test
    fun errorResponse() {

        val data = TestData()

        val throwable = Throwable("Http 404 city Name Not found")

        testSingle = Single.error(throwable)

        `when`(apiService.searchWeather("Berlin", BuildConfig.API_KEY))
            .thenReturn(testSingle)

        spyViewModel.getWeather("Berlin")

        verify(spyViewModel, times(1)).onErrorMessage(throwable)

        verify(spyViewModel, times(0)).successMessage(data.getData())


    }

    @Before
    fun setUpRxSchedulers() {

        MockitoAnnotations.initMocks(this)

        spyViewModel = spy(viewModel)

        val immediate = object : Scheduler() {

            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }


}