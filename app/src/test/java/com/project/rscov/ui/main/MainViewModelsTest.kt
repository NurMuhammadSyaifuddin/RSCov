package com.project.rscov.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.project.rscov.model.Hospital
import com.project.rscov.utils.DataDummy
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class MainViewModelsTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModels

    @Before
    fun setUp(){
        mainViewModel = Mockito.mock(MainViewModels::class.java)
    }

    @Test
    fun getListHospitals() {
        val dataDummy = MutableLiveData<MutableList<Hospital>>()
        dataDummy.value = DataDummy.generateDataMovie().toMutableList()

        `when`(mainViewModel.getHospitals()).thenReturn(dataDummy)

        val listHospitals = mainViewModel.getHospitals().value
        assertNotNull(listHospitals)
        verify(mainViewModel).getHospitals()
    }

}