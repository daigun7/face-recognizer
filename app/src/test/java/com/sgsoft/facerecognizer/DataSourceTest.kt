package com.sgsoft.facerecognizer

import com.sgsoft.facerecognizer.data.FaceDataSource
import com.sgsoft.facerecognizer.di.component.DaggerTestComponent
import com.sgsoft.facerecognizer.di.module.NetworkModule
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.File
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
class DataSourceTest {
    @Inject
    lateinit var dataSource: FaceDataSource

    @Before
    fun setup() {
        DaggerTestComponent.builder()
                .networkModule(NetworkModule(Constants.CFR_API_HOST))
                .build()
                .inject(this)
    }

    @Test
    fun test_getFace() {
        val uri = javaClass.getResource("iu.jpg")?.toURI()
        val file = File(uri)
        val observer = dataSource.getFaces(file).test()

        observer.assertSubscribed()
        observer.awaitTerminalEvent()
        observer.assertNoErrors()
        observer.assertNoTimeout()
        observer.assertValueCount(1)
    }

    @Test
    fun test_getCelebrityFaces() {
        val uri = javaClass.getResource("iu.jpg")?.toURI()
        val file = File(uri)
        val observer = dataSource.getCelebrityFaces(file).test()

        observer.assertSubscribed()
        observer.awaitTerminalEvent()
        observer.assertNoErrors()
        observer.assertNoTimeout()
        observer.assertValueCount(1)
    }
}