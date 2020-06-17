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

public class LogInTest {

    @Rule
    public ActivityTestRule<LogIn> lActivityTestRule=new ActivityTestRule<LogIn>(LogIn.class);
    private LogIn lActivity=null;
    Instrumentation.ActivityMonitor monitor=getInstrumentation().addMonitor(search.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        lActivity=lActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        lActivity=null;
    }

    @Test
    public void login()
    {
        assertNotNull(lActivity.findViewById(R.id.loginbtn));
        lActivity.LEmail.setText("celinehaddad@yahoo.com");
        lActivity.Lpassword.setText("123456");

        onView(withId(R.id.loginbtn)).perform(click());

        Activity login=getInstrumentation().waitForMonitor(monitor);
        assertNotNull(login);
        login.finish();
    }
}