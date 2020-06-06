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

public class searchTest {

    @Rule
    public ActivityTestRule<search> sActivityTestRule=new ActivityTestRule<search>(search.class);
    private search sActivity=null;
    Instrumentation.ActivityMonitor monitor=getInstrumentation().addMonitor(MainActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor1=getInstrumentation().addMonitor(Cart.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor2=getInstrumentation().addMonitor(locations.class.getName(),null,false);


    @Before
    public void setUp() throws Exception {
        sActivity=sActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        sActivity=null;
    }


    @Test
    public void onCreate() {
    }

    @Test
    public void inCart() {
    }

    @Test
    public void map() {
    }

    @Test
    public void inint() {
    }

    @Test
    public void isServiceOK() {
    }

    @Test
    public void locations(){
        assertNotNull(sActivity.findViewById(R.id.btnMap));
        onView(withId(R.id.btnMap)).perform(click());

        Activity locations=getInstrumentation().waitForMonitor(monitor2);
        assertNotNull(locations);
        locations.finish();
    }


    @Test
    public void cartView(){
        assertNotNull(sActivity.findViewById(R.id.viewCart));
        onView(withId(R.id.viewCart)).perform(click());

        Activity viewCart=getInstrumentation().waitForMonitor(monitor1);
        assertNotNull(viewCart);
        viewCart.finish();
    }

    @Test
    public void logout() {
        assertNotNull(sActivity.findViewById(R.id.logout));
        onView(withId(R.id.logout)).perform(click());

        Activity logout=getInstrumentation().waitForMonitor(monitor);
        assertNotNull(logout);
        logout.finish();
    }
}