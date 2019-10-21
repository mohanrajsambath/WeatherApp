package com.ganesh.weatherapp.di.module


import com.ganesh.weatherapp.BuildConfig
import com.ganesh.weatherapp.data.remote.APIInterface
import com.google.gson.GsonBuilder

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

    lateinit var inter : APIInterface
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

        inter =  Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            //.addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build().create(APIInterface::class.java)

        return inter

    }

//    @Singleton
//    @Provides
//    fun provideApiHelper(): AppApiHelper {
//        return AppApiHelper()
//    }





}