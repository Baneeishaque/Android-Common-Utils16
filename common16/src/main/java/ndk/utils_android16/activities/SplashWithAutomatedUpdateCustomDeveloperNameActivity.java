package ndk.utils_android16.activities;

import android.widget.TextView;

public abstract class SplashWithAutomatedUpdateCustomDeveloperNameActivity extends SplashWithAutomatedUpdateActivity {

    protected abstract String configure_DEVELOPER_NAME();

    @Override
    public void initializeScreen() {
        super.initializeScreen();

        TextView text_developer = findViewById(ndk.utils_android14.R.id.text_Developer);
        text_developer.setText(configure_DEVELOPER_NAME());
    }
}
