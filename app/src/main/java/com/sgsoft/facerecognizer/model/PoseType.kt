package com.sgsoft.facerecognizer.model

import com.sgsoft.facerecognizer.R

enum class PoseType(val value: String) {
    PART_FACE("part_face") {
        override fun getTextResId(): Int = R.string.part_face
    },
    FALSE_FACE("false_face") {
        override fun getTextResId(): Int = R.string.false_face
    },
    SUNGLASSES("sunglasses") {
        override fun getTextResId(): Int = R.string.sunglasses
    },
    FRONTAL_FACE("frontal_face") {
        override fun getTextResId(): Int = R.string.frontal_face
    },
    LEFT_FACE("left_face") {
        override fun getTextResId(): Int = R.string.left_face
    },
    RIGHT_FACE("right_face") {
        override fun getTextResId(): Int = R.string.right_face
    },
    ROTATE_FACE("rotate_face") {
        override fun getTextResId(): Int = R.string.rotate_face
    };

    abstract fun getTextResId(): Int

    companion object {
        private val map: MutableMap<String,PoseType> = HashMap()

        init {
            for(i in PoseType.values()) {
                map[i.value] = i
            }
        }

        fun fromString(str: String?) : PoseType? = map[str]
    }
}