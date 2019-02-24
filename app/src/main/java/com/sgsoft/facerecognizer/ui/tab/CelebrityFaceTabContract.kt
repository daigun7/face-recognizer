package com.sgsoft.facerecognizer.ui.tab

import android.content.Context
import com.sgsoft.facerecognizer.api.CFRModel
import com.sgsoft.facerecognizer.common.presenter.IPresenter
import com.sgsoft.facerecognizer.common.view.IView
import java.io.File

interface CelebrityFaceTabContract {
    interface View : IView {
        fun onFaceRecognized(model: CFRModel, file: File)
    }

    interface Presenter : IPresenter<View> {
        fun recognizeCelebrityFace(context: Context, file: File)
    }
}