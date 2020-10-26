package com.sgsoft.facerecognizer.di.component

import com.sgsoft.facerecognizer.DataSourceTest
import com.sgsoft.facerecognizer.data.FaceDataSource
import com.sgsoft.facerecognizer.di.module.DataModule
import com.sgsoft.facerecognizer.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    DataModule::class
])
interface TestComponent {
    fun dataSource(): FaceDataSource
    fun inject(test: DataSourceTest)

    @Component.Builder
    interface Builder {
        fun networkModule(module: NetworkModule): Builder
        fun build(): TestComponent
    }
}