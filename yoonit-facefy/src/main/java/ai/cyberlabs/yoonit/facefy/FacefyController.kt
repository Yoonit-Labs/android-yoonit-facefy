package ai.cyberlabs.yoonit.facefy

import ai.cyberlabs.yoonit.facefy.model.DetectedFace
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
        onFaceDetected: (DetectedFace) -> Unit
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
                        var boundingBoxLeft = 0
                        var boundingBoxTop = 0
                        var boundingBoxWidth = 0
                        var boundingBoxHeight = 0

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

                        if (FacefyOptions.classification) {
                            boundingBoxLeft = face.boundingBox.left
                            boundingBoxTop = face.boundingBox.top
                            boundingBoxWidth = face.boundingBox.width()
                            boundingBoxHeight = face.boundingBox.height()
                        }

                        onFaceDetected(
                            DetectedFace(
                                leftEyeOpenProbability,
                                rightEyeOpenProbability,
                                smilingProbability,
                                face.headEulerAngleX,
                                face.headEulerAngleY,
                                face.headEulerAngleZ,
                                faceContours,
                                boundingBoxLeft,
                                boundingBoxTop,
                                boundingBoxWidth,
                                boundingBoxHeight,
                                inputImage.width,
                                inputImage.height
                            )
                        )
                    }
            }
            .addOnCompleteListener { detector.close() }
    }
}