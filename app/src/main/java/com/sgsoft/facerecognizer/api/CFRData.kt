package com.sgsoft.facerecognizer.api

data class CFRData(val info: Info, val faces: List<Face>)

data class Info(val size: Place?, val faceCount: Int)

data class Face(
        val roi: Place?,
        val landmark: Landmark?,
        val gender: Gender?,
        val age: Age?,
        val emotion: Emotion?,
        val pose: Pose?,
        val celebrity: Celebrity?
)

data class Landmark(
        val leftEye: Place?,
        val rightEye: Place?,
        val nose: Place?,
        val leftMouth: Place?,
        val rightMouth: Place?
)

data class Gender(val value: String, val confidence: Float)

data class Age(val value: String, val confidence: Float)

data class Emotion(val value: String, val confidence: Float)

data class Pose(val value: String, val confidence: Float)

data class Celebrity(val value: String, val confidence: Float)

data class Place(val width: Int?, val height: Int?, val x: Int?, val y: Int?)