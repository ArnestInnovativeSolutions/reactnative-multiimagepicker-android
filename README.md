## reactnative-multiimagepicker-android
###### React-Native MultipleImagePicker module for Android.

## Getting Started

### Installation
###### $ npm install reactnative-multiimagepicker-android --save

### Autolinking (>= 0.60)
###### react-native link reactnative-multiimagepicker-android

## API
```pickImage(option: SelectionOptions) : Promise<string>``` - returns uri of images selected
```SelectionOptions {
        multiple: boolean;
      }
 ```
## Example
```
MultipleImagePicker.pickImage(
{
  multiple: true
}).then(images => {
  alert(JSON.stringify(images));
}).catch((error) => {
   alert(error)
})
```



