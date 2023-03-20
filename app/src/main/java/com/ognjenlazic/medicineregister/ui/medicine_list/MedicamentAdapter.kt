package com.ognjenlazic.medicineregister.ui.medicine_list

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ognjenlazic.medicineregister.data.model.MedicamentData
import com.ognjenlazic.medicineregister.databinding.MedicamentListItemBinding

class MedicamentAdapter(
    var medicaments: List<MedicamentData>
) : RecyclerView.Adapter<MedicamentAdapter.MedicamentItemHolder>() {

    inner class MedicamentItemHolder(val binding: MedicamentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(medicaments[adapterPosition])
            }
        }
    }

    var onItemClick: ((MedicamentData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicamentItemHolder {

        return MedicamentItemHolder(
            MedicamentListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MedicamentItemHolder, position: Int) {
        holder.binding.apply {
            val medicament = medicaments[position]
            medicamentName.text = medicament.medicament.name
            medicamentDesc.text = "${medicament.medicament.atc} - ${medicament.category.name}"
            color.setBackgroundColor(Color.parseColor(medicament.category.color))
        }
    }

    override fun getItemCount(): Int {
        return medicaments.size
    }
}