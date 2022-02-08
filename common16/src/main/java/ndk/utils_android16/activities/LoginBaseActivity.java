package ndk.utils_android16.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.core.util.Pair;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

import ndk.utils_android1.ErrorUtils;
import ndk.utils_android1.ToastUtils1;
import ndk.utils_android14.ActivityUtils14;
import ndk.utils_android14.ActivityWithContexts14;
import ndk.utils_android14.RestGetTask;
import ndk.utils_android16.BuildConfig;
import ndk.utils_android16.R;
import ndk.utils_android16.SharedPreferenceUtils16;
import ndk.utils_android16.ValidationUtils16;
import ndk.utils_android16.network_task.HttpApiSelectTask;
import ndk.utils_android16.network_task.HttpApiSelectTaskWrapper;

//TODO : Create Layout initialization

public abstract class LoginBaseActivity extends ActivityWithContexts14 {

    public ProgressBar progressBar;
    public ScrollView scrollView;

    private EditText editTextUsername;
    private EditText editTextPassword;

    public abstract String configure_SELECT_USER_URL();

    public abstract String configure_APPLICATION_NAME();

    public abstract Class configure_NEXT_ACTIVITY_CLASS();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        scrollView = findViewById(R.id.scrollView);
        progressBar = findViewById(R.id.progressBar);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        if (BuildConfig.DEBUG) {

            editTextUsername.setText(configureTestUsername());
            editTextPassword.setText(configureTestPassword());
        }

        editTextPassword.setOnEditorActionListener((textView, id, keyEvent) -> {

            if (id == EditorInfo.IME_ACTION_DONE) {

                attemptLogin();
                return true;
            }
            return false;
        });

        Button buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(view -> attemptLogin());
    }

    public abstract String configureTestPassword();

    public abstract String configureTestUsername();

    private void attemptLogin() {

        // Reset errors.
        ValidationUtils16.resetErrors(new EditText[]{editTextUsername, editTextPassword});

        ArrayList<org.javatuples.Pair<EditText, String>> editTextsWithErrorMessages = new ArrayList<>();
        editTextsWithErrorMessages.add(org.javatuples.Pair.with(editTextUsername, "Please Enter Username..."));
        editTextsWithErrorMessages.add(org.javatuples.Pair.with(editTextPassword, "Please Enter Password..."));
        org.javatuples.Pair<Boolean, EditText> nonEmptyCheckEditTextPairsResult = ValidationUtils16.nonEmptyCheckEditTextPairs(editTextsWithErrorMessages);

        if (nonEmptyCheckEditTextPairsResult.getValue0()) {

            InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            if (inputManager != null) {

                View currentFocus = getCurrentFocus();
                if (currentFocus != null) {
                    inputManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }

            performHttpApiSelectTask();

        } else {

            nonEmptyCheckEditTextPairsResult.getValue1().requestFocus();
        }
    }

    public void performHttpApiSelectTask() {

        HttpApiSelectTaskWrapper.executeGetThenReturnJsonObject(RestGetTask.prepareGetUrl(configure_SELECT_USER_URL(), configureHttpApiCallParameters()), this, progressBar, scrollView, configure_APPLICATION_NAME(), handleJsonResponseObject());
    }

    public HttpApiSelectTask.AsyncResponseJSONObject handleJsonResponseObject() {

        return jsonObject -> {

            try {
                String userCount = jsonObject.getString("user_count");

                switch (userCount) {

                    case "1":
                        SharedPreferenceUtils16.commitSharedPreferences(getApplicationContext(), configure_APPLICATION_NAME(), new Pair[]{new Pair<>("user_id", jsonObject.getString("id"))});
                        ActivityUtils14.startActivityForClassWithFinish(currentActivityContext, configure_NEXT_ACTIVITY_CLASS());
                        break;

                    case "0":
                        ToastUtils1.longToast(currentActivityContext, "Login Failure!");
                        editTextUsername.requestFocus();
                        break;

                    default:
                        ErrorUtils.displayJSONFieldMiss(currentActivityContext, jsonObject, configure_APPLICATION_NAME());
                }

            } catch (JSONException e) {

                ErrorUtils.displayException(currentActivityContext, e, configure_APPLICATION_NAME());
            }
        };
    }

    public Pair[] configureHttpApiCallParameters() {

        return new Pair[]{new Pair<>("username", editTextUsername.getText().toString()), new Pair<>("password", editTextPassword.getText().toString())};
    }
}
