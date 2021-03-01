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
                        var roiRect = Rect()
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

                        if (FacefyOptions.roi.enable) {
                            roiRect = Rect(
                                (inputImage.width * FacefyOptions.roi.rectOffset.left).toInt(),
                                (inputImage.height * FacefyOptions.roi.rectOffset.top).toInt(),
                                (inputImage.width - (inputImage.width * FacefyOptions.roi.rectOffset.right)).toInt(),
                                (inputImage.height - (inputImage.height * FacefyOptions.roi.rectOffset.bottom)).toInt()
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
                                roiRect
                            )
                        )

                        return@addOnSuccessListener
                    }

                    onMessage(Message.FACE_UNDETECTED)
            }
            .addOnFailureListener { e ->
                e.message?.let { message -> onMessage(message) }
            }
            .addOnCompleteListener { detector.close() }
    }

    private fun getMessage(boundingBox: RectF, imageWidth: Int, imageHeight: Int): String {
        val boundingBoxWidthRelatedWithImage = boundingBox.width() / imageWidth

        if (boundingBoxWidthRelatedWithImage < FacefyOptions.detectMinSize) {
            return Message.INVALID_MIN_SIZE
        }

        if (boundingBoxWidthRelatedWithImage > FacefyOptions.detectMaxSize) {
            return Message.INVALID_MAX_SIZE
        }

        val topOffset: Float = boundingBox.top / imageHeight
        val rightOffset: Float = (imageWidth - boundingBox.right) / imageWidth
        val bottomOffset: Float = (imageHeight - boundingBox.bottom) / imageHeight
        val leftOffset: Float = boundingBox.left / imageWidth

        if (FacefyOptions.roi.isOutOf(
                topOffset,
                rightOffset,
                bottomOffset,
                leftOffset)
        ) {
            return Message.INVALID_FACE_OUT_OF_ROI
        }

        if (FacefyOptions.roi.hasChanges) {

            // Face is inside the region of interest and faceROI is setted.
            // Face is smaller than the defined "minimumSize".
            val roiWidth: Float =
                imageWidth -
                        ((FacefyOptions.roi.rectOffset.right + FacefyOptions.roi.rectOffset.left) * imageWidth)
            val faceRelatedWithROI: Float = boundingBox.width() / roiWidth

            if (FacefyOptions.roi.minimumSize > faceRelatedWithROI) {
                return Message.INVALID_ROI_MIN_SIZE
            }
        }

        return ""
    }
}