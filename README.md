<img src="https://raw.githubusercontent.com/Yoonit-Labs/android-yoonit-camera/development/logo_cyberlabs.png" width="300">

# android-yoonit-facefy

A Android library to provide:
- [Standart Google ML Kit](https://developers.google.com/ml-kit)
- Face detection (With Min & Max size)
- Face contours detection
- Face expression detection

## API

### Methods

| Function                     | Parameters                                      | Valid values                  | Return Type | Description
| -                            | -                                               | -                             | -           | -
| detect                       | `image: InputImage`                             | -                             | void        | Start face detection from from image
| setFaceClassification        | `enabled: Boolean`                              | -                             | void        | Set face classification enabled/disabled.
| setFaceContours              | `enabled: Boolean`                              | -                             | void        | Set face contours detection enabled/disabled.
| setFaceBoundingBox           | `enabled: Boolean`                              | -                             | void        | Set face bounding box detection enabled/disabled.

### Events

| Event              | Parameters                                                                                                                                                          | Description
| -                  | -                                                                                                                                                                   | -
| onFaceAnalysis     | `leftEyeOpenProbability: Float, rightEyeOpenProbability: Float, smilingProbability: Float, headEulerAngleX: Float, headEulerAngleY: Float, headEulerAngleZ: Float`  | Must have started detect (see `detect`). Emit the detected eyes open probability
| onContours         | `faceContours: MutableList<PointF>`                                                                                                                                 | Must have started detect. Emit the detected contours.
| onFace             | `x: Int, y: Int, width: Int, height: Int`                                                                                                                           | Must have started detect. Emit the detected face bounding box.
