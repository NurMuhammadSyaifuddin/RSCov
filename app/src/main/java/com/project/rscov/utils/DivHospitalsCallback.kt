package com.project.rscov.utils

import androidx.recyclerview.widget.DiffUtil
import com.project.rscov.model.Hospital

class DivHospitalsCallback(
    private val oldListHospital: List<Hospital>,
    private val newListHospital: List<Hospital>
) : DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldListHospital.size

    override fun getNewListSize(): Int = newListHospital.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldListHospital[oldItemPosition].name == newListHospital[newItemPosition].name

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldListHospital[oldItemPosition].name == newListHospital[newItemPosition].name
}