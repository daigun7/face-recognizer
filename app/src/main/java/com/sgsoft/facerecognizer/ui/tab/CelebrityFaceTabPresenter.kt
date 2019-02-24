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
import com.sgsoft.facerecognizer.network.api.ApiServiceFactory
import com.sgsoft.facerecognizer.network.api.CFRApi
import com.sgsoft.facerecognizer.network.api.Constants
import io.reactivex.Flowable
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.concurrent.TimeUnit

class CelebrityFaceTabPresenter : DisposablePresenter<CelebrityFaceTabContract.View>(),
        CelebrityFaceTabContract.Presenter {

    companion object {
        const val TIMEOUT_CFR = 15_000L
    }

    override fun recognizeCelebrityFace(context: Context, file: File) {
        var orientation = ExifInterface(file.absolutePath).getAttributeInt(
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
                        .flatMap {
                            ApiServiceFactory.setBaseUrl(Constants.CFR_API_HOST)
                                    .setTimeout(TIMEOUT_CFR, TimeUnit.MILLISECONDS)
                                    .createService(CFRApi::class.java)
                                    .celebrity(Constants.CLIENT_ID, Constants.CLIENT_SECRET, createMultipartBody(it))
                                    .subscribeOn(SchedulerProvider.io())
                                    .observeOn(SchedulerProvider.ui())
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

    private fun createMultipartBody(file: File) : MultipartBody.Part {
        return RequestBody.create(MediaType.parse("image/*"), file).let {
            MultipartBody.Part.createFormData("image", file.name, it)
        }
    }
}