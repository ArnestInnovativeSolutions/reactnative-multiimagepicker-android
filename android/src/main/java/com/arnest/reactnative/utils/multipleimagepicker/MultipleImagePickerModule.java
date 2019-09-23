package com.arnest.reactnative.utils.multipleimagepicker;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.util.Log;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.PromiseImpl;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class MultipleImagePickerModule extends ReactContextBaseJavaModule {

  private static final int IMAGE_PICKER_REQUEST = 61110;
  private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";
  private static final String E_PICKER_CANCELLED = "E_PICKER_CANCELLED";
  private static final String E_FAILED_TO_SHOW_PICKER = "E_FAILED_TO_SHOW_PICKER";
  private static final String E_NO_IMAGE_DATA_FOUND = "E_NO_IMAGE_DATA_FOUND";

  private Promise mPickerPromise;
  private boolean multiple = false;

  private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
      if (requestCode == IMAGE_PICKER_REQUEST) {
        if (mPickerPromise != null) {
          if (resultCode == Activity.RESULT_CANCELED) {
            mPickerPromise.reject(E_PICKER_CANCELLED, "Image picker was cancelled");
          } else if (resultCode == Activity.RESULT_OK) {
            Uri uri = intent.getData();
            if (uri == null) {
              mPickerPromise.reject(E_NO_IMAGE_DATA_FOUND, "No image data found");
            } else {
              ClipData clipData = intent.getClipData();
              WritableArray imageUriArray=Arguments.createArray();
              for (int i = 0; i < clipData.getItemCount(); i++) {
                imageUriArray.pushString(clipData.getItemAt(i).getUri().toString());
              }
              mPickerPromise.resolve(imageUriArray);
            }
          }

          mPickerPromise = null;
        }
      }
    }
  };

  MultipleImagePickerModule(ReactApplicationContext reactContext) {
    super(reactContext);

    // Add the listener for `onActivityResult`
    reactContext.addActivityEventListener(mActivityEventListener);
  }

  @Override
  public String getName() {
    return "MultipleImagePicker";
  }

  @ReactMethod
  public void pickImage(final ReadableMap options,final Promise promise) {
    Activity currentActivity = getCurrentActivity();
    multiple = options.hasKey("multiple") && options.getBoolean("multiple");

    if (currentActivity == null) {
      promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
      return;
    }

    // Store the promise to resolve/reject when picker returns data
    mPickerPromise = promise;

    try {
      final Intent galleryIntent = new Intent(Intent.ACTION_PICK);

      galleryIntent.setType("image/*");
      galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, multiple);
      final Intent chooserIntent = Intent.createChooser(galleryIntent, "Pick an image");
      
      currentActivity.startActivityForResult(chooserIntent, IMAGE_PICKER_REQUEST);
    } catch (Exception e) {
      mPickerPromise.reject(E_FAILED_TO_SHOW_PICKER, e);
      mPickerPromise = null;
    }
  }
}