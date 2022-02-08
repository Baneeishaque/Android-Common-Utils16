package ndk.utils_android16.activities;

import ndk.utils_android1.ErrorUtils;
import ndk.utils_android16.constants.IntentExtraFields;

public class LoginBundleActivity extends LoginBaseActivity {

    @Override
    public String configure_SELECT_USER_URL() {

        return getIntent().getStringExtra(IntentExtraFields.SELECT_USER_URL);
    }

    @Override
    public String configure_APPLICATION_NAME() {

        return getIntent().getStringExtra(IntentExtraFields.APPLICATION_NAME);
    }

    @Override
    public Class configure_NEXT_ACTIVITY_CLASS() {

        try {

            return Class.forName(getIntent().getStringExtra(IntentExtraFields.NEXT_ACTIVITY_CLASS));

        } catch (ClassNotFoundException e) {

            ErrorUtils.displayException(this, e, configure_APPLICATION_NAME());
            return null;
        }
    }

    @Override
    public String configureTestUsername() {

        return getIntent().getStringExtra(IntentExtraFields.TEST_USERNAME);
    }

    @Override
    public String configureTestPassword() {

        return getIntent().getStringExtra(IntentExtraFields.TEST_PASSWORD);
    }
}
