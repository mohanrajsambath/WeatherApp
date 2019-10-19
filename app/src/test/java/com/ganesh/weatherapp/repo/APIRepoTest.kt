package com.ganesh.weatherapp.repo


import com.ganesh.data.model.UseCaseResult
import com.ganesh.data.repo.APIRepository
import com.ganesh.weatherapp.BuildConfig
import com.ganesh.weatherapp.util.TestData
import com.tamil.galassignment.data.remote.APIInterface
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class APIRepoTest : BaseTest() {


    private val mockAPI = Mockito.mock(APIInterface::class.java)

    @InjectMocks
    var rateService = APIRepository(mockAPI)

    @Before
    fun initAll() {
        MockitoAnnotations.initMocks(this)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun successCase() {

        val responseData = TestData().getData()

        runBlocking {

            Mockito.`when`(

                mockAPI.getWeatherAsync(
                    cityName,
                    BuildConfig.API_KEY
                )

            ).thenReturn(async {

                responseData

            })


            val result = rateService.searchWeather(cityName)

            assertEquals(true, result is UseCaseResult.Success)

            assertEquals(false, result is UseCaseResult.Error)


        }


    }


    @ExperimentalCoroutinesApi
    @Test
    fun failureCase() {

        val ex = Throwable(errorMessage)

        runBlocking {

            Mockito.`when`(

                mockAPI.getWeatherAsync(
                    cityName,
                    BuildConfig.API_KEY
                )

            ).then {

                (async {
                    ex
                })

            }


            val result = rateService.searchWeather(cityName)

            assert(result is UseCaseResult.Error)
            assertEquals(false, result is UseCaseResult.Success)


        }
    }


}