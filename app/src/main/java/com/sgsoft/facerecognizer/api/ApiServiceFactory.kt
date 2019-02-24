package com.sgsoft.facerecognizer.network.api

import com.sgsoft.facerecognizer.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiServiceFactory {
    private var mOkHttpBuilder = OkHttpClient.Builder()
        /*
        .addInterceptor {
            it.proceed(it.request().newBuilder()
                .addHeader("", "")
                .build())
        }
        */
        .apply {
            if(BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                    .apply { level =  HttpLoggingInterceptor.Level.BODY }

                addNetworkInterceptor(interceptor)
            }
        }

    private var mRetrofitBuilder = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    private var mBaseUrl: String? = null

    fun setBaseUrl(url: String) : ApiServiceFactory {
        mBaseUrl = url
        return this
    }

    fun setConnectTimeout(timeout: Long, timeUnit: TimeUnit) : ApiServiceFactory {
        mOkHttpBuilder.connectTimeout(timeout, timeUnit)
        return this
    }

    fun setReadTimeout(timeout: Long, timeUnit: TimeUnit) : ApiServiceFactory {
        mOkHttpBuilder.readTimeout(timeout, timeUnit)
        return this
    }

    fun setWriteTimeout(timeout: Long, timeUnit: TimeUnit) : ApiServiceFactory {
        mOkHttpBuilder.writeTimeout(timeout, timeUnit)
        return this
    }

    fun setTimeout(timeout: Long, timeUnit: TimeUnit) : ApiServiceFactory {
        mOkHttpBuilder.connectTimeout(timeout, timeUnit)
        mOkHttpBuilder.readTimeout(timeout, timeUnit)
        mOkHttpBuilder.writeTimeout(timeout, timeUnit)

        return this
    }

    fun <T> createService(service: Class<T>) : T {
        return mRetrofitBuilder
            .baseUrl(mBaseUrl!!)
            .client(mOkHttpBuilder.build())
            .build()
            .create(service)
    }
}