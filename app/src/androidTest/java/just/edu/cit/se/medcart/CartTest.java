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

public class CartTest {


    @Rule
    public ActivityTestRule<Cart> cActivityTestRule=new ActivityTestRule<Cart>(Cart.class);
    private Cart cActivity=null;
    Instrumentation.ActivityMonitor monitor= InstrumentationRegistry.getInstrumentation().addMonitor(orderInfo.class.getName(),null,false);

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
        Espresso.onView(ViewMatchers.withId(R.id.order)).perform(ViewActions.click());

        Activity order= InstrumentationRegistry.getInstrumentation().waitForMonitor(monitor);
        assertNotNull(order);
        order.finish();

    }



}