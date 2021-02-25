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

    var roiEnable: Boolean = FacefyOptions.roi.enable
        set(value) {
            FacefyOptions.roi.enable = value
            field = value
        }

    var roiRect: RectF = FacefyOptions.roi.rectOffset
        set(value) {
            FacefyOptions.roi.rectOffset = value
            field = value
        }

    var roiDetectMinSize: Float = FacefyOptions.roi.minimumSize
        set(value) {
            FacefyOptions.roi.minimumSize = value
            field = value
        }

    var detectMinSize: Float = FacefyOptions.detectMinSize
        set(value) {
            FacefyOptions.detectMinSize = value
            field = value
        }

    var detectMaxSize: Float = FacefyOptions.detectMaxSize
        set(value) {
            FacefyOptions.detectMaxSize = value
            field = value
        }

    fun detect(image: InputImage, onFaceDetected: (FaceDetected) -> Unit, onMessage: (String) -> Unit) {
        facefyController.detect(image, onFaceDetected, onMessage)
    }
}