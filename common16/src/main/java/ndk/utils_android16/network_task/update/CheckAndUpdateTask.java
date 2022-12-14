package ndk.utils_android16.network_task.update;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ndk.utils_android1.ErrorUtils;
import ndk.utils_android1.LogUtils1;
import ndk.utils_android1.NetworkUtils1;
import ndk.utils_android1.ToastUtils1;
import ndk.utils_android1.UpdateUtils;
import ndk.utils_android14.ActivityUtils14;
import ndk.utils_android16.ServerUtils;

import static ndk.utils_android1.NetworkUtils1.displayFriendlyExceptionMessage;
import static ndk.utils_android16.update.UpdateApplication.updateApplication;

public class CheckAndUpdateTask extends AsyncTask<Void, Void, String[]> {

    private AppCompatActivity currentActivity;
    private Class nextActivity;
    private String URL, applicationName, updateUrl;
    private boolean securityFlag, tabIndexFlag;
    private int tabIndex;
    private Pair[] nextActivityExtras;

    public CheckAndUpdateTask(String applicationName, AppCompatActivity currentActivity, String URL, String updateUrl, Class nextActivity, boolean securityFlag, boolean tabIndexFlag, int tabIndex, Pair[] nextActivityExtras) {

        this.currentActivity = currentActivity;
        this.URL = URL;
        this.applicationName = applicationName;
        this.updateUrl = updateUrl;
        this.nextActivity = nextActivity;
        this.securityFlag = securityFlag;
        this.tabIndexFlag = tabIndexFlag;
        this.tabIndex = tabIndex;
        this.nextActivityExtras = nextActivityExtras;
    }

    @Override
    protected String[] doInBackground(Void... params) {

        return UpdateUtils.getServerVersion(URL, applicationName, currentActivity);
    }

    @Override
    protected void onPostExecute(final String[] networkActionResponseArray) {

        NetworkUtils1.displayNetworkActionResponse(applicationName, networkActionResponseArray, currentActivity);

        if (networkActionResponseArray[0].equals("1")) {

            displayFriendlyExceptionMessage(currentActivity, networkActionResponseArray[1]);
            currentActivity.finish();

        } else {

            try {

                JSONArray jsonArray = new JSONArray(networkActionResponseArray[1]);
                checkAndUpdate(jsonArray);

            } catch (JSONException e) {

                ErrorUtils.displayException(currentActivity, e, applicationName);
            }
        }
    }

    public void checkAndUpdate(JSONArray jsonArray) {

        try {
            JSONObject tempJsonObject = jsonArray.getJSONObject(0);

            if (ServerUtils.checkSystemStatus(currentActivity, tempJsonObject.getString("system_status"), applicationName)) {

                if (Integer.parseInt(tempJsonObject.getString("version_code")) != UpdateUtils.getVersionCode(currentActivity) || Float.parseFloat(tempJsonObject.getString("version_name")) != UpdateUtils.getVersionName(currentActivity)) {

                    updateApplication(applicationName, currentActivity, Float.parseFloat(tempJsonObject.getString("version_name")), updateUrl);

                } else {

                    LogUtils1.debug(applicationName, "Latest Version...", currentActivity);

                    if (!securityFlag) {

                        ToastUtils1.shortToast(currentActivity, "Latest Version...");
                    }
                    // After completing http call will close this activity and launch main activity
                    if (tabIndexFlag) {

                        //TODO : Tab Index with Other extras
                        ActivityUtils14.startActivityWithFinishAndTabIndex(currentActivity, nextActivity, tabIndex);

                    } else {

                        if (nextActivityExtras.length == 0) {

                            ActivityUtils14.startActivityForClassWithFinish(currentActivity, nextActivity);

                        } else {

                            ActivityUtils14.startActivityWithStringExtrasAndFinish(currentActivity, nextActivity, nextActivityExtras);
                        }
                    }
                }
            }
        } catch (JSONException e) {

            ErrorUtils.displayException(currentActivity, e, applicationName);
        }
    }
}
