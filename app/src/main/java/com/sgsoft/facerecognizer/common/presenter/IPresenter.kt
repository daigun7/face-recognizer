package com.sgsoft.facerecognizer.common.presenter

import com.sgsoft.facerecognizer.common.view.IView

interface IPresenter<in V : IView> {
    fun attachView(view: V)
    fun detachView()
}