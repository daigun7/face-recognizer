package com.sgsoft.facerecognizer.data

import com.sgsoft.facerecognizer.model.FaceEntity
import io.reactivex.Single
import java.io.File

interface FaceDataSource {
    fun getFaces(file: File): Single<List<FaceEntity>>
    fun getCelebrityFaces(file: File): Single<List<FaceEntity>>
}