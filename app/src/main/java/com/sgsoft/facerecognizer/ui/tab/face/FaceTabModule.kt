package com.sgsoft.facerecognizer.ui.tab.face

import android.app.Application
import com.sgsoft.facerecognizer.data.FaceDataSource
import com.sgsoft.facerecognizer.di.scope.FragmentScoped
import dagger.Module
import dagger.Provides

@Module
class FaceTabModule {
    @FragmentScoped
    @Provides
    fun getFacesUseCase(dataSource: FaceDataSource) = GetFacesUseCase(dataSource)

    @FragmentScoped
    @Provides
    fun provideFaceTabViewModelFactory(app: Application, useCase: GetFacesUseCase)
            = FaceTabViewModelFactory(app, useCase)
}