package ai.cyberlabs.yoonit.facefy.model

object Message {
    // Face width percentage in relation of the screen width is less than the CaptureOptions.faceCaptureMinSize
    const val INVALID_MIN_SIZE = "INVALID_MIN_SIZE"

    // Face width percentage in relation of the screen width is more than the CaptureOptions.faceCaptureMinSize
    const val INVALID_MAX_SIZE = "INVALID_MAX_SIZE"

    // Face bounding box is out of the setted region of interest.
    const val INVALID_FACE_OUT_OF_ROI = "INVALID_FACE_OUT_OF_ROI"

    // Face width percentage in relation of the screen width is less than the CaptureOptions.FaceROI.minimumSize.
    const val INVALID_ROI_MIN_SIZE = "INVALID_ROI_MIN_SIZE"

    const val FACE_UNDETECTED = "FACE_UNDETECTED"
}