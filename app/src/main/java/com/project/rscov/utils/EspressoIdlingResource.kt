package com.project.rscov.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {

    private const val RESOURCE = "global"

    val espressoIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment(){
        espressoIdlingResource.increment()
    }

    fun decrement(){
        espressoIdlingResource.decrement()
    }

}