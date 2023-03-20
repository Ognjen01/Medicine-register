package com.ognjenlazic.medicineregister.data.api

import com.ognjenlazic.medicineregister.data.model.Medicament
import com.ognjenlazic.medicineregister.data.model.MedicamentCategory
import com.ognjenlazic.medicineregister.data.model.MedicamentSubstances
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("medicaments")
    suspend fun getMedicaments(): Response<List<Medicament>>

    @GET("categories")
    suspend fun getCategories(): Response<List<MedicamentCategory>>

    @GET("substances")
    suspend fun getMedicamentSubstances(@Query("drugid") drugId: Int): Response<List<MedicamentSubstances>>
}