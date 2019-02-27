package com.sgsoft.facerecognizer.data

import com.sgsoft.facerecognizer.Constants
import com.sgsoft.facerecognizer.common.util.SchedulerProvider
import com.sgsoft.facerecognizer.model.*
import com.sgsoft.facerecognizer.network.api.CFRApi
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class FaceRepository(val api: CFRApi) : FaceDataSource {

    override fun getFaces(file: File): Single<List<FaceEntity>> {
        return api.face(Constants.CLIENT_ID, Constants.CLIENT_SECRET, createMultipartBody(file))
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .map { data ->
                    data.faces.map { face ->
                        val gender = face.gender?.let {
                            Gender(it.value, (it.confidence * 100f).toInt())
                        }

                        val age = face.age?.let {
                            Age(it.value, (it.confidence * 100f).toInt())
                        }

                        val emotion = face.emotion?.let {
                            Emotion(it.value, (it.confidence * 100f).toInt())
                        }

                        val pose = face.pose?.let {
                            Pose(it.value, (it.confidence * 100f).toInt())
                        }

                        val celebrity = face.celebrity?.let {
                            Celebrity(it.value, (it.confidence * 100f).toInt())
                        }

                        FaceEntity(gender, age, emotion, pose, celebrity)
                    }
                }
    }

    override fun getCelebrityFaces(file: File): Single<List<FaceEntity>> {
        return api.celebrity(Constants.CLIENT_ID, Constants.CLIENT_SECRET, createMultipartBody(file))
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .map { data ->
                    data.faces.map { face ->
                        val celebrity = face.celebrity?.let {
                            Celebrity(it.value, (it.confidence * 100f).toInt())
                        }

                        FaceEntity(null, null, null, null, celebrity)
                    }
                }
    }

    private fun createMultipartBody(file: File): MultipartBody.Part {
        return RequestBody.create(MediaType.parse("image/*"), file).let {
            MultipartBody.Part.createFormData("image", file.name, it)
        }
    }
}