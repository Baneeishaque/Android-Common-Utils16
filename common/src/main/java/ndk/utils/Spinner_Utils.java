package ndk.utils;

import android.content.Context;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import ndk.utils.network_task.REST_Select_Task;

import static ndk.utils.Network_Utils.isOnline;
import static ndk.utils.ProgressBar_Utils.showProgress;

/**
 * Created on 03-12-2017 10:35 under Caventa_Android.
 */
public class Spinner_Utils {
    private static REST_Select_Task REST_select_task = null;

    public static void attach_items_to_simple_spinner(Context context, Spinner spinner, ArrayList<String> items) {
        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, items);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
    }

    public static void populate_spinner_from_json_array(int start_index, JSONArray json_array, Context context, String application_name, Spinner spinner, ArrayList<String> spinner_items, String key) {
        for (int i = start_index; i < json_array.length(); i++) {

            try {
                spinner_items.add(json_array.getJSONObject(i).getString(key));
            } catch (JSONException e) {
                Toast.makeText(context, "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d(application_name, e.getLocalizedMessage());
            }

        }
        Spinner_Utils.attach_items_to_simple_spinner(context, spinner, spinner_items);
    }

    public static void get_json_from_network_and_populate(final Context context, View progress_Bar, View form, String URL, final String application_name, final int start_index, final Spinner spinner, final ArrayList<String> spinner_items, final String key) {
//        if (REST_select_task != null) {
//            REST_select_task.cancel(true);
//            REST_select_task = null;
//        }

        if (isOnline(context)) {
            showProgress(true, context, progress_Bar, form);
            REST_select_task = new REST_Select_Task(URL, REST_select_task, context, progress_Bar, form, application_name, new Pair[]{}, new REST_Select_Task.AsyncResponse() {

                @Override
                public void processFinish(JSONArray json_array) {
                    populate_spinner_from_json_array(start_index, json_array, context, application_name, spinner, spinner_items, key);
                }

            });
            REST_select_task.execute((Void) null);
        } else {
            Toast_Utils.longToast(context, "Internet is unavailable");
        }
    }
}
