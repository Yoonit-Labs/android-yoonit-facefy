package ai.cyberlabs.yoonit.facefy

import android.graphics.PointF

interface FacefyEventListener {

    fun onFaceAnalysis(
        leftEyeOpenProbability: Float?,
        rightEyeOpenProbability: Float?,
        smilingProbability: Float?,
        headEulerAngleX: Float,
        headEulerAngleY: Float,
        headEulerAngleZ: Float
    )

    fun onContours(faceContours: MutableList<PointF>)

    fun onFace(x: Int, y: Int, width: Int, height: Int)
}