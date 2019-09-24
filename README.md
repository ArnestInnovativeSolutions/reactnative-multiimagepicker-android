reactnative-multiimagepicker-android
React-Native MultipleImagePicker module for Android.

Installation
$ npm install reactnative-multiimagepicker-android --save

Mostly automatic installation (react-native 0.59 and lower)
react-native link @reactnative-multiimagepicker-android

Permissions - Android
Add the required permissions in AndroidManifest.xml:

<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

Usage
MultipleImagePicker.pickImage(
{
    multiple:true
}
).then(images => {
let imagesUri = images;   
}).catch((error) => {
    alert(error)
})

