package com.sgsoft.facerecognizer.ui.tab.celebrity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CelebrityFaceTabViewModelFactory(private val app: Application, private val useCase: GetCelebrityFacesUseCase)
    : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CelebrityFaceTabViewModel(app, useCase) as T
    }
}