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

public class orderInfoTest {

    @Rule
    public ActivityTestRule<orderInfo> oActivityTestRule=new ActivityTestRule<orderInfo>(orderInfo.class);
    private orderInfo oActivity=null;
    Instrumentation.ActivityMonitor monitor= InstrumentationRegistry.getInstrumentation().addMonitor(UserLocation.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor1= InstrumentationRegistry.getInstrumentation().addMonitor(search.class.getName(),null,false);

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
        Espresso.onView(ViewMatchers.withId(R.id.location)).perform(ViewActions.click());
        Activity location= InstrumentationRegistry.getInstrumentation().waitForMonitor(monitor);
        assertNotNull(location);
        location.finish();
    }

    @Test
    public void confirm(){
        assertNotNull(oActivity.findViewById(R.id.confrim));
        Espresso.onView(ViewMatchers.withId(R.id.confrim)).perform(ViewActions.click());

        Activity confirm= InstrumentationRegistry.getInstrumentation().waitForMonitor(monitor1);
        assertNotNull(confirm);
    }
}