package just.edu.cit.se.medcart;

import android.app.Activity;
import android.app.Instrumentation;

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

public class orderInfoTest {

    @Rule
    public ActivityTestRule<orderInfo> oActivityTestRule=new ActivityTestRule<orderInfo>(orderInfo.class);
    private orderInfo oActivity=null;
    Instrumentation.ActivityMonitor monitor=getInstrumentation().addMonitor(UserLocation.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor1=getInstrumentation().addMonitor(search.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        oActivity=oActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        oActivity=null;
    }

    @Test
    public void location(){
        assertNotNull(oActivity.findViewById(R.id.location));
        onView(withId(R.id.location)).perform(click());

        Activity location=getInstrumentation().waitForMonitor(monitor);
        assertNotNull(location);
        location.finish();
    }

    @Test
    public void confirm(){
        assertNotNull(oActivity.findViewById(R.id.confrim));
        onView(withId(R.id.confrim)).perform(click());

        Activity confirm=getInstrumentation().waitForMonitor(monitor1);
        assertNotNull(confirm);
    }
}