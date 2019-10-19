package com.ganesh.weatherapp.view_model


import com.ganesh.data.model.UseCaseResult
import com.ganesh.data.repo.APIRepository
import com.ganesh.weatherapp.repo.BaseTest
import com.ganesh.weatherapp.util.TestData
import com.tamil.galassignment.data.model.CityWeatherModel
import com.tamil.galassignment.data.remote.APIInterface
import com.tamil.galassignment.data.repo.APIRepoInterface
import junit.framework.TestCase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking

import kotlinx.coroutines.test.TestCoroutineDispatcher

import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.mock

class WeatherViewModelTest : BaseTest() {

    private  val mockAPI: APIInterface = mock(APIInterface::class.java)

    @InjectMocks
    var apiRepository: APIRepoInterface = APIRepository(mockAPI)

    private lateinit var spyViewModel: WeatherViewModel


    @InjectMocks
    val viewModel = WeatherViewModel(apiRepository)


    @Before
    fun initAll() {
        MockitoAnnotations.initMocks(this)
        spyViewModel = Mockito.spy(viewModel)
    }


    @Test
    fun testAPISuccessCase() {

        val repo = mock(APIRepository::class.java)

        val responseData = TestData().getData()

        val user: UseCaseResult<CityWeatherModel> = UseCaseResult.Success(responseData)


        runBlocking {

            Mockito.`when`(repo.searchWeather("Berlin")).thenReturn(
                user
            )
        }


        spyViewModel.getWeather("Berlin")


        when (user) {

            is UseCaseResult.Success -> {
                assertEquals("DE", user.data.sys.country)
            }

            is UseCaseResult.Error -> {
                assertNull(user.exception)
            }

        }


    }


    @Test
    fun testAPIFailureCase() {

        val repo = mock(APIRepository::class.java)

        val responseData = Throwable("HTTP 404 Not Found")

        val user: UseCaseResult<CityWeatherModel> = UseCaseResult.Error(responseData)


        runBlocking {

            Mockito.`when`(repo.searchWeather("Test City")).thenReturn(
                user
            )
        }


        spyViewModel.getWeather("Berlin")


        when (user) {

            is UseCaseResult.Success -> {
                assertNull(user.data)
            }

            is UseCaseResult.Error -> {
                assertEquals("HTTP 404 Not Found", user.exception.message)
            }

        }


    }


    @ExperimentalCoroutinesApi
    @Test
    fun testViewModelOnSuccess() {

        val responseData = TestData().getData()

        val user: UseCaseResult<CityWeatherModel> = UseCaseResult.Success(responseData)

        spyViewModel.resultHandler(user)

        assertNotNull(spyViewModel.weatherResponseLiveData.value)

        assertNull(spyViewModel.errorResponseLiveData.value)

    }


    @ExperimentalCoroutinesApi
    @Test
    fun testViewModelOnFailure() {

        Dispatchers.setMain(TestCoroutineDispatcher())

        val user: UseCaseResult<CityWeatherModel> = UseCaseResult.Error(Throwable(errorMessage))

        spyViewModel.resultHandler(user)

        assertNull(spyViewModel.weatherResponseLiveData.value)

        assertNotNull(spyViewModel.errorResponseLiveData.value)


    }


}