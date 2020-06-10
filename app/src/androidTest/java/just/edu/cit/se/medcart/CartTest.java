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

public class CartTest {


    @Rule
    public ActivityTestRule<Cart> cActivityTestRule=new ActivityTestRule<Cart>(Cart.class);
    private Cart cActivity=null;
    Instrumentation.ActivityMonitor monitor=getInstrumentation().addMonitor(orderInfo.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        cActivity=cActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        cActivity=null;
    }

    @Test
    public void order(){
        assertNotNull(cActivity.findViewById(R.id.order));
        onView(withId(R.id.order)).perform(click());

        Activity order=getInstrumentation().waitForMonitor(monitor);
        assertNotNull(order);
        order.finish();

    }



}