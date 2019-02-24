package com.sgsoft.facerecognizer.di.module

import com.sgsoft.facerecognizer.di.scope.ActivityScoped
import com.sgsoft.facerecognizer.ui.MainActivity
import com.sgsoft.facerecognizer.ui.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity() : MainActivity
}