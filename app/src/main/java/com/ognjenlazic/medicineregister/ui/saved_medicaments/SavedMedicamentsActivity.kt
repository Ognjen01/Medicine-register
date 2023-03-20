package com.ognjenlazic.medicineregister.ui.saved_medicaments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ognjenlazic.medicineregister.databinding.ActivitySavedMedicamentsBinding
import com.ognjenlazic.medicineregister.ui.medicine_details.MedicineDetailsActivity
import com.ognjenlazic.medicineregister.ui.medicine_list.MedicamentAdapter
import com.ognjenlazic.medicineregister.viewmodel.ViewModel

class SavedMedicamentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySavedMedicamentsBinding
    private lateinit var viewModel: ViewModel
    private lateinit var adapter: MedicamentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedMedicamentsBinding.inflate(layoutInflater)
        viewModel = ViewModel(application)

        viewModel.readAllData.observe(this){ medicaments ->
            adapter = MedicamentAdapter(medicaments)

            adapter.onItemClick = {
                val intent = Intent(this, MedicineDetailsActivity::class.java)
                intent.putExtra("medicament", it)
                startActivity(intent)
            }

            binding.medicamentsRecyclerView.adapter = adapter
            binding.medicamentsRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.progressBar.visibility = View.GONE

            if(medicaments.isEmpty()){
                binding.emptyList.visibility = View.VISIBLE
            } else {
                binding.emptyList.visibility = View.GONE
            }
        }
        setContentView(binding.root)
    }
}