package com.ognjenlazic.medicineregister.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "medicament_data_table")
data class MedicamentData(
    @PrimaryKey(autoGenerate = true)
    val dbId: Int,
    @Embedded(prefix = "medicament_")
    val medicament: Medicament,
    @Embedded(prefix = "category_")
    val category: MedicamentCategory
) : Parcelable