package com.sgsoft.facerecognizer.ui.tab.celebrity

import android.app.Application
import android.graphics.Bitmap
import android.media.ExifInterface
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.RequestOptions
import com.sgsoft.facerecognizer.common.util.RotateTransformation
import com.sgsoft.facerecognizer.common.util.SchedulerProvider
import com.sgsoft.facerecognizer.common.viewmodel.DisposableAndroidViewModel
import com.sgsoft.facerecognizer.common.viewmodel.SingleLiveEvent
import com.sgsoft.facerecognizer.model.EmotionType
import com.sgsoft.facerecognizer.model.FaceEntity
import com.sgsoft.facerecognizer.model.GenderType
import com.sgsoft.facerecognizer.model.PoseType
import io.reactivex.Flowable
import io.reactivex.Single
import java.io.File
import java.io.FileOutputStream
import java.util.*

class CelebrityFaceTabViewModel(private val app: Application, private val useCase: GetCelebrityFacesUseCase)
    : DisposableAndroidViewModel(app) {

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

    private fun getBitmapFromFile(file: File): FutureTarget<Bitmap> {
        val orientation = ExifInterface(file.absolutePath).getAttributeInt(
                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        val options = RequestOptions()
                .override(600, 600)
                .transforms(RotateTransformation(orientation))
                .downsample(DownsampleStrategy.DEFAULT)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)

        return Glide.with(app)
                .asBitmap()
                .load(file)
                .apply(options)
                .submit()
    }

    private fun getFileFromBitmap(bitmap: Bitmap): Single<File> {
        return Single.create<File> { emitter ->
            val storageDir = app.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val fileName = "${Date().time}.jpg"
            val file = File(storageDir, fileName)
            val out = FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()

            emitter.onSuccess(file)
        }
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
    }

    fun getCelebrityFaces(file: File) {
        val future = getBitmapFromFile(file)

        addDisposable(
                Flowable.fromFuture(future)
                        .subscribeOn(SchedulerProvider.io())
                        .observeOn(SchedulerProvider.ui())
                        .flatMapSingle {
                            getFileFromBitmap(it)
                        }
                        .flatMapSingle {
                            useCase.execute(it)
                                    .subscribeOn(SchedulerProvider.io())
                                    .observeOn(SchedulerProvider.ui())
                        }
                        .map { faces ->
                            faces.forEach { face ->
                                face.gender?.apply {
                                    GenderType.fromString(value)?.getTextResId()?.let {
                                        value = app.resources.getString(it)
                                    }
                                }

                                face.emotion?.apply {
                                    EmotionType.fromString(value)?.getTextResId()?.let {
                                        value = app.resources.getString(it)
                                    }
                                }

                                face.pose?.apply {
                                    PoseType.fromString(value)?.getTextResId()?.let {
                                        value = app.resources.getString(it)
                                    }
                                }
                            }
                            faces
                        }
                        .doOnSubscribe {
                            _loading.postValue(true)
                        }
                        .doOnTerminate {
                            _loading.postValue(false)
                            Glide.with(app).clear(future)
                        }
                        .subscribe({
                            _faceList.postValue(it)

                        }, {
                            it.printStackTrace()
                            _error.postValue(it.message)
                        })
        )
    }

    fun clickCamera() {
        _clickCamera.call()
    }

    fun clickAlbum() {
        _clickAlbum.call()
    }
}