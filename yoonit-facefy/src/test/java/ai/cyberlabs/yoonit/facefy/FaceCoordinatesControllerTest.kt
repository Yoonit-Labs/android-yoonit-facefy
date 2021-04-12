package ai.cyberlabs.yoonit.facefy

import android.graphics.Rect
import com.google.mlkit.vision.face.Face
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FaceCoordinatesControllerTest {

    private val bigRect: Rect = mock {
        on { width() } doReturn 300
        on { height() } doReturn 300
    }

    private val smallRect: Rect = mock {
        on { width() } doReturn 300
        on { height() } doReturn 300
    }

    private val biggestFace: Face = mock {
        on { boundingBox } doReturn bigRect
    }

    private val smallestFace: Face = mock {
        on { boundingBox } doReturn smallRect
    }

    private val faces: MutableList<Face> = mutableListOf(biggestFace, smallestFace)

    private val SUT = FaceCoordinatesController()

    @Test
    fun `Test Get Closest Face`() {
        val closestFace = SUT.getClosestFace(faces)
        assertThat(closestFace?.boundingBox).isEqualTo(biggestFace.boundingBox)
    }

    @Test
    fun `Test Get Closest Face with Empty Face`() {
        val closestFace = SUT.getClosestFace(mutableListOf())
        assertThat(closestFace).isNull()
    }
}
