package com.sgsoft.facerecognizer.ui.tab.face

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FaceTabViewModelFactory(private val app: Application, private val useCase: GetFacesUseCase)
    : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FaceTabViewModel(app, useCase) as T
    }
}