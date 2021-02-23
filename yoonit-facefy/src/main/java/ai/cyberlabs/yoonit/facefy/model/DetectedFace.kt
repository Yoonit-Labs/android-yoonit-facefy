package ai.cyberlabs.yoonit.facefy.model

import android.graphics.PointF

data class DetectedFace(
    var leftEyeOpenProbability: Float?,
    var rightEyeOpenProbability: Float?,
    var smilingProbability: Float?,
    var headEulerAngleX: Float,
    var headEulerAngleY: Float,
    var headEulerAngleZ: Float,
    var faceContours: MutableList<PointF> = mutableListOf(),
    var x: Int,
    var y: Int,
    var width: Int,
    var height: Int,
    var originalImageWidth: Int,
    var originalImageHeight: Int
)