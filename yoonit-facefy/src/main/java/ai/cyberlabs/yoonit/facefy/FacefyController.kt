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


package ai.cyberlabs.yoonit.facefy

import ai.cyberlabs.yoonit.facefy.model.FaceDetected
import android.graphics.Bitmap
import android.graphics.PointF
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

/**
 * The Facefy controller to manipulate the face detection results and possible errors.
 */
internal class FacefyController {

    private val faceCoordinatesController = FaceCoordinatesController()

    private val faceDetectorOptions =
            FaceDetectorOptions
                    .Builder()
                    .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                    .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                    .build()

    fun detect(
        inputBitmap: Bitmap,
        onSuccess: (FaceDetected?) -> Unit,
        onError: (String) -> Unit,
        onComplete: () -> Unit
    ) {
        val detector = FaceDetection.getClient(faceDetectorOptions)

        val inputImage = InputImage.fromBitmap(inputBitmap,0)

        detector
            .process(inputImage)
            .addOnSuccessListener { faces ->

                // Get closest face.
                // Can be null if no face found.
                // Used to crop the face image.
                val closestFace: Face? = this.faceCoordinatesController.getClosestFace(faces)

                closestFace?.let { face ->

                    val faceContours = mutableListOf<PointF>()
                    face.allContours.forEach {faceContour ->
                        faceContour.points.forEach { pointF ->
                            faceContours.add(pointF)
                        }
                    }

                    onSuccess(
                        FaceDetected(
                            face.leftEyeOpenProbability,
                            face.rightEyeOpenProbability,
                            face.smilingProbability,
                            face.headEulerAngleX,
                            face.headEulerAngleY,
                            face.headEulerAngleZ,
                            faceContours,
                            face.boundingBox
                        )
                    )
                    return@addOnSuccessListener
                }

                onSuccess(null)
            }
            .addOnFailureListener { e ->
                e.message?.let { errorMessage -> onError(errorMessage) }
            }
            .addOnCompleteListener {
                detector.close()
                onComplete()
            }
    }
}
