package com.sgsoft.facerecognizer.ui.tab

import android.content.Context
import com.sgsoft.facerecognizer.common.presenter.IPresenter
import com.sgsoft.facerecognizer.common.view.IView
import com.sgsoft.facerecognizer.model.FaceEntity
import java.io.File

interface FaceTabContract {
    interface View : IView {
        fun onFaceRecognized(faces: List<FaceEntity>, file: File)
    }

    interface Presenter : IPresenter<View> {
        fun recognizeFace(context: Context, file: File)
    }
}