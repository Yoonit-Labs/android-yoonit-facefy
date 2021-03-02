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


package ai.cyberlabs.yoonit.facefy

import ai.cyberlabs.yoonit.facefy.model.FaceDetected
import com.google.mlkit.vision.common.InputImage

/**
 * The main Facefy class.
 * This class represents the input layer.
 */
open class Facefy {

    // Facefy controller instance.
    private val facefyController = FacefyController()

    /**
     * @param image The source image input to detect the face image.
     * @param onFaceDetected The success closure for face detected.
     * @param onMessage The message closure used to notify some states of the face detection.
     */
    fun detect(
            image: InputImage,
            onFaceDetected: (FaceDetected) -> Unit,
            onMessage: (String) -> Unit
    ) {
        this.facefyController.detect(image, onFaceDetected, onMessage)
    }
}