package ai.cyberlabs.yoonit.facefy

import ai.cyberlabs.yoonit.facefy.model.FaceDetected
import ai.cyberlabs.yoonit.facefy.model.FacefyOptions
import ai.cyberlabs.yoonit.facefy.model.Message
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import androidx.core.graphics.toRectF
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
                        var roi = Rect()
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

                        if (FacefyOptions.faceROI.enable) {
                            val rect = Rect(1,2,4,5)
                            rect.top = 1

                            roi = Rect(
                                (inputImage.width * FacefyOptions.faceROI.rectOffset.left).toInt(),
                                (inputImage.height * FacefyOptions.faceROI.rectOffset.top).toInt(),
                                (inputImage.width - (inputImage.width * FacefyOptions.faceROI.rectOffset.right)).toInt(),
                                (inputImage.height - (inputImage.height * FacefyOptions.faceROI.rectOffset.bottom)).toInt()
                            )
                        }

                        val boundingBox: RectF = face.boundingBox.toRectF()

                        val message = this.getMessage(boundingBox, inputImage.width, inputImage.height)

                        if (message.isNotEmpty()) {
                            onMessage(message)
                            return@addOnSuccessListener
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
                                face.boundingBox,
                                roi
                            )
                        )
                    }
            }
            .addOnFailureListener { e ->
                e.message?.let { message -> onMessage(message) }
            }
            .addOnCompleteListener { detector.close() }
    }

    private fun getMessage(boundingBox: RectF, imageWidth: Int, imageHeight: Int): String {
        val boundingBoxWidthRelatedWithImage = boundingBox.width() / imageWidth

        if (boundingBoxWidthRelatedWithImage < FacefyOptions.faceCaptureMinSize) {
            return Message.INVALID_CAPTURE_FACE_MIN_SIZE
        }

        if (boundingBoxWidthRelatedWithImage > FacefyOptions.faceCaptureMaxSize) {
            return Message.INVALID_CAPTURE_FACE_MAX_SIZE
        }

        val topOffset: Float = boundingBox.top / imageHeight
        val rightOffset: Float = (imageWidth - boundingBox.right) / imageWidth
        val bottomOffset: Float = (imageHeight - boundingBox.bottom) / imageHeight
        val leftOffset: Float = boundingBox.left / imageWidth

        if (FacefyOptions.faceROI.isOutOf(
                topOffset,
                rightOffset,
                bottomOffset,
                leftOffset)
        ) {
            return Message.INVALID_CAPTURE_FACE_OUT_OF_ROI
        }

        if (FacefyOptions.faceROI.hasChanges) {

            // Face is inside the region of interest and faceROI is setted.
            // Face is smaller than the defined "minimumSize".
            val roiWidth: Float =
                imageWidth -
                        ((FacefyOptions.faceROI.rectOffset.right + FacefyOptions.faceROI.rectOffset.left) * imageWidth)
            val faceRelatedWithROI: Float = boundingBox.width() / roiWidth

            if (FacefyOptions.faceROI.minimumSize > faceRelatedWithROI) {
                return Message.INVALID_CAPTURE_FACE_ROI_MIN_SIZE
            }
        }

        return ""
    }
}