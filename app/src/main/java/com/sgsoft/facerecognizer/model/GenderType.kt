package com.sgsoft.facerecognizer.model

import com.sgsoft.facerecognizer.R

enum class GenderType(val value: String) {
    MALE("male") {
        override fun getTextResId(): Int = R.string.male
    },
    FEMALE("female") {
        override fun getTextResId(): Int = R.string.female
    };

    abstract fun getTextResId() : Int

    companion object {
        private val map: MutableMap<String,GenderType> = HashMap()

        init {
            for(i in GenderType.values()) {
                map[i.value] = i
            }
        }

        fun fromString(str: String?) : GenderType? = map[str]
    }
}