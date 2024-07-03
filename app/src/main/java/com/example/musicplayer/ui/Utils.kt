package com.example.musicplayer.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, stream)
    val byteArray: ByteArray = stream.toByteArray()
    stream.close()
    return byteArray
}

@SuppressLint("DefaultLocale")
fun formatMillis(millis: Long): String {
    val duration = millis.toDuration(DurationUnit.MILLISECONDS)
    return (if(duration.inWholeHours > 0)
                "${duration.inWholeHours}:" else "") +
            (duration.inWholeMinutes%60).toString() +
            String.format(":%02d", duration.inWholeSeconds % 60)
}