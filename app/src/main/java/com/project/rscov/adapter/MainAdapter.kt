package com.project.rscov.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.rscov.R
import com.project.rscov.databinding.ItemHospitalBinding
import com.project.rscov.model.Hospital
import com.project.rscov.utils.DivHospitalsCallback
import com.project.rscov.utils.loadImage

class MainAdapter: RecyclerView.Adapter<MainAdapter.ViewHolder>(){

    private var listener: ((Hospital) -> Unit)? = null

    var hospitals = mutableListOf<Hospital>()
    set(value) {
        val callback = DivHospitalsCallback(field, value)
        val result = DiffUtil.calculateDiff(callback)
        field.clear()
        field.addAll(value)
        result.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemHospitalBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(hospital: Hospital){
            binding.apply {
                imgHospital.loadImage(hospital.imageUrl.toString())
                tvHospitalName.text = hospital.name
                tvRegion.text = itemView.resources.getString(R.string.location, hospital.region, hospital.province)
                tvTelepon.text = hospital.phone

                itemView.setOnClickListener {
                    listener?.let { listener1 ->
                        listener1(hospital)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        ItemHospitalBinding.inflate(LayoutInflater.from(parent.context), parent, false).also {
            return ViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(hospitals[position])
    }

    override fun getItemCount(): Int = hospitals.size

    fun onClick(listener: ((Hospital) -> Unit)?){
        this.listener = listener
    }

}