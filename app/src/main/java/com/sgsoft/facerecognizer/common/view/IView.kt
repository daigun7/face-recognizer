package com.sgsoft.facerecognizer.common.view

import android.content.Context
import androidx.annotation.StringRes

interface IView {
    fun getContext(): Context
    fun showProgress()
    fun hideProgress()
    fun showMessage(message: String)
    fun showMessage(@StringRes resId: Int)
}