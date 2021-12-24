package com.project.rscov.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.project.rscov.model.Hospital
import com.project.rscov.utils.DataDummy
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class DetailViewModelTest{

    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setUp(){
        detailViewModel = Mockito.mock(DetailViewModel::class.java)
    }

    @Test
    fun getDetailHospital(){
        val dataDummy = MutableLiveData<Hospital>()
        dataDummy.value = DataDummy.generateDataMovie().toMutableList()[0]

        Mockito.`when`(detailViewModel.getHospital()).thenReturn(dataDummy)

        val hospital = detailViewModel.getHospital().value
        assertNotNull(hospital)
        Mockito.verify(detailViewModel).getHospital()
    }

}