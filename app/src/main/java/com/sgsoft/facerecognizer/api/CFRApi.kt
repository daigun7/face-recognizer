package com.sgsoft.facerecognizer.network.api

import com.sgsoft.facerecognizer.api.CFRData
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CFRApi {
    @Multipart
    @POST("/v1/vision/celebrity")
    fun celebrity(@Header("X-Naver-Client-Id") clientId: String,
                  @Header("X-Naver-Client-Secret") clientSecret: String,
                  @Part file: MultipartBody.Part): Single<CFRData>

    @Multipart
    @POST("/v1/vision/face")
    fun face(@Header("X-Naver-Client-Id") clientId: String,
             @Header("X-Naver-Client-Secret") clientSecret: String,
             @Part file: MultipartBody.Part): Single<CFRData>
}