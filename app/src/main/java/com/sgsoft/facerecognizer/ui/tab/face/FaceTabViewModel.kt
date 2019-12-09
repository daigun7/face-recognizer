package com.sgsoft.facerecognizer.ui.tab.face

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sgsoft.facerecognizer.common.util.SchedulerProvider
import com.sgsoft.facerecognizer.common.viewmodel.DisposableViewModel
import com.sgsoft.facerecognizer.common.viewmodel.SingleLiveEvent
import com.sgsoft.facerecognizer.data.ImageProvider
import com.sgsoft.facerecognizer.data.ResourcesProvider
import com.sgsoft.facerecognizer.model.EmotionType
import com.sgsoft.facerecognizer.model.FaceEntity
import com.sgsoft.facerecognizer.model.GenderType
import com.sgsoft.facerecognizer.model.PoseType
import java.io.File

class FaceTabViewModel(private val resourcesProvider: ResourcesProvider,
                       private val imageProvider: ImageProvider,
                       private val useCase: GetFacesUseCase) : DisposableViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    private val _faceList = MutableLiveData<List<FaceEntity>>()
    private val _error = MutableLiveData<String>()
    private val _clickCamera = SingleLiveEvent<Unit>()
    private val _clickAlbum = SingleLiveEvent<Unit>()

    val loading: LiveData<Boolean> get() = _loading
    val faceList: LiveData<List<FaceEntity>> get() = _faceList
    val error: LiveData<String> get() = _error
    val clickCamera: LiveData<Unit> get() = _clickCamera
    val clickAlbum: LiveData<Unit> get() = _clickAlbum

    fun getFaces(file: File) {
        addDisposable(imageProvider.resize(file)
                .flatMap {
                    useCase.execute(it)
                }
                .map { faces ->
                    faces.forEach { face ->
                        face.gender?.apply {
                            GenderType.fromString(value)?.getTextResId()?.let {
                                value = resourcesProvider.getString(it)
                            }
                        }

                        face.emotion?.apply {
                            EmotionType.fromString(value)?.getTextResId()?.let {
                                value = resourcesProvider.getString(it)
                            }
                        }

                        face.pose?.apply {
                            PoseType.fromString(value)?.getTextResId()?.let {
                                value = resourcesProvider.getString(it)
                            }
                        }
                    }
                    faces
                }
                .doOnSubscribe {
                    _loading.postValue(true)
                }
                .doAfterTerminate {
                    _loading.postValue(false)
                }
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .subscribe({
                    _faceList.postValue(it)

                }, {
                    it.printStackTrace()
                    _error.postValue(it.message)
                }))
    }

    fun clickCamera() {
        _clickCamera.call()
    }

    fun clickAlbum() {
        _clickAlbum.call()
    }
}