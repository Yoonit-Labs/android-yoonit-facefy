package ai.cyberlabs.yoonit.facefy

import android.graphics.PointF
import android.graphics.Rect

interface FacefyEventListener {

    fun onEyesDetected(leftEyeOpenProbability: Float, rightEyeOpenProbability: Float)

    fun onSmileDetected(smilingProbability: Float)

    fun onContoursDetected(faceContours: MutableList<PointF>)

    fun onFaceDetected(boundingBox: Rect)
}