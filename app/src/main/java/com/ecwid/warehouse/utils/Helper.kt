package com.ecwid.warehouse.utils

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun getByteArrayFromImage(bitmap: Bitmap): ByteArray? {
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    return baos.toByteArray()
}