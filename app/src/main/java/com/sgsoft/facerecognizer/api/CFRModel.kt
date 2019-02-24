package com.sgsoft.facerecognizer.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CFRModel(
        val info: Info,
        val faces: List<Face>
) : Parcelable

@Parcelize
data class Info(
        val size: Place?,
        val faceCount: Int = 0
) : Parcelable

@Parcelize
data class Face(
        val roi: Place?,
        val landmark: Landmark?,
        val gender: Gender?,
        val age: Age?,
        val emotion: Emotion?,
        val pose: Pose?,
        val celebrity: Celebrity?
) : Parcelable

@Parcelize
data class Landmark(
        val leftEye: Place?,
        val rightEye: Place?,
        val nose: Place?,
        val leftMouth: Place?,
        val rightMouth: Place?
) : Parcelable

@Parcelize
data class Gender(val value: String, val confidence: Float) : Parcelable

@Parcelize
data class Age(val value: String, val confidence: Float) : Parcelable

@Parcelize
data class Emotion(val value: String, val confidence: Float) : Parcelable

@Parcelize
data class Pose(val value: String, val confidence: Float) : Parcelable

@Parcelize
data class Celebrity(val value: String, val confidence: Float) : Parcelable

@Parcelize
data class Place(val width: Int?, val height: Int?, val x: Int?, val y: Int?) : Parcelable