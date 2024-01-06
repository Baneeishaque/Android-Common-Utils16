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

    public static boolean isFirstRun(Context context, String applicationName, FirstRunActions firstRunActions) {
        return isFirstRun(context.getSharedPreferences(applicationName, Context.MODE_PRIVATE), firstRunActions);
    }

    public static boolean isFirstRun(SharedPreferences sharedPreferences, FirstRunActions firstRunActions) {

        String isFirstRunKey = "isFirstRun";
        if (sharedPreferences.getString(isFirstRunKey, String.valueOf(true)).equals(String.valueOf(true))) {

            firstRunActions.onFirstRun();

            commitSharedPreferences(sharedPreferences, new PairOfStrings[]{new PairOfStrings(isFirstRunKey, String.valueOf(false))});
            return true;
        }
        return false;
    }

    public interface FirstRunActions {

        void onFirstRun();
    }
}

class PairOfStrings extends Pair<String, String> {

    /**
     * Constructor for a Pair.
     *
     * @param first  the first object in the Pair
     * @param second the second object in the pair
     */
    public PairOfStrings(String first, String second) {
        super(first, second);
    }
}
