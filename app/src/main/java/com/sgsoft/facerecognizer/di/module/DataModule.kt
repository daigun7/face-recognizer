package com.sgsoft.facerecognizer.di.module

import com.sgsoft.facerecognizer.api.CFRApi
import com.sgsoft.facerecognizer.data.FaceDataSource
import com.sgsoft.facerecognizer.data.FaceRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun provideFaceDataSource(api: CFRApi): FaceDataSource {
        return FaceRepository(api)
    }
}