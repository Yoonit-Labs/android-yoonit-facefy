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
import android.graphics.Bitmap

/**
 * The main Facefy class.
 * This class represents the input layer.
 */
open class Facefy {

    // Facefy controller instance.
    private val facefyController = FacefyController()

    /**
     * @param inputBitmap The source image input to detect the face image.
     * @param onSuccess The success closure for face detected.
     * @param onError The message closure used to notify some states of the face detection.
     */
    fun detect(
        inputBitmap: Bitmap,
        onSuccess: (FaceDetected?) -> Unit,
        onError: (String) -> Unit,
        onComplete: () -> Unit
    ) {
        this.facefyController.detect(inputBitmap, onSuccess, onError, onComplete)
    }
}