package com.sgsoft.facerecognizer.ui.tab.face

import android.app.Application
import com.sgsoft.facerecognizer.data.FaceDataSource
import dagger.Module
import dagger.Provides

@Module
class FaceTabModule {
    @Provides
    fun getFacesUseCase(dataSource: FaceDataSource) = GetFacesUseCase(dataSource)

    @Provides
    fun provideFaceTabViewModelFactory(app: Application, useCase: GetFacesUseCase)
            = FaceTabViewModelFactory(app, useCase)
}