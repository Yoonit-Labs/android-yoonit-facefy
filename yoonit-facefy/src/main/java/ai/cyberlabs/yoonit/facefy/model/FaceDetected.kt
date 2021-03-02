/**
 * +-+-+-+-+-+-+
 * |y|o|o|n|i|t|
 * +-+-+-+-+-+-+
 *
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * | Yoonit Face lib for Android applications                        |
 * | Victor Goulart & Haroldo Teruya @ Cyberlabs AI 2020             |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */


package ai.cyberlabs.yoonit.facefy.model

import android.graphics.PointF
import android.graphics.Rect

data class FaceDetected(
    var leftEyeOpenProbability: Float?,
    var rightEyeOpenProbability: Float?,
    var smilingProbability: Float?,
    var headEulerAngleX: Float,
    var headEulerAngleY: Float,
    var headEulerAngleZ: Float,
    var contours: MutableList<PointF> = mutableListOf(),
    var boundingBox: Rect
)