<img src="https://raw.githubusercontent.com/Yoonit-Labs/android-yoonit-camera/development/logo_cyberlabs.png" width="300">

# android-yoonit-facefy

A Android library to provide:
- [Standart Google ML Kit](https://developers.google.com/ml-kit)
- Face detection (With Min & Max size)
- Face contours detection
- Face expression detection

## API

### Methods

| Function                     | Parameters                                                  | Valid values  | Return Type | Description
| -                            | -                                                           | -             | -           | -
| detect                       | `image: InputImage, onFaceDetected: (DetectedFace) -> Unit` | -             | void        | Start face detection from from image
| setFaceClassification        | `enabled: Boolean`                                          | -             | void        | Set face classification enabled/disabled.
| setFaceContours              | `enabled: Boolean`                                          | -             | void        | Set face contours detection enabled/disabled.
| setFaceBoundingBox           | `enabled: Boolean`                                          | -             | void        | Set face bounding box detection enabled/disabled.                                                                                                                      | Must have started detect. Emit the detected face bounding box.

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