package ai.cyberlabs.yoonit.facefy

import ai.cyberlabs.yoonit.facefy.model.FaceDetected
import ai.cyberlabs.yoonit.facefy.model.FacefyOptions
import android.graphics.PointF
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

internal class FacefyController {

    /**
     * Responsible to manipulate everything related with the face coordinates.
     */
    private val faceCoordinatesController = FaceCoordinatesController()

    private val faceDetectorOptions =
            FaceDetectorOptions
                    .Builder()
                    .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                    .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                    .build()

    private val detector = FaceDetection.getClient(faceDetectorOptions)

    fun detect(
        inputImage: InputImage,
        onFaceDetected: (FaceDetected) -> Unit,
        onMessage: (String) -> Unit
    ) {
        this.detector
                .process(inputImage)
                .addOnSuccessListener { faces ->

                    // Get closest face.
                    // Can be null if no face found.
                    // Used to crop the face image.
                    val closestFace: Face? = this.faceCoordinatesController.getClosestFace(faces)

                    closestFace?.let { face ->

                        val faceContours = mutableListOf<PointF>()
                        var leftEyeOpenProbability: Float? = null
                        var rightEyeOpenProbability: Float? = null
                        var smilingProbability: Float? = null

                        if (FacefyOptions.classification) {
                            leftEyeOpenProbability = face.leftEyeOpenProbability
                            rightEyeOpenProbability = face.rightEyeOpenProbability
                            smilingProbability = face.smilingProbability
                        }

                        if (FacefyOptions.contours) {
                            face.allContours.forEach {faceContour ->
                                faceContour.points.forEach { pointF ->
                                    faceContours.add(pointF)
                                }
                            }
                        }

                        onFaceDetected(
                            FaceDetected(
                                leftEyeOpenProbability,
                                rightEyeOpenProbability,
                                smilingProbability,
                                face.headEulerAngleX,
                                face.headEulerAngleY,
                                face.headEulerAngleZ,
                                faceContours,
                                face.boundingBox
                            )
                        )

                        return@addOnSuccessListener
                    }

                    onMessage("FACE_UNDETECTED")
            }
            .addOnFailureListener { e ->
                e.message?.let { message -> onMessage(message) }
            }
            .addOnCompleteListener { detector.close() }
    }
}