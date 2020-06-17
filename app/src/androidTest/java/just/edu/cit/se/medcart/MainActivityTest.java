package just.edu.cit.se.medcart;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule=new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mActivity=null;

    Instrumentation.ActivityMonitor monitor= InstrumentationRegistry.getInstrumentation().addMonitor(LogIn.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor2= InstrumentationRegistry.getInstrumentation().addMonitor(Register.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor3= InstrumentationRegistry.getInstrumentation().addMonitor(search.class.getName(),null,false);


    @Before
    public void setUp() throws Exception {
       mActivity=mActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
       mActivity=null;
    }


    @Test
    public void register() {
        assertNotNull(mActivity.findViewById(R.id.regist));
        Espresso.onView(ViewMatchers.withId(R.id.regist)).perform(ViewActions.click());

        Activity Register= InstrumentationRegistry.getInstrumentation().waitForMonitor(monitor2);
        assertNotNull(Register);
        Register.finish();
    }

    @Test
    public void login() {
        assertNotNull(mActivity.findViewById(R.id.log));
        Espresso.onView(ViewMatchers.withId(R.id.log)).perform(ViewActions.click());

        Activity login= InstrumentationRegistry.getInstrumentation().waitForMonitor(monitor);
        assertNotNull(login);
        login.finish();
    }

    @Test
    public void skip() {
        assertNotNull(mActivity.findViewById(R.id.skip));
        Espresso.onView(ViewMatchers.withId(R.id.skip)).perform(ViewActions.click());

        Activity skip= InstrumentationRegistry.getInstrumentation().waitForMonitor(monitor3);
        assertNotNull(skip);
        skip.finish();
    }
}