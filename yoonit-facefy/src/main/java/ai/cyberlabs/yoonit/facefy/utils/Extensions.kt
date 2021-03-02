/**
 * +-+-+-+-+-+-+
 * |y|o|o|n|i|t|
 * +-+-+-+-+-+-+
 *
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * | Yoonit Face lib for Android applications                        |
 * | Victor Goulart & Haroldo Teruya @ Cyberlabs AI 2020             |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */


package ai.cyberlabs.yoonit.facefy.utils

import android.graphics.Rect

fun Rect.resize(width: Int, height: Int): Rect {
    val left = centerX() - (width / 2)
    val top = centerY() - (height / 2)

    return Rect(
        left,
        top,
        left + width,
        top + height
    )
}