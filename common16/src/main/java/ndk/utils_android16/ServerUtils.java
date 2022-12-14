package ndk.utils_android16;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import ndk.utils_android1.LogUtils1;
import ndk.utils_android1.ToastUtils1;

public class ServerUtils {

    public static boolean checkSystemStatus(Context context, String systemStatus, String applicationName) {

        if (Integer.parseInt(systemStatus) == 0) {

            onMaintenanceClose((AppCompatActivity) context);

        } else if (Integer.parseInt(systemStatus) == 1) {

            LogUtils1.debug(applicationName, "System Status is OK", context);
            return true;
        }
        return false;
    }

    public static boolean checkSystemStatusWithMessage(Context context, String systemStatus, String applicationName) {

        if (checkSystemStatus(context, systemStatus, applicationName)) {

            ToastUtils1.shortToast(context, "System Status is OK");
            return true;
        }
        return false;
    }

    public static void onMaintenanceClose(AppCompatActivity activity) {

        //TODO : Close With Message on ActivityUtils
        ToastUtils1.longToast(activity, "System is in Maintenance, Try Again later...");
        activity.finish();
    }
}
