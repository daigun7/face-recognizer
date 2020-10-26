package com.sgsoft.facerecognizer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.sgsoft.facerecognizer.ui.MainActivity
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @get:Rule
    val mainActivityRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Test
    fun test() {
        onView(withId(R.id.tab_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.pager)).check(matches(isDisplayed()))
    }
}