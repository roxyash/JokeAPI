package com.example.myapplication

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)
    val room = Room.databaseBuilder(ApplicationProvider.getApplicationContext(), JokeDatabase::class.java, "Joke").build()

    @Test
    fun test(){
        room.jokeDao().clear()
        onView(withId(R.id.type)).perform(ViewActions.clearText())
        onView(withId(R.id.type)).perform(ViewActions.typeText("ru"))
        onView(withId(R.id.load)).perform(ViewActions.click())
        assertEquals(1,room.jokeDao().getAll().size)

    }
}