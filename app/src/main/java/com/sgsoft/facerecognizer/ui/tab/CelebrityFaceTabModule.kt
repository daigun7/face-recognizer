package com.sgsoft.facerecognizer.ui.tab

import dagger.Module
import dagger.Provides

@Module
class CelebrityFaceTabModule {
    @Provides
    fun celebrityFaceTabPresenter() : CelebrityFaceTabContract.Presenter = CelebrityFaceTabPresenter()
}