package com.sgsoft.facerecognizer.ui.tab.face

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sgsoft.facerecognizer.data.ImageProviderImpl
import com.sgsoft.facerecognizer.data.ResourcesProviderImpl

class FaceTabViewModelFactory(private val app: Application, private val useCase: GetFacesUseCase)
    : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FaceTabViewModel(ResourcesProviderImpl(app), ImageProviderImpl(app), useCase) as T
    }
}