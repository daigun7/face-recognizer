package com.sgsoft.facerecognizer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FaceEntity(
        val gender: Gender?,
        val age: Age?,
        val emotion: Emotion?,
        val pose: Pose?,
        val celebrity: Celebrity?
) : Parcelable

@Parcelize
data class Gender(var value: String, var confidence: Int) : Parcelable

@Parcelize
data class Age(var value: String, var confidence: Int) : Parcelable

@Parcelize
data class Emotion(var value: String, var confidence: Int) : Parcelable

@Parcelize
data class Pose(var value: String, var confidence: Int) : Parcelable

@Parcelize
data class Celebrity(var value: String, var confidence: Int) : Parcelable