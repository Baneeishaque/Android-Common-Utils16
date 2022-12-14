package ndk.utils_android16.update;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import ndk.utils_android1.DebugUtils;
import ndk.utils_android1.NetworkUtils1;
import ndk.utils_android16.network_task.update.CheckAndUpdateTask;

import static ndk.utils_android14.NetworkUtils14.displayOfflineLongNoFabBottomSnackBar;

public class Check {

    public static void attempt_Update_Check(final String application_name, final AppCompatActivity current_activity, final String URL, CheckAndUpdateTask Update_Task, final String update_URL, final Class next_activity) {

        if (Update_Task != null) {
            return;
        }

        if (NetworkUtils1.isOnline(current_activity)) {

            Update_Task = new CheckAndUpdateTask(application_name, current_activity, URL, update_URL, next_activity, DebugUtils.isDebugBuild(current_activity), false, 0, new Pair[]{});
            Update_Task.execute();

        } else {

            final CheckAndUpdateTask final_Update_Task = Update_Task;
            View.OnClickListener retry_Failed_Network_Task = view -> attempt_Update_Check(application_name, current_activity, URL, final_Update_Task, update_URL, next_activity);
            displayOfflineLongNoFabBottomSnackBar(current_activity.getWindow().getDecorView(), retry_Failed_Network_Task);
        }
    }
}
