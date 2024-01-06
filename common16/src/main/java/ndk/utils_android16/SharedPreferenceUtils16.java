package ndk.utils_android16;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.core.util.Pair;

import ndk.utils_android1.SharedPreferencesUtils1;
import ndk.utils_android9.SharedPreferencesUtils9;

public class SharedPreferenceUtils16 extends SharedPreferencesUtils9 {

    public static void commitSharedPreferences(Context applicationContext, String applicationName, Pair[] sharedPreferencePairs) {

        commitSharedPreferences(SharedPreferencesUtils1.getSharedPreferences(applicationContext, applicationName), sharedPreferencePairs);
    }

    public static boolean commitSharedPreferences(SharedPreferences sharedPreferences, Pair[] sharedPreferencePairs) {

        if (sharedPreferencePairs.length != 0) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            for (Pair shared_preference_pair : sharedPreferencePairs) {

                editor.putString(shared_preference_pair.first != null ? shared_preference_pair.first.toString() : null, shared_preference_pair.second != null ? shared_preference_pair.second.toString() : null);
            }
            return editor.commit();
        }
        return true;
    }

    public static boolean isFirstRun(Context context, String applicationName, FirstRunActions firstRunActions) {

        SharedPreferences settings = context.getSharedPreferences(applicationName, Context.MODE_PRIVATE);

        if (settings.getString("first_run", String.valueOf(true)).equals(String.valueOf(true))) {

            // Do first run stuff here then set 'first_run' as false
            firstRunActions.onFirstRun();

            commitSharedPreferences(context, applicationName, new Pair[]{new Pair<>("first_run", String.valueOf(false))});
            return true;
        }
        return false;
    }

    public static boolean isFirstRun(SharedPreferences sharedPreferences, FirstRunActions firstRunActions) {

        String isFirstRunKey = "isFirstRun";
        if (sharedPreferences.getString(isFirstRunKey, String.valueOf(true)).equals(String.valueOf(true))) {

            // Do first run stuff here then set 'isFirstRun' as false
            firstRunActions.onFirstRun();

            commitSharedPreferences(sharedPreferences, new Pair[]{new Pair<>(isFirstRunKey, String.valueOf(false))});
            return true;
        }
        return false;
    }

    public interface FirstRunActions {

        void onFirstRun();
    }
}
