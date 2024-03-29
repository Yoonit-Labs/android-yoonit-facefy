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


package ai.cyberlabs.yoonit.facefy

import ai.cyberlabs.yoonit.facefy.utils.resize
import com.google.mlkit.vision.face.Face
import kotlin.math.max

internal class FaceCoordinatesController {

    /**
     * Get closest face.
     * Can be null if no face found.
     *
     * @param faces the face list camera detected;
     * @return the closest face. null if no face found.
     */
    fun getClosestFace(faces: List<Face>): Face? {

        //  Check if found face.
        if (faces.isEmpty()) {
            return null
        }

        // Get closest face.
        var closestFace: Face? = null
        faces.forEach {
            if (closestFace == null ||
                it.boundingBox.width() > closestFace!!.boundingBox.width()
            ) {
                closestFace = it
            }
        }

        // Transform bounding box rectangle in square.
        val size = max(
            closestFace!!.boundingBox.width(),
            closestFace!!.boundingBox.height()
        )
        closestFace?.let {
            it.boundingBox.set(it.boundingBox.resize(size, size))
        }

        return closestFace
    }
}
