package com.sgsoft.facerecognizer.common.presenter

import com.sgsoft.facerecognizer.common.view.IView

abstract class AbstractPresenter<V : IView> : IPresenter<V> {
    protected var mView: V? = null

    override fun attachView(view: V) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }
}