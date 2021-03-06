package com.sgsoft.facerecognizer.ui.tab.celebrity

import android.app.Application
import com.sgsoft.facerecognizer.data.FaceDataSource
import com.sgsoft.facerecognizer.di.scope.FragmentScoped
import dagger.Module
import dagger.Provides

@Module
class CelebrityFaceTabModule {
    @FragmentScoped
    @Provides
    fun getCelebrityFacesUseCase(dataSource: FaceDataSource) = GetCelebrityFacesUseCase(dataSource)

    @FragmentScoped
    @Provides
    fun provideCelebrityFaceTabViewModelFactory(app: Application, useCase: GetCelebrityFacesUseCase)
            = CelebrityFaceTabViewModelFactory(app, useCase)
}