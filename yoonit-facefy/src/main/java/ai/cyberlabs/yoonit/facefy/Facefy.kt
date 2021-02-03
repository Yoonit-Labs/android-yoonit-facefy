package ai.cyberlabs.yoonit.facefy

import ai.cyberlabs.yoonit.facefy.model.FacefyOptions
import com.google.mlkit.vision.common.InputImage

class Facefy(facefyEventListener: FacefyEventListener) {

    private val facefyController = FacefyController(facefyEventListener)

    fun detect(image: InputImage) = facefyController.detect(image)

    fun setFaceClassification(enabled: Boolean) {
        FacefyOptions.faceClassification = enabled
    }

    fun setFaceContours(enabled: Boolean) {
        FacefyOptions.faceContours = enabled
    }

    fun setFaceBoundingBox(enabled: Boolean) {
        FacefyOptions.faceBoundingBox = enabled
    }
}