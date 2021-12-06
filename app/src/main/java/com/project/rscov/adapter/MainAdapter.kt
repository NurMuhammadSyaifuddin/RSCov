package com.project.rscov.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.rscov.R
import com.project.rscov.databinding.ItemHospitalBinding
import com.project.rscov.model.Hospital
import com.project.rscov.utils.DivHospitalsCallback
import com.project.rscov.utils.loadImage

class MainAdapter: RecyclerView.Adapter<MainAdapter.ViewHolder>(), Filterable {

    private var listener: ((Hospital) -> Unit)? = null
    private var hospitalsFilter = mutableListOf<Hospital>()

    var hospitals = mutableListOf<Hospital>()
    set(value) {
        val callback = DivHospitalsCallback(field, value)
        val result = DiffUtil.calculateDiff(callback)
        field.clear()
        field.addAll(value)
        hospitalsFilter.addAll(value)
        result.dispatchUpdatesTo(this)
    }

    private val filters = object : Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var filteredList = mutableListOf<Hospital>()
            val filterPattern = constraint.toString().trim().lowercase()

            if (filterPattern.isEmpty()){
                filteredList = hospitals
            }else{
                for (hospital in hospitals){
                    val title = hospital.name.trim().lowercase()
                    val region = hospital.region.trim().lowercase()
                    val province = hospital.province.trim().lowercase()

                    if (title.contains(filterPattern) || region.contains(filterPattern) || province.contains(filterPattern)){
                        filteredList.add(hospital)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(p0: CharSequence?, results: FilterResults?) {
            val callback = DivHospitalsCallback(hospitalsFilter, results?.values as List<Hospital>)
            val result = DiffUtil.calculateDiff(callback)
            hospitalsFilter = results.values as MutableList<Hospital>
            result.dispatchUpdatesTo(this@MainAdapter)
        }

    }

    inner class ViewHolder(private val binding: ItemHospitalBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(hospital: Hospital){
            binding.apply {
                imgHospital.loadImage(hospital.imageUrl)
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

    override fun getFilter(): Filter = filters

}