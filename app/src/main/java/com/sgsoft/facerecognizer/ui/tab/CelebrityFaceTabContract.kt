package com.sgsoft.facerecognizer.ui.tab

import android.content.Context
import com.sgsoft.facerecognizer.common.presenter.IPresenter
import com.sgsoft.facerecognizer.common.view.IView
import com.sgsoft.facerecognizer.model.FaceEntity
import java.io.File

interface CelebrityFaceTabContract {
    interface View : IView {
        fun onFaceRecognized(faces: List<FaceEntity>, file: File)
    }

    interface Presenter : IPresenter<View> {
        fun recognizeCelebrityFace(context: Context, file: File)
    }
}