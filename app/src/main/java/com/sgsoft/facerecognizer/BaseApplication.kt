package com.sgsoft.facerecognizer

import android.app.Activity
import android.app.Application
import com.sgsoft.facerecognizer.di.component.DaggerAppComponent
import com.sgsoft.facerecognizer.di.module.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class BaseApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        inject()
    }

    fun inject() {
        DaggerAppComponent.builder()
                .application(this)
                .networkModule(NetworkModule(Constants.CFR_API_HOST))
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}