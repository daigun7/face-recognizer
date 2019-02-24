package com.sgsoft.facerecognizer.common.presenter

import com.sgsoft.facerecognizer.common.view.IView

abstract class AbstractPresenter<V : IView> : IPresenter<V> {
    override fun attachView(view: V) {

    }

    override fun detachView() {

    }
}