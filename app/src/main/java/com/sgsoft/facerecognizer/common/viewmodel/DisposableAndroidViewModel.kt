package com.sgsoft.facerecognizer.common.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class DisposableAndroidViewModel(app: Application) : AndroidViewModel(app) {
    private val mCompositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        if(mCompositeDisposable.isDisposed) {
            return
        }

        mCompositeDisposable.add(disposable)
    }

    override fun onCleared() {
        mCompositeDisposable.clear()
        super.onCleared()
    }
}