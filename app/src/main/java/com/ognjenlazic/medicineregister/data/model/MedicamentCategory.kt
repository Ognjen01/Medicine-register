package com.ognjenlazic.medicineregister.data.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "medicament_category_table")
data class MedicamentCategory(
    val color: String,
    val id: Int,
    val mark: String,
    val name: String
) : Parcelable