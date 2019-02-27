package com.sgsoft.facerecognizer.ui.tab

import com.sgsoft.facerecognizer.data.FaceDataSource
import dagger.Module
import dagger.Provides

@Module
class FaceTabModule {
    @Provides
    fun faceTabPresenter(dataSource: FaceDataSource): FaceTabContract.Presenter = FaceTabPresenter(dataSource)
}