package com.ognjenlazic.medicineregister.ui.medicine_details

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.load
import com.ognjenlazic.medicineregister.R
import com.ognjenlazic.medicineregister.data.model.MedicamentData
import com.ognjenlazic.medicineregister.data.model.MedicamentSubstances
import com.ognjenlazic.medicineregister.databinding.ActivityMedicineDetailsBinding
import com.ognjenlazic.medicineregister.utilities.Constants
import com.ognjenlazic.medicineregister.viewmodel.ViewModel

class MedicineDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedicineDetailsBinding
    private lateinit var viewModel: ViewModel

    var savedInDatabase = false

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMedicineDetailsBinding.inflate(layoutInflater)

        viewModel = ViewModel(application)

        val medicament = intent.extras?.get("medicament") as MedicamentData

        viewModel.getMedicamentSubstances(medicament.medicament.id)

        binding.image.load(Constants.IMAGE)
        binding.medicamentName.text = medicament.medicament.name
        binding.category.text = "${medicament.medicament.atc} - ${medicament.category.name}"
        binding.shortDesc.text = medicament.medicament.shortDescription
        binding.decsription.text = medicament.medicament.description

        viewModel.readAllData.observe(this){
            for (item in it){
                if (item.medicament.id == medicament.medicament.id){
                    binding.save.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.saved))
                    savedInDatabase = true
                    break
                }
            }
        }

        binding.save.setOnClickListener{
            if (savedInDatabase){
                viewModel.deleteMedicament(medicament)
                binding.save.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.save))
                savedInDatabase = false
            } else {
                viewModel.addMedicament(medicament)
            }
        }

        if (medicament.medicament.activeSubstanceValue != 0 ){
            binding.activeSubstanceText.text = "${medicament.medicament.activeSubstanceValue} - ${medicament.medicament.activeSubstanceMeasurementUnit}"
        } else {
            binding.activeSubstanceText.text = "/"
        }

        if (medicament.medicament.activeSubstanceSelectedQuantity != 0 ){
            binding.activeSubstanceRatioText.text = "${medicament.medicament.activeSubstanceSelectedQuantity} - ${medicament.medicament.activeSubstanceQuantityMeasurementUnit}"
        } else {
            binding.activeSubstanceRatioText.text = "/"
        }

        if (medicament.medicament.minimumDailyDose != 0 ){
            binding.recomendedDoseText.text = "${medicament.medicament.minimumDailyDose}"
        } else {
            binding.recomendedDoseText.text = "/"
        }

        viewModel.medicamentSubstances.observe(this){substances ->
            substances.map {substance ->
                binding.table.addView(createTableViewRow(substance))
            }
        }

        setContentView(binding.root)
    }

    private fun createTableViewRow(substance: MedicamentSubstances) : TableRow{
        val row = TableRow(this)
        val t1v = TextView(this)
        t1v.text = substance.name
        t1v.setTextColor(Color.BLACK)
        t1v.gravity = Gravity.CENTER
        row.addView(t1v)
        return row
    }
}