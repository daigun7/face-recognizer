package com.sgsoft.facerecognizer.common.presenter

import com.sgsoft.facerecognizer.common.view.IView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class DisposablePresenter<V : IView> : AbstractPresenter<V>() {
    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun detachView() {
        mCompositeDisposable.clear()
    }

    fun addDisposable(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }
}