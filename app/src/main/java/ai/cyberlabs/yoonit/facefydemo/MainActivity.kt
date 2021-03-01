/**
 * +-+-+-+-+-+-+
 * |y|o|o|n|i|t|
 * +-+-+-+-+-+-+
 *
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * | Yoonit Facefy lib for Android applications                      |
 * | Victor Goulart & Haroldo Teruya @ Cyberlabs AI 2021             |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
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
import com.google.mlkit.vision.common.InputImage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var cameraView: CameraView

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

            imageBitmap?.let { imageBitmap ->
                val inputImage = InputImage.fromBitmap(imageBitmap, 0)

                Facefy().detect(
                    inputImage,
                    {faceDetected ->
                        faceDetected.leftEyeOpenProbability?.let {leftEyeOpenProbability ->
                            left_eye_open_probability.text =
                                getString(
                                    R.string.left_eye_open_label,
                                    leftEyeOpenProbability.times(100).toString()
                                )
                        }
                        faceDetected.rightEyeOpenProbability?.let {rightEyeOpenProbability ->
                            right_eye_open_probability.text =
                                getString(
                                    R.string.right_eye_open_label,
                                    rightEyeOpenProbability.times(100).toString()
                                )
                        }
                        faceDetected.smilingProbability?.let {smilingProbability ->
                            smiling_probability.text =
                                getString(
                                    R.string.smiling_probability_label,
                                    smilingProbability.times(100).toString()
                                )
                        }

                        setFaceMovement(faceDetected.headEulerAngleY)

                        setPreviewImage(BitmapFactory.decodeFile(imagePath), faceDetected.boundingBox)
                    },
                    { message -> Log.d(TAG, message) }
                )
            }
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

    private fun setFaceMovement(headAngleY: Float) {
        when {
            headAngleY < -36 -> {
                face_movement.text =
                    getString(
                        R.string.face_movement,
                        "Super Left"
                    )
            }
            headAngleY > -36 && headAngleY < -12 -> {
                face_movement.text =
                    getString(
                        R.string.face_movement,
                        "Left"
                    )
            }
            headAngleY > -12 && headAngleY < 12 -> {
                face_movement.text =
                    getString(
                        R.string.face_movement,
                        "Frontal"
                    )
            }
            headAngleY > 12 && headAngleY < 36 -> {
                face_movement.text =
                    getString(
                        R.string.face_movement,
                        "Right"
                    )
            }
            headAngleY > 36 -> {
                face_movement.text =
                    getString(
                        R.string.face_movement,
                        "Super Right"
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