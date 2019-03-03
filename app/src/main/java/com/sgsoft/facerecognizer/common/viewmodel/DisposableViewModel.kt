package com.sgsoft.facerecognizer.common.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class DisposableViewModel : ViewModel() {
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