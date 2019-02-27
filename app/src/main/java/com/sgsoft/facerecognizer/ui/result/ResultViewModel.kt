package com.sgsoft.facerecognizer.ui.result

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sgsoft.facerecognizer.common.viewmodel.SingleLiveEvent
import com.sgsoft.facerecognizer.model.FaceEntity

class ResultViewModel(app: Application) : AndroidViewModel(app) {
    private val _image = MutableLiveData<String>()
    private val _items = MutableLiveData<List<FaceEntity>>()
    private val _clickClose = SingleLiveEvent<Unit>()

    val image: LiveData<String> get() = _image
    val items: LiveData<List<FaceEntity>> get() = _items
    val clickClose: LiveData<Unit> get() = _clickClose

    fun setImage(path: String) {
        _image.value = path
    }

    fun setItems(items: List<FaceEntity>) {
        _items.value = items
    }

    fun onClickClose() {
        _clickClose.call()
    }
}