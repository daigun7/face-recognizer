package com.sgsoft.facerecognizer.data

import android.content.Context
import android.graphics.Bitmap
import android.media.ExifInterface
import android.os.Environment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.sgsoft.facerecognizer.common.util.RotateTransformation
import io.reactivex.Single
import java.io.File
import java.io.FileOutputStream
import java.util.*

interface ImageProvider {
    fun resize(file: File, width: Int = 600, height: Int = 600): Single<File>
}

class ImageProviderImpl(private val context: Context) : ImageProvider {

    override fun resize(file: File, width: Int, height: Int): Single<File> {
        val orientation = ExifInterface(file.absolutePath)
                .getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        val options = RequestOptions().override(width, height)
                .transforms(RotateTransformation(orientation))
                .downsample(DownsampleStrategy.DEFAULT)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)

        val future = Glide.with(context.applicationContext)
                .asBitmap()
                .load(file)
                .apply(options)
                .submit()

        return Single.fromFuture(future)
                .flatMap {
                    Single.create<File> { emitter ->
                        val storageDir = context.applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                        val fileName = "${Date().time}.jpg"
                        val dstFile = File(storageDir, fileName)
                        val out = FileOutputStream(dstFile)

                        it.compress(Bitmap.CompressFormat.JPEG, 90, out)
                        out.flush()
                        out.close()

                        emitter.onSuccess(dstFile)
                    }
                }
                .doAfterTerminate {
                    Glide.with(context.applicationContext).clear(future)
                }
    }
}