/**
 * ██╗   ██╗ ██████╗  ██████╗ ███╗   ██╗██╗████████╗
 * ╚██╗ ██╔╝██╔═══██╗██╔═══██╗████╗  ██║██║╚══██╔══╝
 *  ╚████╔╝ ██║   ██║██║   ██║██╔██╗ ██║██║   ██║
 *   ╚██╔╝  ██║   ██║██║   ██║██║╚██╗██║██║   ██║
 *    ██║   ╚██████╔╝╚██████╔╝██║ ╚████║██║   ██║
 *    ╚═╝    ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚═╝   ╚═╝
 *
 * https://yoonit.dev - about@yoonit.dev
 *
 * Yoonit Facefy
 * The face detection's module for Android with a lot of awesome features
 *
 * Haroldo Teruya & Victor Goulart @ 2020-2021
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
