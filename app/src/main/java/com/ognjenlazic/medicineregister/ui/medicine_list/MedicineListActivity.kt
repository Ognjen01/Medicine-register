package com.ognjenlazic.medicineregister.ui.medicine_list

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.ognjenlazic.medicineregister.R
import com.ognjenlazic.medicineregister.databinding.ActivityMedicineListBinding
import com.ognjenlazic.medicineregister.ui.medicine_details.MedicineDetailsActivity
import com.ognjenlazic.medicineregister.ui.saved_medicaments.SavedMedicamentsActivity
import com.ognjenlazic.medicineregister.viewmodel.ViewModel

class MedicineListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedicineListBinding
    private lateinit var viewModel: ViewModel
    private lateinit var adapter: MedicamentAdapter


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMedicineListBinding.inflate(layoutInflater)

        viewModel = ViewModel(application)
        viewModel.getMedicamentsAndCategories()

        viewModel.medicamentsData.observe(this) { medicaments ->
            adapter = MedicamentAdapter(medicaments)

            adapter.onItemClick = {
                val intent = Intent(this, MedicineDetailsActivity::class.java)
                intent.putExtra("medicament", it)
                startActivity(intent)
            }

            binding.medicamentsRecyclerView.adapter = adapter
            binding.medicamentsRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.progressBar.visibility = View.GONE
        }

        viewModel.categories.observe(this) { list ->
            list.map { item ->
                val view = createCategoryMartItem(item.mark)
                binding.categoriesLinear.addView(view)
            }
        }

        binding.searchTextField.doOnTextChanged { text, _, _, _ ->
            viewModel.search(text.toString()) {
                adapter.medicaments = it
                adapter.notifyDataSetChanged()
            }
        }

        binding.save.setOnClickListener {
            val intent = Intent(this, SavedMedicamentsActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun createCategoryMartItem(mark: String): TextView {
        val view = TextView(this)
        view.text = mark
        view.setTextColor(resources.getColor(R.color.purple))
        view.setBackgroundResource(R.drawable.bg_purple)
        view.gravity = Gravity.CENTER
        view.textSize = 20F
        view.setPadding(10)

        view.setOnClickListener {
            viewModel.searchCategory(mark) {
                adapter.medicaments = it
                adapter.notifyDataSetChanged()
            }
        }

        return view
    }
}