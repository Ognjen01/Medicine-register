package com.ognjenlazic.medicineregister.data.repository

import com.ognjenlazic.medicineregister.data.api.RetrofitInstance
import com.ognjenlazic.medicineregister.data.model.Medicament
import com.ognjenlazic.medicineregister.data.model.MedicamentCategory
import com.ognjenlazic.medicineregister.data.model.MedicamentData
import com.ognjenlazic.medicineregister.data.model.MedicamentSubstances
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ApiRepository {

    suspend fun getMedicamentsAndCategories(processDone: ((List<MedicamentData>, List<MedicamentCategory>) -> Unit)) {

        val medicaments: MutableList<MedicamentData> = emptyList<MedicamentData>().toMutableList()
        val avalibleCategores : MutableList<MedicamentCategory> = emptyList<MedicamentCategory>().toMutableList()

        coroutineScope {
            launch {
                val medicamentsResponse =
                    withContext(Dispatchers.Default) {
                        getMedicaments()
                    }

                val categoriesResponse =
                    withContext(Dispatchers.Default) {
                        getCategories()
                    }

                if (medicamentsResponse.isSuccessful &&
                    categoriesResponse.isSuccessful
                ) {

                    medicamentsResponse.body()?.map { medicament ->
                        val category =
                            categoriesResponse.body()?.find { it.id == medicament.categoryId }
                        val medicamentData = MedicamentData(0, medicament, category!!)
                        medicaments.add(medicamentData)
                        avalibleCategores.add(category)
                    }
                }
                val categoryList = avalibleCategores.distinctBy { it.id }.toSet().toList()

                processDone(medicaments, categoryList)
            }
        }
    }

    private suspend fun getMedicaments(): Response<List<Medicament>> {
        return RetrofitInstance.api.getMedicaments()
    }

    private suspend fun getCategories(): Response<List<MedicamentCategory>> {
        return RetrofitInstance.api.getCategories()
    }

    suspend fun getMedicamentSubstances(drugId: Int): Response<List<MedicamentSubstances>> {
        return RetrofitInstance.api.getMedicamentSubstances(drugId)
    }
}