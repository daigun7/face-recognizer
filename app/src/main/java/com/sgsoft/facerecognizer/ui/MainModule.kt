package com.sgsoft.facerecognizer.ui

import com.sgsoft.facerecognizer.di.scope.ActivityScoped
import com.sgsoft.facerecognizer.di.scope.FragmentScoped
import com.sgsoft.facerecognizer.ui.tab.celebrity.CelebrityFaceTabFragment
import com.sgsoft.facerecognizer.ui.tab.celebrity.CelebrityFaceTabModule
import com.sgsoft.facerecognizer.ui.tab.face.FaceTabFragment
import com.sgsoft.facerecognizer.ui.tab.face.FaceTabModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {
    @ActivityScoped
    @Binds
    abstract fun mainPresenter(presenter: MainPresenter): MainContract.Presenter

    @FragmentScoped
    @ContributesAndroidInjector(modules = [FaceTabModule::class])
    abstract fun faceTabFragment(): FaceTabFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [CelebrityFaceTabModule::class])
    abstract fun celebrityFaceTabFragment(): CelebrityFaceTabFragment
}