<img src="https://raw.githubusercontent.com/Yoonit-Labs/android-yoonit-camera/development/logo_cyberlabs.png" width="300">

# android-yoonit-facefy

A Android library to provide:
- [Standart Google ML Kit](https://developers.google.com/ml-kit)
- Face detection
- Face contours
- Face expressions
- Face movement

## Install
  
Add the JitPack repository to your root `build.gradle` at the end of repositories  

```groovy  
allprojects {
	repositories {  
	... 
	maven { url 'https://jitpack.io' }
	} 
}  
```  

Add the dependency  

```groovy  
dependencies {
	implementation 'com.github.Yoonit-Labs:android-yoonit-facefy:master-SNAPSHOT'
}
```  

## Usage

This is a basic usage to the `FacefyYoonit`.
Feel free to use the demo.

```kotlin
import ai.cyberlabs.yoonit.facefy.Facefy

...

    fun example(inputImage: InputImage) {
        Facefy()
            .detect(
                inputImage,
                { faceDetected ->
                    val boudingBox: Rect = faceDetected.boundingBox
                    val contours: MutableList<PointF> = faceDetected.contours
                    val headEulerAngleX: Float = faceDetected.headEulerAngleX
                    val headEulerAngleY: Float = faceDetected.headEulerAngleY
                    val headEulerAngleZ: Float = faceDetected.headEulerAngleZ
                    val leftEyeOpenProbability: Float? = faceDetected.leftEyeOpenProbability
                    val rightEyeOpenProbability: Float? = faceDetected.rightEyeOpenProbability
                    val smilingProbability: Float? = faceDetected.smilingProbability
                },
                { message ->
                    val mesage: String = message
                }
            )
    }
```

## API

### Methods

| Function | Parameters                                                                                                                                                                                               | Return Type | Description |
| -              | -                                                                                                                                                                                                                | -                   | -                 |
| detect     |  `image: InputImage, onFaceDetected: (FaceDetected) -> Unit, onMessage: (String) -> Unit` | void   | Detect a face from image and return the result in the [`FaceDetected`](#facedetected) as a closure. |

### FaceDetected

| Attribute               | Type                | Description                                                                                    |
| -                       | -                   | -                                                                                              |
| leftEyeOpenProbability  | Float?              | The left eye open probability.                                                                 |
| rightEyeOpenProbability | Float?              | The right eye open probability.                                                                |
| smilingProbability      | Float?              | The smilling probability.                                                                      |
| headEulerAngleY         | Float               | The angle that points the "left-right" head direction. See [HeadEulerAngleY](#headeulerangley) |
| contours                | Mutablelist<PointF> | List of Points that represents the shape of the recognized face.                               |
| boundingBox             | Rect                | The face bounding box.                                                                         |

#### Euler Angle Y Detectable Landmarks

Landmarks are points of interest within a face regarding the Euler Angle Y 

| Euler Angle Y                     | Detectable landmarks                                      
| -                                 | -                                              
| < -36 degrees                     | left eye, left mouth, left ear, nose base, left cheek                             
| -36 degrees to -12 degrees        | left mouth, nose base, bottom mouth, right eye, left eye, left cheek, left ear tip                  
| -12 degrees to 12 degrees         | right eye, left eye, nose base, left cheek, right cheek, left mouth, right mouth, bottom mouth          
| 12 degrees to 36 degrees          | right mouth, nose base, bottom mouth, left eye, right eye, right cheek, right ear tip             
| > 36 degrees                      | right eye, right mouth, right ear, nose base, right cheek       

#### Contours

Contours is a list of Points that represents the shape of the recognized face       

## To contribute and make it better

Clone the repo, change what you want and send PR.

Contributions are always welcome!

---

Code with ❤ by the [**Cyberlabs AI**](https://cyberlabs.ai/) Front-End Team