package ndk.utils_android16.update;

import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import ndk.utils_android16.network_task.update.Application_VCS_Utils;

public class Update_Application {

    public static void update_application(final String application_name, final AppCompatActivity current_activity, final float version_name, final String update_URL) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(current_activity);
        builder1.setMessage("New version is available, please update...").setCancelable(false)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Application_VCS_Utils.download_and_install_apk(application_name, version_name, update_URL, current_activity, application_name);
                    }
                });
        AlertDialog alert = builder1.create();
        alert.setTitle("Warning!");
        alert.show();
    }
}