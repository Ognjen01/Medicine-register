package com.ognjenlazic.medicineregister.data.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "medicament_table")
data class Medicament(
    val activeSubstanceMeasurementUnit: String?,
    val activeSubstanceQuantityMeasurementUnit: String?,
    val activeSubstanceSelectedQuantity: Int,
    val activeSubstanceValue: Int,
    val atc: String?,
    val categoryId: Int,
    val description: String?,
    val forbiddenInPregnancy: Boolean,
    val id: Int,
    val maximumDailyDose: Int,
    val minimumDailyDose: Int,
    val name: String?,
    val shortDescription: String?,
    val showOnCalculator: Boolean
) : Parcelable