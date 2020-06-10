package just.edu.cit.se.medcart;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;
import android.widget.TextView;

import androidx.core.widget.TextViewCompat;
import androidx.test.espresso.ViewAssertion;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.w3c.dom.Text;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static java.util.regex.Pattern.matches;
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

    @Test
    public void search()
    {
        assertNotNull(sActivity.findViewById(R.id.s));
        sActivity.MedET.setText("concor");
         onView(withId(R.id.s)).perform(click());
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
         onView(withId(R.id.s)).perform(click());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int Size=sActivity.Clist.size();
        onView(withId(R.id.addCart)).perform(click());

         assertEquals(Size,sActivity.Clist.size());

    }
}