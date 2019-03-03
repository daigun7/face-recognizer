package com.sgsoft.facerecognizer.ui.tab.celebrity

import android.app.Application
import com.sgsoft.facerecognizer.data.FaceDataSource
import dagger.Module
import dagger.Provides

@Module
class CelebrityFaceTabModule {
    @Provides
    fun getCelebrityFacesUseCase(dataSource: FaceDataSource) = GetCelebrityFacesUseCase(dataSource)

    @Provides
    fun provideCelebrityFaceTabViewModelFactory(app: Application, useCase: GetCelebrityFacesUseCase)
            = CelebrityFaceTabViewModelFactory(app, useCase)
}