package com.sgsoft.facerecognizer.common.presenter

import com.sgsoft.facerecognizer.common.view.IView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class DisposablePresenter<V : IView> : IPresenter<V> {
    protected var mView: V? = null
    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun attachView(view: V) {
        mView = view
    }
    override fun detachView() {
        mView = null
        mCompositeDisposable.clear()
    }

    fun addDisposable(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }
}