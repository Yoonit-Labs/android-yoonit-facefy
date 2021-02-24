package ai.cyberlabs.yoonit.facefy.model

import android.graphics.Color
import android.graphics.RectF

/**
 * Model to set face region of interest.
 */
class FaceROI {
    // Enable or disable ROI.
    var enable: Boolean = false

    // Region of interest in percentage.
    // Values valid [0, 1].
    var rectOffset: RectF = RectF()

    // Minimum face size in percentage in relation of the ROI.
    var minimumSize: Float = 0.0f

    // Return if any attributes has modifications.
    val hasChanges: Boolean
        get() {
            return (this.rectOffset.top != 0.0f ||
                    this.rectOffset.right != 0.0f ||
                    this.rectOffset.bottom != 0.0f ||
                    this.rectOffset.left != 0.0f)
        }

    /**
     * Current offsets is out of the offset parameters.
     *
     * @param topOffset top offset.
     * @param rightOffset right offset.
     * @param bottomOffset bottom offset.
     * @param leftOffset left offset.
     * @return is out of the offset parameters.
     */
    fun isOutOf(
        topOffset: Float,
        rightOffset: Float,
        bottomOffset: Float,
        leftOffset: Float
    ): Boolean {
        return (this.rectOffset.top > topOffset ||
                this.rectOffset.right > rightOffset ||
                this.rectOffset.bottom > bottomOffset ||
                this.rectOffset.left > leftOffset)
    }
}