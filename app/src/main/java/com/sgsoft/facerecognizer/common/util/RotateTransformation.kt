package com.sgsoft.facerecognizer.common.util

import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.bumptech.glide.util.Util
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.util.*

class RotateTransformation(private val exifOrientation: Int) : BitmapTransformation() {
    companion object {
        val ID: String = UUID.randomUUID().toString()
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return TransformationUtils.rotateImageExif(
                pool, toTransform, TransformationUtils.getExifOrientationDegrees(exifOrientation))
    }

    override fun equals(other: Any?): Boolean {
        return other is RotateTransformation && exifOrientation == other.exifOrientation
    }

    override fun hashCode(): Int {
        return Util.hashCode(ID.hashCode(), Util.hashCode(exifOrientation))
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID.toByteArray())

        ByteBuffer.allocate(4)
                .putInt(exifOrientation)
                .array()
                .also { messageDigest.update(it) }
    }
}