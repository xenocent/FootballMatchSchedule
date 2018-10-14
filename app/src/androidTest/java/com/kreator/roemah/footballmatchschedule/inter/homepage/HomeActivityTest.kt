package com.kreator.roemah.footballmatchschedule.inter.homepage

import android.support.design.widget.BottomNavigationView
import android.support.test.espresso.Espresso
import com.kreator.roemah.footballmatchschedule.R
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.kreator.roemah.footballmatchschedule.main.HomeActivity
import kotlinx.coroutines.experimental.delay
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {

        onView(withId(R.id.listPastEvent)).check(matches(isDisplayed()))
        onView(withId(R.id.listPastEvent)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.listPastEvent)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        onView(withId(R.id.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())
//        onView(withText("Added to favorite")).check(matches(isDisplayed())).inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.`is`(activityRule.activity.window.decorView)))).check(matches(isDisplayed()))

        Espresso.pressBack()

        onView(withId(R.id.bottom_nav_home))
                .check(matches(isDisplayed()))
        onView(withId(R.id.next)).perform(click())
        onView(withId(R.id.listNextEvent)).check(matches(isDisplayed()))
        onView(withId(R.id.listNextEvent)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.listNextEvent)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        onView(withId(R.id.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())
        onView(withText("Added to favorite")).check(matches(isDisplayed())).inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.`is`(activityRule.activity.window.decorView)))).check(matches(isDisplayed()))
        Espresso.pressBack()

    }
}