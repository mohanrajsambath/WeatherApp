package com.ganesh.di.module


import com.ganesh.data.repo.APIRepository
import com.ganesh.weatherapp.BuildConfig
import com.tamil.galassignment.data.remote.APIInterface
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tamil.galassignment.data.repo.APIRepoInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkDIModule {


    @Provides
    @Singleton
    fun createHttpClient(): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(logging)
        client.readTimeout(5 * 60, TimeUnit.SECONDS)

        return client.addInterceptor {
            val original = it.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            val request = requestBuilder.method(original.method(), original.body()).build()
            return@addInterceptor it.proceed(request)
        }.build()

    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): APIInterface {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build().create(APIInterface::class.java)

    }

    @Singleton
    @Provides
    fun proviceRepo(interfce:APIInterface): APIRepoInterface {
        return APIRepository(interfce)
    }









}