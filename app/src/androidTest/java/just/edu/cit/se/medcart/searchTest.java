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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class searchTest {

    @Rule
    public ActivityTestRule<search> sActivityTestRule=new ActivityTestRule<search>(search.class);
    private search sActivity=null;
    Instrumentation.ActivityMonitor monitor= InstrumentationRegistry.getInstrumentation().addMonitor(MainActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor1= InstrumentationRegistry.getInstrumentation().addMonitor(Cart.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor2= InstrumentationRegistry.getInstrumentation().addMonitor(locations.class.getName(),null,false);


    @Before
    public void setUp() throws Exception {
        sActivity=sActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        sActivity=null;
    }



    @Test
    public void inCart() {
         assertNotNull(sActivity.findViewById(R.id.addCart));
         CartMeds CM=new CartMeds();
         CM.name="concor";
         CM.price="1.2JD";
         CM.quantity=1;
         sActivity.Clist.add(CM);

         sActivity.MedET.setText("concor");
         assertEquals(sActivity.inCart(),true);

         sActivity.MedET.setText("lanzotec");
         assertEquals(sActivity.inCart(),false);

    }



    @Test
    public void locations(){
        assertNotNull(sActivity.findViewById(R.id.btnMap));
        Espresso.onView(ViewMatchers.withId(R.id.btnMap)).perform(ViewActions.click());

        Activity locations= InstrumentationRegistry.getInstrumentation().waitForMonitor(monitor2);
        assertNotNull(locations);
        locations.finish();
    }


    @Test
    public void cartView(){
        assertNotNull(sActivity.findViewById(R.id.viewCart));
        Espresso.onView(ViewMatchers.withId(R.id.viewCart)).perform(ViewActions.click());

        Activity viewCart= InstrumentationRegistry.getInstrumentation().waitForMonitor(monitor1);
        assertNotNull(viewCart);
        viewCart.finish();
    }

    @Test
    public void logout() {
        assertNotNull(sActivity.findViewById(R.id.logout));
        Espresso.onView(ViewMatchers.withId(R.id.logout)).perform(ViewActions.click());

        Activity logout= InstrumentationRegistry.getInstrumentation().waitForMonitor(monitor);
        assertNotNull(logout);
        logout.finish();
    }

    @Test
    public void search()
    {
        assertNotNull(sActivity.findViewById(R.id.s));
        sActivity.MedET.setText("concor");
         Espresso.onView(ViewMatchers.withId(R.id.s)).perform(ViewActions.click());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(sActivity.MedET.getText().toString(),sActivity.name.getText().toString());

    }

    @Test
    public void addToCart(){
         assertNotNull(sActivity.findViewById(R.id.addCart));
         CartMeds CM=new CartMeds();
         CM.name="concor";
         CM.price="1.2JD";
         CM.quantity=1;
         sActivity.Clist.add(CM);

         sActivity.MedET.setText("concor");
         Espresso.onView(ViewMatchers.withId(R.id.s)).perform(ViewActions.click());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int Size=sActivity.Clist.size();
        Espresso.onView(ViewMatchers.withId(R.id.addCart)).perform(ViewActions.click());

         assertEquals(Size,sActivity.Clist.size());

    }
}