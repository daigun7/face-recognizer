package com.sgsoft.facerecognizer.common.presenter

import com.sgsoft.facerecognizer.common.view.IView

interface IPresenter<V : IView> {
    fun attachView(view: V)
    fun detachView()
}