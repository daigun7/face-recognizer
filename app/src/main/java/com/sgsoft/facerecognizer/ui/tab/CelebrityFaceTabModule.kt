package com.sgsoft.facerecognizer.ui.tab

import com.sgsoft.facerecognizer.data.FaceDataSource
import dagger.Module
import dagger.Provides

@Module
class CelebrityFaceTabModule {
    @Provides
    fun celebrityFaceTabPresenter(dataSource: FaceDataSource): CelebrityFaceTabContract.Presenter
            = CelebrityFaceTabPresenter(dataSource)
}