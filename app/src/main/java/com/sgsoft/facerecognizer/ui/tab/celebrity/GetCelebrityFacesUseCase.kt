package com.sgsoft.facerecognizer.ui.tab.celebrity

import com.sgsoft.facerecognizer.common.usecase.BaseUseCase
import com.sgsoft.facerecognizer.data.FaceDataSource
import com.sgsoft.facerecognizer.model.FaceEntity
import io.reactivex.Single
import java.io.File

class GetCelebrityFacesUseCase(private val dataSource: FaceDataSource) : BaseUseCase<File, Single<List<FaceEntity>>> {

    override fun execute(param: File): Single<List<FaceEntity>> {
        return dataSource.getCelebrityFaces(param)
    }
}