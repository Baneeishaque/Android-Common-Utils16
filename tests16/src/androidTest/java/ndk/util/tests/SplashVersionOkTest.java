package ndk.util.tests;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will executePostThenReturnJsonObject on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class SplashVersionOkTest {

    //TODO : Application_Utils Network Response
    //TODO : Application_Utils Next Screen
    //TODO : Application_Utils Alert Screen
    //TODO : Application_Utils Download Request

    @Rule
    public ActivityTestRule<Splash_Version_OK> activity_Test_Rule = new ActivityTestRule<>(Splash_Version_OK.class);

    @Test
    public void check_network_response() {
    }

}
