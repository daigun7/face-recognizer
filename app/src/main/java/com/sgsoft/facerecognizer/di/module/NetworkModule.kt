package com.sgsoft.facerecognizer.di.module

import com.sgsoft.facerecognizer.BuildConfig
import com.sgsoft.facerecognizer.api.CFRApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(private val baseUrl: String) {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val clientBuilder = OkHttpClient.Builder()

        if(BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }

        return Retrofit.Builder()
                .client(clientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): CFRApi = retrofit.create(CFRApi::class.java)
}