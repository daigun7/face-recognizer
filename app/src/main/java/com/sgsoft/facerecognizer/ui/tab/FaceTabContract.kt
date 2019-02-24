package com.sgsoft.facerecognizer.ui.tab

import android.content.Context
import com.sgsoft.facerecognizer.api.CFRModel
import com.sgsoft.facerecognizer.common.presenter.IPresenter
import com.sgsoft.facerecognizer.common.view.IView
import java.io.File

interface FaceTabContract {
    interface View : IView {
        fun onFaceRecognized(model: CFRModel, file: File)
    }

    interface Presenter : IPresenter<View> {
        fun recognizeFace(context: Context, file: File)
    }
}