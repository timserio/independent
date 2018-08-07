package serio.tim.android.com.teatimer

import android.support.test.espresso.Espresso
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import biz.kasual.materialnumberpicker.MaterialNumberPicker
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.anything
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import serio.tim.android.com.teatimer.timer.TimerActivity

@RunWith(AndroidJUnit4::class)
@LargeTest
class EditTimerTest {

    var mTimerActivityTestRule = ActivityTestRule<TimerActivity>(TimerActivity::class.java)
            @Rule get

    @Test
    @Throws(Exception::class)
    fun clickEditTimerButton_opensEditTimerUi() {
        Espresso.onView(ViewMatchers.withId(R.id.edit_menu)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.text_edit_label)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.text_edit_teaname)).check(ViewAssertions.matches(ViewMatchers.withText(Matchers.containsString("Black Tea"))))

        Espresso.onView(ViewMatchers.withId(R.id.fab_edit)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.text_timer_countdown)).check(ViewAssertions.matches(ViewMatchers.withText(Matchers.containsString("3:00"))))
    }


    @Test
    @Throws(Exception::class)
    fun onDeleteClickedError() {
        Espresso.onView(ViewMatchers.withId(R.id.spinner_timer_tea_type)).perform(ViewActions.click())
        Espresso.onData(anything()).atPosition(7).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.edit_menu)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.edit_menu)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.text_timer_countdown)).check(ViewAssertions.matches(isDisplayed()))
    }
}