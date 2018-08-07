package serio.tim.android.com.teatimer

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import serio.tim.android.com.teatimer.timer.TimerActivity

@RunWith(AndroidJUnit4::class)
@LargeTest
class TimerTest {

    var mTimerActivityTestRule = ActivityTestRule<TimerActivity>(TimerActivity::class.java)
        @Rule get

    @Test
    @Throws(Exception::class)
    fun startButtonClicked() {
        Espresso.onView(ViewMatchers.withId(R.id.fab_start)).perform(ViewActions.click())

    }

    @Test
    @Throws(Exception::class)
    fun pauseButtonClicked() {
        Espresso.onView(ViewMatchers.withId(R.id.fab_start)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.fab_pause)).perform(ViewActions.click())
    }

    @Test
    @Throws(Exception::class)
    fun stopButtonClicked() {
        Espresso.onView(ViewMatchers.withId(R.id.fab_start)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.fab_stop)).perform(ViewActions.click())
    }
}