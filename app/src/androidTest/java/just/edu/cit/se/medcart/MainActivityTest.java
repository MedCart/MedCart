package just.edu.cit.se.medcart;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule=new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mActivity=null;

    Instrumentation.ActivityMonitor monitor=getInstrumentation().addMonitor(LogIn.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor2=getInstrumentation().addMonitor(Register.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor3=getInstrumentation().addMonitor(search.class.getName(),null,false);


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
        onView(withId(R.id.regist)).perform(click());

        Activity Register=getInstrumentation().waitForMonitor(monitor2);
        assertNotNull(Register);
        Register.finish();
    }

    @Test
    public void login() {
        assertNotNull(mActivity.findViewById(R.id.log));
        onView(withId(R.id.log)).perform(click());

        Activity login=getInstrumentation().waitForMonitor(monitor);
        assertNotNull(login);
        login.finish();
    }

    @Test
    public void skip() {
        assertNotNull(mActivity.findViewById(R.id.skip));
        onView(withId(R.id.skip)).perform(click());

        Activity skip=getInstrumentation().waitForMonitor(monitor3);
        assertNotNull(skip);
        skip.finish();
    }
}