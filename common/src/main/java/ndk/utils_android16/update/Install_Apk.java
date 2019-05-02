package ndk.utils_android16.update;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Nabeel on 21-01-2018.
 */

public class Install_Apk {
    public static void install_apk(Uri uri, DownloadManager manager, long downloadId, Context context, BroadcastReceiver download_complete_trigger) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        install.setDataAndType(uri, manager.getMimeTypeForDownloadedFile(downloadId));
        context.startActivity(install);
        context.unregisterReceiver(download_complete_trigger);
        ((AppCompatActivity) context).finish();
    }
}
