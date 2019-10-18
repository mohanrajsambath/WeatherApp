package com.ganesh.weatherapp

import com.ganesh.data.model.UseCaseResult
import com.ganesh.data.repo.APIRepository
import com.ganesh.weatherapp.view_model.WeatherViewModel
import com.tamil.galassignment.data.model.CityWeatherModel
import com.tamil.galassignment.data.remote.APIInterface
import com.tamil.galassignment.data.repo.APIRepoInterface
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class WeatherViewModelTest : BaseTest() {

    private lateinit var spyViewModel: WeatherViewModel

    @Before
    fun initAll() {
        MockitoAnnotations.initMocks(this)
        spyViewModel = Mockito.spy(viewModel)
    }



    @Mock
    val mockAPI :APIInterface  = Mockito.mock(APIInterface::class.java)

    @InjectMocks
    var apiRepository: APIRepoInterface = APIRepository(mockAPI)

    @InjectMocks
    val viewModel = WeatherViewModel(apiRepository)


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