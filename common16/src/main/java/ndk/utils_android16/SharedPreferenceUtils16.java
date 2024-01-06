package ndk.utils_android16;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.core.util.Pair;

import ndk.utils_android1.SharedPreferencesUtils1;
import ndk.utils_android9.SharedPreferencesUtils9;

public class SharedPreferenceUtils16 extends SharedPreferencesUtils9 {

    public static boolean commitSharedPreferences(Context applicationContext, String applicationName, Pair<String, String>[] sharedPreferencePairs) {

        return commitSharedPreferences(SharedPreferencesUtils1.getSharedPreferences(applicationContext, applicationName), sharedPreferencePairs);
    }

    public static boolean commitSharedPreferences(SharedPreferences sharedPreferences, Pair<String, String>[] sharedPreferencePairs) {

        if (sharedPreferencePairs.length != 0) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            for (Pair<String, String> shared_preference_pair : sharedPreferencePairs) {

                editor.putString(shared_preference_pair.first != null ? shared_preference_pair.first : null, shared_preference_pair.second != null ? shared_preference_pair.second : null);
            }
            return editor.commit();
        }
        return true;
    }

    public interface FirstRunActions {

        void onFirstRun();
    }
}
