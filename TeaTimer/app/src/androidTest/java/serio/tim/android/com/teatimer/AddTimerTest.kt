package serio.tim.android.com.teatimer

import android.support.test.espresso.Espresso
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import biz.kasual.materialnumberpicker.MaterialNumberPicker
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import serio.tim.android.com.teatimer.timer.TimerActivity
import android.support.test.InstrumentationRegistry




@RunWith(AndroidJUnit4::class)
@LargeTest
class AddTimerTest {
    companion object {
        fun setNumber(newValue: Int): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return ViewMatchers.isAssignableFrom(MaterialNumberPicker::class.java)
                }

                override fun getDescription(): String {
                    return "Could not set MaterialNumberPicker"
                }

                override fun perform(uiController: UiController?, view: View?) {
                    (view as MaterialNumberPicker).value = newValue
                }
            }
        }
    }

    private fun getResourceString(id: Int): String {
        val targetContext = InstrumentationRegistry.getTargetContext()
        return targetContext.resources.getString(id)
    }

    var mTimerActivityTestRule = ActivityTestRule<TimerActivity>(TimerActivity::class.java)
        @Rule get

    @Test
    @Throws(Exception::class)
    fun clickAddTimerButton_opensAddTimerUi() {
        Espresso.onView(ViewMatchers.withId(R.id.add_menu)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.edittext_add)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    @Throws(Exception::class)
    fun addTimer() {
        val newTimerTitle = "Raw Pu Erh"

        Espresso.onView(ViewMatchers.withId(R.id.add_menu)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.edittext_add)).perform(typeText(newTimerTitle), closeSoftKeyboard())

        Espresso.onView(ViewMatchers.withId(R.id.picker_add_degrees_hundreds)).perform(setNumber(1))

        Espresso.onView(ViewMatchers.withId(R.id.picker_add_degrees_tens)).perform(setNumber(7))

        Espresso.onView(ViewMatchers.withId(R.id.picker_add_degrees_ones)).perform(setNumber(0))

        Espresso.onView(ViewMatchers.withId(R.id.picker_add_minutes)).perform(setNumber(1))

        Espresso.onView(ViewMatchers.withId(R.id.picker_add_seconds_tens)).perform(setNumber(0))

        Espresso.onView(ViewMatchers.withId(R.id.picker_add_seconds_ones)).perform(setNumber(0))

        Espresso.onView(ViewMatchers.withId(R.id.fab_add)).perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.text_timer_countdown)).check(ViewAssertions.matches(ViewMatchers.withText(Matchers.containsString("3:00"))))
    }

    @Test
    @Throws(Exception::class)
    fun addAlreadyExistsErrorTest() {
        val newTimerTitle = "Raw Pu Erh"

        Espresso.onView(ViewMatchers.withId(R.id.add_menu)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.edittext_add)).perform(typeText(newTimerTitle), closeSoftKeyboard())

        Espresso.onView(ViewMatchers.withId(R.id.picker_add_degrees_hundreds)).perform(setNumber(1))

        Espresso.onView(ViewMatchers.withId(R.id.picker_add_degrees_tens)).perform(setNumber(7))

        Espresso.onView(ViewMatchers.withId(R.id.picker_add_degrees_ones)).perform(setNumber(0))

        Espresso.onView(ViewMatchers.withId(R.id.picker_add_minutes)).perform(setNumber(1))

        Espresso.onView(ViewMatchers.withId(R.id.picker_add_seconds_tens)).perform(setNumber(0))

        Espresso.onView(ViewMatchers.withId(R.id.picker_add_seconds_ones)).perform(setNumber(0))

        Espresso.onView(ViewMatchers.withId(R.id.fab_add)).perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.text_add_error)).check(ViewAssertions.matches(ViewMatchers.withText(Matchers.containsString(getResourceString(R.string.error_timer_exists)))))
    }

}