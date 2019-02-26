package com.sgsoft.facerecognizer.model

import com.sgsoft.facerecognizer.R

enum class EmotionType(val value: String) {
    ANGRY("angry") {
        override fun getTextResId(): Int = R.string.angry
    },
    DISGUST("disgust") {
        override fun getTextResId(): Int = R.string.disgust
    },
    FEAR("fear") {
        override fun getTextResId(): Int = R.string.fear
    },
    LAUGH("laugh") {
        override fun getTextResId(): Int = R.string.laugh
    },
    NEUTRAL("neutral") {
        override fun getTextResId(): Int = R.string.neutral
    },
    SAD("sad") {
        override fun getTextResId(): Int = R.string.sad
    },
    SURPRISE("surprise") {
        override fun getTextResId(): Int = R.string.surprise
    },
    SMILE("smile") {
        override fun getTextResId(): Int = R.string.smile
    },
    TALKING("talking") {
        override fun getTextResId(): Int = R.string.talking
    };

    abstract fun getTextResId(): Int

    companion object {
        private val map: MutableMap<String,EmotionType> = HashMap()

        init {
            for(i in EmotionType.values()) {
                map[i.value] = i
            }
        }

        fun fromString(str: String?): EmotionType? = map[str]
    }
}