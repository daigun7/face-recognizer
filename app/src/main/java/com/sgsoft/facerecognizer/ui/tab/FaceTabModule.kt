package com.sgsoft.facerecognizer.ui.tab

import dagger.Module
import dagger.Provides

@Module
class FaceTabModule {
    @Provides
    fun faceTabPresenter() : FaceTabContract.Presenter = FaceTabPresenter()
}