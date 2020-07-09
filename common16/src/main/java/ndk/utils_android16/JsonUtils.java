package ndk.utils_android16;

import android.content.Context;
import android.os.Build;

import androidx.core.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import ndk.utils_android1.ErrorUtils;
import ndk.utils_android1.ExceptionUtils;
import ndk.utils_android1.LogUtils;

public class JsonUtils {

    private static List<JSONObject> sortJsonObjectListByDateField(List<JSONObject> jsonObjectList, SimpleDateFormat desiredDateFormat, String keyField) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            jsonObjectList.sort((firstJsonObject, secondJsonObject) -> {

                int compare = 0;

                try {

                    Date keyA = desiredDateFormat.parse(firstJsonObject.getString(keyField));
                    Date keyB = desiredDateFormat.parse(secondJsonObject.getString(keyField));
                    compare = Objects.requireNonNull(keyA).compareTo(keyB);

                } catch (JSONException | ParseException e) {

                    e.printStackTrace();
                }
                return compare;
            });
        }

        return jsonObjectList;
    }

    public static JSONArray sortJsonArrayInStringByUkLocaleDateField(String jsonArray, String desiredDateFormatInUkLocale, String keyField, String applicationName) {

        return sortJsonArrayInStringByDateInSimpleDateFormatField(jsonArray, new SimpleDateFormat(desiredDateFormatInUkLocale, Locale.UK), keyField, applicationName);
    }

    public static JSONArray sortJsonArrayInStringByLocalizedDateField(String jsonArray, String desiredDateFormat, Locale locale, String keyField, String applicationName) {

        return sortJsonArrayInStringByDateInSimpleDateFormatField(jsonArray, new SimpleDateFormat(desiredDateFormat, locale), keyField, applicationName);
    }

    public static JSONArray sortJsonArrayInStringByDateInSimpleDateFormatField(String JSON_array, SimpleDateFormat desired_date_format, String key_field, String applicationName) {

        return JSON_object_list_to_JSON_array(sortJsonObjectListByDateField(jsonArrayInStringToJsonObjectList(JSON_array, applicationName), desired_date_format, key_field));
    }

    public static JSONArray sortJsonArrayByDateInSimpleDateFormatField(JSONArray JSON_array, SimpleDateFormat desired_date_format, String key_field, String applicationName) {

        return JSON_object_list_to_JSON_array(sortJsonObjectListByDateField(jsonArrayToJsonObjectList(JSON_array, applicationName), desired_date_format, key_field));
    }

    private static List<JSONObject> jsonArrayInStringToJsonObjectList(String jsonArray, String applicationName) {

        try {

            return jsonArrayToJsonObjectList(new JSONArray(jsonArray), applicationName);

        } catch (JSONException e) {

            LogUtils.debug(applicationName, ExceptionUtils.getExceptionDetails(e));
            return jsonArrayToJsonObjectList(new JSONArray(), applicationName);
        }
    }

    private static List<JSONObject> jsonArrayToJsonObjectList(JSONArray jsonArray, String applicationName) {

        List<JSONObject> JSON_object_list = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {

                JSON_object_list.add(jsonArray.getJSONObject(i));
            }
        } catch (JSONException e) {

            LogUtils.debug(applicationName, ExceptionUtils.getExceptionDetails(e));
        }
        return JSON_object_list;
    }

    private static JSONArray JSON_object_list_to_JSON_array(List<JSONObject> JSON_object_list) {

        JSONArray JSON_array = new JSONArray();
        for (JSONObject json_object : JSON_object_list) {

            JSON_array.put(json_object);
        }
        return JSON_array;
    }

    public static void print_json_array(JSONArray JSON_array, String applicationName) {

        try {
            for (int i = 0; i < JSON_array.length(); i++) {

                JSONObject innerObj = JSON_array.getJSONObject(i);

                for (Iterator it = innerObj.keys(); it.hasNext(); ) {

                    String key = (String) it.next();
                    System.out.println(key + ":" + innerObj.get(key));
                }
                System.out.println("---------------------------------");
            }
        } catch (JSONException e) {

            LogUtils.debug(applicationName, ExceptionUtils.getExceptionDetails(e));
        }

    }

    private static List<JSONObject> sort_JSON_array_by_integer_field(List<JSONObject> JSON_array_list, String key_field) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            JSON_array_list.sort((first_json_object, second_json_object) -> {

                int compare = 0;

                try {
                    int first_json_object_key_value = first_json_object.getInt(key_field);
                    int second_json_object_key_value = second_json_object.getInt(key_field);
                    compare = Integer.compare(first_json_object_key_value, second_json_object_key_value);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
                return compare;
            });
        }

        return JSON_array_list;
    }

    public static JSONArray sort_JSON_array_by_integer_field(String JSON_array, String key_field, String applicationName) {

        return JSON_object_list_to_JSON_array(sort_JSON_array_by_integer_field(jsonArrayInStringToJsonObjectList(JSON_array, applicationName), key_field));
    }

    public static void jsonObjectFieldsToSharedPreferences(JSONObject jsonObject, boolean fieldsToIgnoreFlag, String[] fieldsToIgnore, Context applicationContext, String applicationName) {

        for (int i = 0; i < jsonObject.names().length(); i++) {

            try {

                LogUtils.debug(applicationName, "key = " + jsonObject.names().getString(i) + " value = " + jsonObject.get(jsonObject.names().getString(i)));

                String key = jsonObject.names().getString(i);

                if (fieldsToIgnoreFlag && Arrays.asList(fieldsToIgnore).contains(key)) {
                    continue;
                }

                SharedPreferenceUtils.commitSharedPreferences(applicationContext, applicationName, new Pair[]{new Pair<>(key, jsonObject.get(key))});

            } catch (JSONException json_exception) {

                ErrorUtils.displayException(applicationContext, json_exception, applicationName);
            }
        }
    }

    public static void jsonObjectFieldsToSharedPreferencesWithoutFieldsToIgnore(JSONObject jsonObject, Context applicationContext, String applicationName) {
        jsonObjectFieldsToSharedPreferences(jsonObject, false, new String[]{}, applicationContext, applicationName);
    }

    public static void jsonObjectFieldsToSharedPreferencesWithFieldsToIgnore(JSONObject jsonObject, String[] fieldsToIgnore, Context applicationContext, String applicationName) {
        jsonObjectFieldsToSharedPreferences(jsonObject, true, fieldsToIgnore, applicationContext, applicationName);
    }

    public static void jsonArrayToSharedPreferencesWithFieldsToIgnore(JSONArray jsonArray, String[] fieldsToIgnore, Context applicationContext, String applicationName) {

        for (int i = 0; i < jsonArray.length(); i++) {

            try {

                jsonObjectFieldsToSharedPreferencesWithFieldsToIgnore(jsonArray.getJSONObject(i), fieldsToIgnore, applicationContext, applicationName);

            } catch (JSONException jsonException) {

                ErrorUtils.displayException(applicationContext, jsonException, applicationName);
            }

        }
    }

    public static void JSON_array_to_array_list(JSONArray jsonArray, ArrayList arrayList, int start_position, Object_Utils.IGet_object iGet_object, Context context, String applicationName) {

        for (int i = start_position; i < jsonArray.length(); i++) {

            try {

                arrayList.add(iGet_object.get_object(jsonArray.getJSONObject(i)));

            } catch (JSONException e) {

                ErrorUtils.displayException(context, e, applicationName);

            }
        }
    }
}
