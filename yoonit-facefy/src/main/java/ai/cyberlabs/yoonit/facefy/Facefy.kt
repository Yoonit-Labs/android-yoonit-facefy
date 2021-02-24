package ai.cyberlabs.yoonit.facefy

import ai.cyberlabs.yoonit.facefy.model.FaceDetected
import ai.cyberlabs.yoonit.facefy.model.FacefyOptions
import android.graphics.RectF
import com.google.mlkit.vision.common.InputImage

class Facefy {

    private val facefyController = FacefyController()

    var classification: Boolean = FacefyOptions.classification
        set(value) {
            FacefyOptions.classification = value
            field = value
        }

    var contours: Boolean = FacefyOptions.contours
        set(value) {
            FacefyOptions.contours = value
            field = value
        }

    var boundingBox: Boolean = FacefyOptions.boundingBox
        set(value) {
            FacefyOptions.boundingBox = value
            field = value
        }

    var roiEnable: Boolean = FacefyOptions.faceROI.enable
        set(value) {
            FacefyOptions.faceROI.enable = value
            field = value
        }

    var roiRect: RectF = FacefyOptions.faceROI.rectOffset
        set(value) {
            FacefyOptions.faceROI.rectOffset = value
            field = value
        }

    var roiDetectMinSize: Float = FacefyOptions.faceROI.minimumSize
        set(value) {
            FacefyOptions.faceROI.minimumSize = value
            field = value
        }

    var detectMinSize: Float = FacefyOptions.faceCaptureMinSize
        set(value) {
            FacefyOptions.faceCaptureMinSize = value
            field = value
        }

    var detectMaxSize: Float = FacefyOptions.faceCaptureMaxSize
        set(value) {
            FacefyOptions.faceCaptureMaxSize = value
            field = value
        }

    fun detect(image: InputImage, onFaceDetected: (FaceDetected) -> Unit, onMessage: (String) -> Unit) {
        facefyController.detect(image, onFaceDetected, onMessage)
    }
}