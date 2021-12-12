package com.project.rscov.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.project.rscov.R
import com.project.rscov.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest{

    private val searchSuccessText = "Semarang"
    private val searchFailedText = "Semarangs"
    private val getTextFailed = "Hasil pencarian \"$searchFailedText\" tidak ditemukan"

    @get:Rule
    var actvityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoIdlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoIdlingResource)
    }

    @Test
    fun loadHospitals() {
        onView(withId(R.id.rv_hospitals))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_hospitals))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))

    }

    @Test
    fun detailHospital() {
        onView(withId(R.id.rv_hospitals))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_hospitals))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.rv_hospitals))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    10,
                    click()
                )
            )

        onView(withId(R.id.img_picture_header))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_hospital_name))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_address))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_region))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_province))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_phone))
            .check(matches(isDisplayed()))
        onView(withId(R.id.img_copas))
            .perform(click())

        Espresso.pressBack()
    }

    @Test
    fun searchSuccess(){
        onView(withId(R.id.edt_search_main)).perform(typeText(searchSuccessText), closeSoftKeyboard())
        onView(withId(R.id.rv_hospitals))
            .check(matches(isDisplayed()))
    }

    @Test
    fun searchFailed(){
        onView(withId(R.id.edt_search_main)).perform(typeText(searchFailedText), closeSoftKeyboard())
        onView(withId(R.id.tv_no_data)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_no_data)).check(matches(withText(getTextFailed)))
    }

}