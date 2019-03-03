package com.sgsoft.facerecognizer.extension

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.IOException

@Throws(IOException::class)
fun File.createTempFile(): File {
    return File.createTempFile(
            "temp_",
            null,
            this
    ).apply { deleteOnExit() }
}

fun Uri.getRealPath(resolver: ContentResolver): String? {
    return resolver.query(
            this,
            arrayOf(MediaStore.Images.Media.DATA),
            null,
            null,
            null
    )?.use { cursor ->
        cursor.moveToFirst()

        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA).let { cursor.getString(it) }
    }
}