package com.sgsoft.facerecognizer.ui.tab

import android.content.Context
import android.graphics.Bitmap
import android.media.ExifInterface
import android.os.Environment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.sgsoft.facerecognizer.common.presenter.DisposablePresenter
import com.sgsoft.facerecognizer.common.util.RotateTransformation
import com.sgsoft.facerecognizer.common.util.SchedulerProvider
import com.sgsoft.facerecognizer.data.FaceDataSource
import com.sgsoft.facerecognizer.model.EmotionType
import com.sgsoft.facerecognizer.model.GenderType
import com.sgsoft.facerecognizer.model.PoseType
import io.reactivex.Flowable
import io.reactivex.Single
import java.io.File
import java.io.FileOutputStream
import java.util.*

class FaceTabPresenter(private val dataSource: FaceDataSource)
    : DisposablePresenter<FaceTabContract.View>(), FaceTabContract.Presenter {

    override fun recognizeFace(context: Context, file: File) {
        val orientation = ExifInterface(file.absolutePath).getAttributeInt(
                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        val options = RequestOptions()
                .override(600, 600)
                .transforms(RotateTransformation(orientation))
                .downsample(DownsampleStrategy.DEFAULT)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)

        val future = Glide.with(context)
                .asBitmap()
                .load(file)
                .apply(options)
                .submit()

        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val fileName = "${Date().time}.jpg"
        val newFile = File(storageDir, fileName)

        addDisposable(
                Flowable.fromFuture(future)
                        .subscribeOn(SchedulerProvider.io())
                        .observeOn(SchedulerProvider.ui())
                        .flatMapSingle {
                            Single.create<File> { emitter ->
                                val out = FileOutputStream(newFile)
                                it.compress(Bitmap.CompressFormat.JPEG, 90, out)

                                out.flush()
                                out.close()

                                emitter.onSuccess(newFile)
                            }
                                    .subscribeOn(SchedulerProvider.io())
                                    .observeOn(SchedulerProvider.ui())
                        }
                        .flatMapSingle {
                            dataSource.getFaces(it)
                                    .subscribeOn(SchedulerProvider.io())
                                    .observeOn(SchedulerProvider.ui())
                        }
                        .map { faces ->
                            faces.forEach { face ->
                                face.gender?.apply {
                                    GenderType.fromString(value)?.getTextResId()?.let {
                                        value = context.resources.getString(it)
                                    }
                                }

                                face.emotion?.apply {
                                    EmotionType.fromString(value)?.getTextResId()?.let {
                                        value = context.resources.getString(it)
                                    }

                                }

                                face.pose?.apply {
                                    PoseType.fromString(value)?.getTextResId()?.let {
                                        value = context.resources.getString(it)
                                    }

                                }
                            }
                            faces
                        }
                        .doOnSubscribe {
                            mView?.showProgress()
                        }
                        .doOnTerminate {
                            Glide.with(context).clear(future)
                            mView?.hideProgress()
                        }
                        .subscribe({
                            mView?.onFaceRecognized(it, newFile)
                        }, {
                            it.printStackTrace()
                        })
        )
    }
}