package ndk.utils_android16;

import android.content.Context;

import ndk.utils_android1.UpdateUtils;

public class UpdateUtilsWrapperBase {

    private static String applicationName;

    UpdateUtilsWrapperBase(String applicationName) {
        UpdateUtilsWrapperBase.applicationName = applicationName;
    }

    public static String[] getFlavouredServerVersion(String flavour, String fullVersionCheckUrl, Context currentApplicationContext) {
        return UpdateUtils.getFlavouredServerVersion(flavour, fullVersionCheckUrl, applicationName, currentApplicationContext);
    }

    public static String[] getServerVersion(String fullVersionCheckUrl, Context currentApplicationContext) {
        return UpdateUtils.getServerVersion(fullVersionCheckUrl, applicationName, currentApplicationContext);
    }
}
