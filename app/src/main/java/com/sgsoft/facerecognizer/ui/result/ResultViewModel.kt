package com.sgsoft.facerecognizer.ui.result

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sgsoft.facerecognizer.api.Face
import com.sgsoft.facerecognizer.common.util.SingleLiveEvent

class ResultViewModel(private val app: Application) : AndroidViewModel(app) {
    private val _imagePath = MutableLiveData<String>()
    private val _items = MutableLiveData<List<Face>>()
    private val _clickClose = SingleLiveEvent<Unit>()

    val imagePath: LiveData<String> get() = _imagePath
    val items: LiveData<List<Face>> get() = _items
    val clickClose: LiveData<Unit> get() = _clickClose

    fun setImage(path: String) {
        _imagePath.value = path
    }

    fun setItems(items: List<Face>) {
        _items.value = items
    }

    fun onClickClose() {
        _clickClose.call()
    }
}