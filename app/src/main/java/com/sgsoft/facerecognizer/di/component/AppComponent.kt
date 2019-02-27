package com.sgsoft.facerecognizer.di.component

import android.app.Application
import com.sgsoft.facerecognizer.BaseApplication
import com.sgsoft.facerecognizer.di.module.ActivityBindingModule
import com.sgsoft.facerecognizer.di.module.ApplicationModule
import com.sgsoft.facerecognizer.di.module.DataModule
import com.sgsoft.facerecognizer.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    NetworkModule::class,
    DataModule::class,
    ActivityBindingModule::class
])
interface AppComponent : AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun networkModule(module: NetworkModule): Builder
        fun build(): AppComponent
    }
}