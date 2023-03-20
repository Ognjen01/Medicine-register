package com.ognjenlazic.medicineregister.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ognjenlazic.medicineregister.data.model.MedicamentData

@Dao
interface MedicamentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMedicament(medicament: MedicamentData)

    @Delete
    suspend fun deleteMedicament(medicament: MedicamentData)

    @Query("DELETE FROM medicament_data_table")
    suspend fun deleteAllMedicaments()

    @Query("SELECT * FROM medicament_data_table")
    fun readAllData(): LiveData<List<MedicamentData>>

}