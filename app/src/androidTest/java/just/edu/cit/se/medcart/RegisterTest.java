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

public class RegisterTest {

    @Rule
    public ActivityTestRule<Register> rActivityTestRule=new ActivityTestRule<Register>(Register.class);
    private Register rActivity=null;
    Instrumentation.ActivityMonitor monitor= InstrumentationRegistry.getInstrumentation().addMonitor(search.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        rActivity=rActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        rActivity=null;
    }

    @Test
    public void register(){ //this also will make sure that the database is connected
        assertNotNull(rActivity.findViewById(R.id.regbtn));
        rActivity.Rname.setText("celine");
        rActivity.REmail.setText("celine@outlook.com");
        rActivity.Rphone.setText("079234344");
        rActivity.Rpassword.setText("123456");
        Espresso.onView(ViewMatchers.withId(R.id.regbtn)).perform(ViewActions.click());

        Activity register= InstrumentationRegistry.getInstrumentation().waitForMonitor(monitor);
        assertNotNull(register);
        register.finish();
    }
}