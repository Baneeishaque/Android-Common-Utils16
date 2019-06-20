package ndk.utils_android16.network_task;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import ndk.utils_android16.ProgressBarUtils;
import ndk.utils_android16.ToastUtils;

import static ndk.utils_android16.NetworkUtils.further_Actions;
import static ndk.utils_android16.NetworkUtils.isOnline;

public class REST_Insert_Task_Wrapper {

    public static void execute(Context context, String task_URL, AppCompatActivity current_activity, View mProgressView, View mLoginFormView, String APPLICATION_NAME, Pair[] name_value_pairs, View view_to_focus_on_error, Class next_activity) {

        Log.d(APPLICATION_NAME, "REST Insert TASK URL : " + task_URL);

        if (isOnline(context)) {

            ProgressBarUtils.showProgress(true, context, mProgressView, mLoginFormView);

            REST_Insert_Task rest_insert_task = new REST_Insert_Task(task_URL, current_activity, mProgressView, mLoginFormView, APPLICATION_NAME, name_value_pairs, view_to_focus_on_error, next_activity);

            rest_insert_task.execute();
        } else {
            ToastUtils.longToast(context, "Internet is unavailable");
        }
    }

    public static void execute(Context context, String task_URL, AppCompatActivity current_activity, View mProgressView, View mLoginFormView, String APPLICATION_NAME, Pair[] name_value_pairs, View view_to_focus_on_error, Class next_activity, Pair[] next_class_extras) {

        Log.d(APPLICATION_NAME, "REST Insert TASK URL : " + task_URL);

        if (isOnline(context)) {

            ProgressBarUtils.showProgress(true, context, mProgressView, mLoginFormView);

            REST_Insert_Task rest_insert_task = new REST_Insert_Task(task_URL, current_activity, mProgressView, mLoginFormView, APPLICATION_NAME, name_value_pairs, view_to_focus_on_error, next_activity, next_class_extras);

            rest_insert_task.execute();
        } else {
            ToastUtils.longToast(context, "Internet is unavailable");
        }
    }

    public static void execute(Context context, String task_URL, AppCompatActivity current_activity, View mProgressView, View mLoginFormView, String APPLICATION_NAME, Pair[] name_value_pairs, View view_to_focus_on_error, EditText[] texts_to_clear) {

        Log.d(APPLICATION_NAME, "REST Insert TASK URL : " + task_URL);

        if (isOnline(context)) {

            ProgressBarUtils.showProgress(true, context, mProgressView, mLoginFormView);

            REST_Insert_Task rest_insert_task = new REST_Insert_Task(task_URL, current_activity, mProgressView, mLoginFormView, APPLICATION_NAME, name_value_pairs, view_to_focus_on_error, texts_to_clear);

            rest_insert_task.execute();
        } else {
            ToastUtils.longToast(context, "Internet is unavailable");
        }
    }

    public static void execute(Context context, String task_URL, AppCompatActivity current_activity, View mProgressView, View mLoginFormView, String APPLICATION_NAME, Pair[] name_value_pairs, View view_to_focus_on_error, EditText[] texts_to_clear, further_Actions further_actions) {

        Log.d(APPLICATION_NAME, "REST Insert TASK URL : " + task_URL);

        if (isOnline(context)) {

            ProgressBarUtils.showProgress(true, context, mProgressView, mLoginFormView);

            REST_Insert_Task rest_insert_task = new REST_Insert_Task(task_URL, current_activity, mProgressView, mLoginFormView, APPLICATION_NAME, name_value_pairs, view_to_focus_on_error, texts_to_clear, further_actions);

            rest_insert_task.execute();
        } else {
            ToastUtils.longToast(context, "Internet is unavailable");
        }
    }

    public static void execute(Context context, String task_URL, AppCompatActivity current_activity, View mProgressView, View mLoginFormView, String APPLICATION_NAME, Pair[] name_value_pairs, View view_to_focus_on_error) {

        Log.d(APPLICATION_NAME, "REST Insert TASK URL : " + task_URL);

        if (isOnline(context)) {

            ProgressBarUtils.showProgress(true, context, mProgressView, mLoginFormView);

            REST_Insert_Task rest_insert_task = new REST_Insert_Task(task_URL, current_activity, mProgressView, mLoginFormView, APPLICATION_NAME, name_value_pairs, view_to_focus_on_error);

            rest_insert_task.execute();
        } else {
            ToastUtils.longToast(context, "Internet is unavailable");
        }
    }

    public static void execute(Context context, String task_URL, AppCompatActivity current_activity, View mProgressView, View mLoginFormView, String APPLICATION_NAME, Pair[] name_value_pairs, View view_to_focus_on_error, further_Actions further_actions) {

        Log.d(APPLICATION_NAME, "REST Insert TASK URL : " + task_URL);

        if (isOnline(context)) {

            ProgressBarUtils.showProgress(true, context, mProgressView, mLoginFormView);

            REST_Insert_Task rest_insert_task = new REST_Insert_Task(task_URL, current_activity, mProgressView, mLoginFormView, APPLICATION_NAME, name_value_pairs, view_to_focus_on_error, further_actions);

            rest_insert_task.execute();
        } else {
            ToastUtils.longToast(context, "Internet is unavailable");
        }
    }
}
