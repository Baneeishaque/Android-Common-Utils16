package ndk.utils_android16;

import android.content.Context;

import org.json.JSONObject;

public class ErrorUtilsWrapperBase {

    private static String applicationName;

    public ErrorUtilsWrapperBase(String applicationName) {

        ErrorUtilsWrapperBase.applicationName = applicationName;
    }

    public static void displayException(Context context, Exception exception) {

        ErrorUtils.displayException(context, exception, applicationName);
    }

    public static void displayJSONFieldMiss(Context context, JSONObject jsonObject) {

        ErrorUtils.displayJSONFieldMiss(context, jsonObject, applicationName);
    }
}
