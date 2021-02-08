package ai.cyberlabs.yoonit.facefy

import ai.cyberlabs.yoonit.facefy.model.FacefyOptions
import android.graphics.PointF
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

internal class FacefyController(private var facefyEventListener: FacefyEventListener) {

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

    fun detect(inputImage: InputImage) {
        this.detector
                .process(inputImage)
                .addOnSuccessListener { faces ->

                    // Get closest face.
                    // Can be null if no face found.
                    // Used to crop the face image.
                    val closestFace: Face? = this.faceCoordinatesController.getClosestFace(faces)

                    closestFace?.let { face ->

                        if (FacefyOptions.faceClassification) {
                            facefyEventListener.onFaceAnalysis(
                                face.leftEyeOpenProbability,
                                face.rightEyeOpenProbability,
                                face.smilingProbability,
                                face.headEulerAngleX,
                                face.headEulerAngleY,
                                face.headEulerAngleZ
                            )
                        }

                        if (FacefyOptions.faceContours) {
                            val faceContours = mutableListOf<PointF>()

                            face.allContours.forEach {faceContour ->
                                faceContour.points.forEach { pointF ->
                                    faceContours.add(pointF)
                                }
                            }
                            facefyEventListener.onContours(faceContours)
                        }

                        if (FacefyOptions.faceBoundingBox) {
                            facefyEventListener.onFace(
                                face.boundingBox.left,
                                face.boundingBox.top,
                                face.boundingBox.width(),
                                face.boundingBox.height()
                            )
                        }
                    }
            }
    }
}