declare module "reactnative-multiimagepicker-android" {
    export interface SelectionOptions {
        multiple: boolean;
    }

    /**
     * React-Native multiple imagepicker module for Android.
     * pickImage method returns uri of selected image
     */
    export interface MultipleImagePicker {
        /**
         * select images, single or multiple
         * @param option set selection mode
         */
        pickImage(option: SelectionOptions) : Promise<string>;
    }

    /**
     * React-Native multiple imagepicker module for Android
     */
    const multipleImagePicker: MultipleImagePicker

    /**
     * React-Native multiple imagepicker module for Android
     */
    export default multipleImagePicker;
}