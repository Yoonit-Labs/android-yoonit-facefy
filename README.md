<h1 align="center">Android Yoonit Facefy</h1>

<p align="center">
	<img src="https://img.shields.io/github/v/tag/Yoonit-Labs/android-yoonit-facefy?color=lightgrey&label=version&style=for-the-badge"/>
	<img src="https://img.shields.io/github/license/Yoonit-Labs/android-yoonit-facefy?color=lightgrey&style=for-the-badge"/>
</p>

<div align="center">
	<table>
		<tbody>
			<tr>
				<td align="left" valign="middle">
					<p>◻ <a href="https://developers.google.com/ml-kit">Google MLKit</a> integration</p>
					<p>◻ <a href="https://pytorch.org/mobile/home">PyTorch</a> integration</p>
					<p>◻ Computer vision pipeline</p>
					<p>◻ Face detection</p>
					<p>◻ Face contours</p>
					<p>◻ Face expressions</p>
					<p>◻ Face movement</p>
				</td>
				<td align="center" valign="middle">
					<img src="https://raw.githubusercontent.com/Yoonit-Labs/android-yoonit-facefy/main/facefy.gif" width="300" />
				</td>
			</tr>
		<tbody>
	</table>
</div>

<div align="center">
	<h3>Sponsors</h3>
	<table>
		<tbody>
			<tr>
				<td>
					<b>Platinum</b>
				</td>
			</tr>
			<tr>
				<td align="center" valign="middle">
					<a href="https://cyberlabs.ai"><img src="https://raw.githubusercontent.com/Yoonit-Labs/android-yoonit-facefy/development/sponsor_cyberlabs.png" width="300" /></a>
				</td>
			</tr>
		</tbody>
	</table>
	<h5><a href="mailto:about@yoonit.dev">Become a sponsor!</a></h5>
</div>

## Table of Contents

* [Installation](#installation)
* [Usage](#usage)
* [API](#api)
  * [Methods](#methods)
  * [FaceDetected](#facedetected)
    * [Head Movements](#head-movements)
* [To contribute and make it better](#to-contribute-and-make-it-better)

## Installation
  
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

    fun example(inputBitmap: Bitmap) {
        Facefy()
            .detect(
                inputBitmap,
                { faceDetected ->
                    val boundingBox: Rect = faceDetected.boundingBox
                    val contours: MutableList<PointF> = faceDetected.contours
                    val headEulerAngleX: Float = faceDetected.headEulerAngleX
                    val headEulerAngleY: Float = faceDetected.headEulerAngleY
                    val headEulerAngleZ: Float = faceDetected.headEulerAngleZ
                    val leftEyeOpenProbability: Float? = faceDetected.leftEyeOpenProbability
                    val rightEyeOpenProbability: Float? = faceDetected.rightEyeOpenProbability
                    val smilingProbability: Float? = faceDetected.smilingProbability
                },
                { errorMessage ->
                    val mesage: String = errorMessage
                },
                {
                    // Process completed.
                }       
            )
    }
```

## API

### Methods

| Function | Parameters                                                                          | Return Type | Description                                                                                          |
| -        | -                                                                                   | -           | -                                                                                                    |
| detect   | `inputBitmap: Bitmap, onSuccess: (FaceDetected) -> Unit, onError: (String) -> Unit` | void        | Detect a face from bitmap and return the result in the [`FaceDetected`](#facedetected) as a closure. |

### FaceDetected

| Attribute               | Type                | Description                                                                                                               |
| -                       | -                   | -                                                                                                                         |
| leftEyeOpenProbability  | Float?              | The left eye open probability.                                                                                            |
| rightEyeOpenProbability | Float?              | The right eye open probability.                                                                                           |
| smilingProbability      | Float?              | The smiling probability.                                                                                                  |
| headEulerAngleX         | Float               | The angle that points the rotation of the face about the horizontal axis of the image. [HeadMovements](#head-movements)   |
| headEulerAngleY         | Float               | The angle that points the "left-right" head direction. See [HeadMovements](#head-movements)                               |
| headEulerAngleZ         | Float               | The angle that points the rotation of the face about the axis pointing out of the image. [HeadMovements](#head-movements) |
| contours                | Mutablelist<PointF> | List of Points that represents the shape of the recognized face.                                                          |
| boundingBox             | Rect                | The face bounding box.                                                                                                    |

#### Head Movements

Here we explaining the above gif and how reached the "results". Each "movement" (vertical, horizontal and tilt) is a state, based in the angle in degrees that indicate head direction;

| Head Direction | Attribute         |  _v_ < -36°           | -36° < _v_ < -12° | -12° < _v_ < 12° | 12° < _v_ < 36° |  36° < _v_    | 
| -              | -                 | -                     | -                 | -                | -               | -             |
| Vertical       | `headEulerAngleX` | Super Down            | Down              | Frontal          | Up              | Super Up      |            
| Horizontal     | `headEulerAngleY` | Super Left            | Left              | Frontal          | Right           | Super Right   |
| Tilt           | `headEulerAngleZ` | Super Right           | Right             | Frontal          | Left            | Super Left    |

## To contribute and make it better

Clone the repo, change what you want and send PR.
For commit messages we use <a href="https://www.conventionalcommits.org/">Conventional Commits</a>.

Contributions are always welcome!

<a href="https://github.com/Yoonit-Labs/android-yoonit-facefy/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=Yoonit-Labs/android-yoonit-facefy" />
</a>

---

Code with ❤ by the [**Yoonit**](https://yoonit.dev/) Team
