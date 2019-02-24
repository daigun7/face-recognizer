package com.sgsoft.facerecognizer.model

import android.content.res.Resources
import android.os.Parcelable
import com.sgsoft.facerecognizer.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Face(
        val gender: String?,
        val age: String?,
        val emotion: String?,
        val pose: String?,
        val celebrity: String?
) : Parcelable {

    companion object {
        fun from(resource: Resources, from: com.sgsoft.facerecognizer.api.Face): Face {
            val gender = from.gender?.let { gender ->
                val confidence = (gender.confidence * 100f).toInt()
                val value = GenderType.fromString(gender.value)?.getTextResId()?.let {
                    resource.getString(it)
                } ?: gender.value

                resource.getString(R.string.gender_format, value, confidence)
            }

            val age = from.age?.let {
                val confidence = (it.confidence * 100f).toInt()
                resource.getString(R.string.age_format, it.value, confidence)
            }

            val emotion = from.emotion?.let { emotion ->
                val confidence = (emotion.confidence * 100f).toInt()
                val value = EmotionType.fromString(emotion.value)?.getTextResId()?.let {
                    resource.getString(it)
                } ?: emotion.value

                resource.getString(R.string.emotion_format, value, confidence)
            }

            val pose = from.pose?.let { pose ->
                val confidence = (pose.confidence * 100f).toInt()
                val value = PoseType.fromString(pose.value)?.getTextResId()?.let {
                    resource.getString(it)
                } ?: pose.value

                resource.getString(R.string.pose_format, value, confidence)
            }


            val celebrity = from.celebrity?.let {
                val confidence = (it.confidence * 100f).toInt()
                resource.getString(R.string.celebrity_format, it.value, confidence)
            }


            return Face(gender, age, emotion, pose, celebrity)
        }
    }
}