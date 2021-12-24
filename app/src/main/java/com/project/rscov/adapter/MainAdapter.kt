package com.project.rscov.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.rscov.databinding.ItemHospitalBinding
import com.project.rscov.model.Hospital
import com.project.rscov.utils.DivHospitalsCallback
import com.project.rscov.utils.loadImage

class MainAdapter: RecyclerView.Adapter<MainAdapter.ViewHolder>(), Filterable{

    private var listener: ((Hospital) -> Unit)? = null
    private var popUpImage: ((String) -> Unit)? = null

    val hospitalsFilter = mutableListOf<Hospital>()

    var hospitals = mutableListOf<Hospital>()
    set(value) {
        val callback = DivHospitalsCallback(field, value)
        val result = DiffUtil.calculateDiff(callback)
        field.clear()
        field.addAll(value)
        hospitalsFilter.clear()
        hospitalsFilter.addAll(value)
        result.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemHospitalBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(hospital: Hospital){
            binding.apply {
                imgHospital.loadImage(hospital.imageUrl.toString())
                tvHospitalName.text = hospital.name
                tvRegion.text = hospital.region
                tvTelepon.text = hospital.phone

                itemView.setOnClickListener {
                    listener?.let { listener1 ->
                        listener1(hospital)
                    }
                }

                imgHospital.setOnClickListener {
                    hospital.imageUrl?.let { url ->
                        popUpImage?.let { popUp ->
                            popUp(url)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHospitalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(hospitalsFilter[position])
    }

    override fun getItemCount(): Int = hospitalsFilter.size

    fun onClick(listener: ((Hospital) -> Unit)?){
        this.listener = listener
    }

    fun onClickToPopUpImage(popUpImage: ((String) -> Unit)?){
        this.popUpImage = popUpImage
    }

    override fun getFilter(): Filter = object : Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<Hospital>()
            val filternPattern = constraint.toString().trim().lowercase()

            if (filternPattern.isBlank()){
                filteredList.addAll(hospitals)
            }else{
                for (hospital in hospitals){
                    val title = hospital.name?.trim()?.lowercase()
                    val region = hospital.region?.trim()?.lowercase()
                    val province = hospital.province?.trim()?.lowercase()

                    if (title?.contains(filternPattern) as Boolean || region?.contains(filternPattern) as Boolean  || province?.contains(filternPattern) as Boolean ){
                        filteredList.add(hospital)
                    }
                }
            }
            val result = FilterResults()
            result.values = filteredList
            return result
        }

        override fun publishResults(p0: CharSequence?, results: FilterResults?) {
            val callback = DivHospitalsCallback(hospitalsFilter, results?.values as List<Hospital>)
            val result = DiffUtil.calculateDiff(callback)
            hospitalsFilter.clear()
            hospitalsFilter.addAll(results.values as List<Hospital>)
            result.dispatchUpdatesTo(this@MainAdapter)
        }

    }

}