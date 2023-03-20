package com.ognjenlazic.medicineregister.data.repository

import androidx.lifecycle.LiveData
import com.ognjenlazic.medicineregister.data.database.MedicamentDao
import com.ognjenlazic.medicineregister.data.model.MedicamentData

class DatabaseRepository(private val medicamentDao: MedicamentDao) {

    val readAllData: LiveData<List<MedicamentData>> = medicamentDao.readAllData()

    suspend fun addMedicament(medicamentData: MedicamentData){
        medicamentDao.addMedicament(medicamentData)
    }

    suspend fun deleteMedicament(medicamentData: MedicamentData){
        medicamentDao.deleteMedicament(medicamentData)
    }
}