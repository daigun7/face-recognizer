package com.sgsoft.facerecognizer.ui

import com.sgsoft.facerecognizer.common.presenter.IPresenter
import com.sgsoft.facerecognizer.common.view.IView

interface MainContract {
    interface View : IView {

    }

    interface Presenter : IPresenter<View> {

    }
}