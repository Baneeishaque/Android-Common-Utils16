package ndk.util.tests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class Splash_Version_OK_Test {

    //TODO : Check Network Response
    //TODO : Check Next Screen
    //TODO : Check Alert Screen
    //TODO : Check Download Request

    @Rule
    public ActivityTestRule<Splash_Version_OK> activity_Test_Rule = new ActivityTestRule<>(Splash_Version_OK.class);

    @Test
    public void check_network_response() {
    }

}