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

package ai.cyberlabs.yoonit.facefydemo

import ai.cyberlabs.yoonit.camera.CameraView
import ai.cyberlabs.yoonit.camera.interfaces.CameraEventListener
import ai.cyberlabs.yoonit.facefy.Facefy
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var cameraView: CameraView

    private var facefy: Facefy = Facefy()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.cameraView = camera_view

        this.cameraView.setCameraEventListener(this.buildCameraEventListener())

        if (this.allPermissionsGranted()) {
            this.cameraView.startPreview()

            this.cameraView.startCaptureType("frame")

            this.cameraView.setSaveImageCaptured(true)

            return
        }

        ActivityCompat.requestPermissions(
            this,
            REQUIRED_PERMISSIONS,
            PackageManager.PERMISSION_GRANTED
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        this.cameraView.destroy()
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this.baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun buildCameraEventListener(): CameraEventListener = object : CameraEventListener {

        override fun onImageCaptured(
            type: String,
            count: Int,
            total: Int,
            imagePath: String,
            inferences: ArrayList<android.util.Pair<String, FloatArray>>
        ) {
            Log.d(TAG, "onImageCaptured ")

            val imageBitmap = BitmapFactory.decodeFile(imagePath)

            facefy.detect(
                imageBitmap,
                { faceDetected ->
                    faceDetected?.let { faceDetected ->
                        faceDetected.leftEyeOpenProbability?.let {leftEyeOpenProbability ->
                            if (leftEyeOpenProbability > 0.8) {
                                left_eye_open_probability.text =
                                    getString(
                                        R.string.left_eye_open_label,
                                        "Open"
                                    )
                                return@let
                            }
                            left_eye_open_probability.text =
                                getString(
                                    R.string.left_eye_open_label,
                                    "Close"
                                )
                        }
                        faceDetected.rightEyeOpenProbability?.let {rightEyeOpenProbability ->
                            if (rightEyeOpenProbability > 0.8) {
                                right_eye_open_probability.text =
                                    getString(
                                        R.string.right_eye_open_label,
                                        "Open"
                                    )
                                return@let
                            }
                            right_eye_open_probability.text =
                                getString(
                                    R.string.right_eye_open_label,
                                    "Close"
                                )
                        }
                        faceDetected.smilingProbability?.let {smilingProbability ->
                            if (smilingProbability > 0.8) {
                                smiling_probability.text =
                                    getString(
                                        R.string.smiling_probability_label,
                                        "Smiling"
                                    )
                                return@let
                            }
                            smiling_probability.text =
                                getString(
                                    R.string.smiling_probability_label,
                                    "Not Smiling"
                                )
                        }

                        setFaceHorizontalMovement(faceDetected.headEulerAngleY)
                        setFaceVerticalMovement(faceDetected.headEulerAngleX)
                        setFaceTiltMovement(faceDetected.headEulerAngleZ)

                        val boundingBox = faceDetected.boundingBox
                        val bitmap = BitmapFactory.decodeFile(imagePath)

                        if (
                            (boundingBox.left + boundingBox.width()) <= bitmap.width &&
                            (boundingBox.top + boundingBox.height()) <= bitmap.height &&
                            boundingBox.left >= 0
                        ) {
                            setPreviewImage(bitmap, faceDetected.boundingBox)
                        }
                        return@let
                    }

                    onFaceUndetected()
                },
                { errorMessage -> Log.d(TAG, errorMessage) },
                { Log.d(TAG, "onComplete") }
            )
        }

        override fun onFaceDetected(x: Int, y: Int, width: Int, height: Int) {
            Log.d(TAG, "onFaceDetected $x, $y, $width, $height.")
        }

        override fun onFaceUndetected() {
            Log.d(TAG, "onFaceUndetected")
        }

        override fun onEndCapture() {
            Log.d(TAG, "onEndCapture")

        }

        override fun onError(error: String) {
            Log.d(TAG, "onError: $error")
        }

        override fun onMessage(message: String) {
            Log.d(TAG, "onMessage: $message")
        }

        override fun onPermissionDenied() {
            Log.d(TAG, "onPermissionDenied")
        }

        override fun onQRCodeScanned(content: String) {
            Log.d(TAG, "onQRCodeScanned")
        }
    }

    private fun setPreviewImage(bitmap: Bitmap, boundingBox: Rect) {
        val croppedBitmap =
            Bitmap.createBitmap(
                bitmap,
                boundingBox.left,
                boundingBox.top,
                boundingBox.width(),
                boundingBox.height()
            )

        image_preview.setImageBitmap(croppedBitmap)
    }

    private fun setFaceHorizontalMovement(headAngleY: Float) {
        when {
            headAngleY < -36 -> {
                horizontal_movement.text =
                    getString(
                        R.string.horizontal_movement,
                        "Super Left"
                    )
            }
            headAngleY > -36 && headAngleY < -12 -> {
                horizontal_movement.text =
                    getString(
                        R.string.horizontal_movement,
                        "Left"
                    )
            }
            headAngleY > -12 && headAngleY < 12 -> {
                horizontal_movement.text =
                    getString(
                        R.string.horizontal_movement,
                        "Frontal"
                    )
            }
            headAngleY > 12 && headAngleY < 36 -> {
                horizontal_movement.text =
                    getString(
                        R.string.horizontal_movement,
                        "Right"
                    )
            }
            headAngleY > 36 -> {
                horizontal_movement.text =
                    getString(
                        R.string.horizontal_movement,
                        "Super Right"
                    )
            }
        }
    }

   private fun setFaceVerticalMovement(headAngleX: Float) {
        when {
            headAngleX < -36 -> {
                vertical_movement.text =
                    getString(
                        R.string.vertical_movement,
                        "Super Down"
                    )
            }
            headAngleX > -36 && headAngleX < -12 -> {
                vertical_movement.text =
                    getString(
                        R.string.vertical_movement,
                        "Down"
                    )
            }
            headAngleX > -12 && headAngleX < 12 -> {
                vertical_movement.text =
                    getString(
                        R.string.vertical_movement,
                        "Frontal"
                    )
            }
            headAngleX > 12 && headAngleX < 36 -> {
                vertical_movement.text =
                    getString(
                        R.string.vertical_movement,
                        "Up"
                    )
            }
            headAngleX > 36 -> {
                vertical_movement.text =
                    getString(
                        R.string.vertical_movement,
                        "Super Up"
                    )
            }
        }
    }

    private fun setFaceTiltMovement(headAngleZ: Float) {
        when {
            headAngleZ < -36 -> {
                tilt_movement.text =
                    getString(
                        R.string.tilt_movement,
                        "Super Right"
                    )
            }
            headAngleZ > -36 && headAngleZ < -12 -> {
                tilt_movement.text =
                    getString(
                        R.string.tilt_movement,
                        "Right"
                    )
            }
            headAngleZ > -12 && headAngleZ < 12 -> {
                tilt_movement.text =
                    getString(
                        R.string.tilt_movement,
                        "Frontal"
                    )
            }
            headAngleZ > 12 && headAngleZ < 36 -> {
                tilt_movement.text =
                    getString(
                        R.string.tilt_movement,
                        "Left"
                    )
            }
            headAngleZ > 36 -> {
                tilt_movement.text =
                    getString(
                        R.string.tilt_movement,
                        "Super Left"
                    )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PackageManager.PERMISSION_GRANTED) {
            if (allPermissionsGranted()) {
                this.cameraView.startPreview()
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    companion object {
        private const val TAG = "YoonitFacefyDemo"
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}
