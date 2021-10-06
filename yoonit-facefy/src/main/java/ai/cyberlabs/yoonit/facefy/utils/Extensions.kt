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
