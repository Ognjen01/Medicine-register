package com.ognjenlazic.medicineregister.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ognjenlazic.medicineregister.data.database.MedicamentDatabase
import com.ognjenlazic.medicineregister.data.model.MedicamentCategory
import com.ognjenlazic.medicineregister.data.model.MedicamentData
import com.ognjenlazic.medicineregister.data.model.MedicamentSubstances
import com.ognjenlazic.medicineregister.data.repository.ApiRepository
import com.ognjenlazic.medicineregister.data.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val apiRepository = ApiRepository()
    val medicamentsData: MutableLiveData<List<MedicamentData>> = MutableLiveData()
    val categories: MutableLiveData<List<MedicamentCategory>> = MutableLiveData()
    val medicamentSubstances: MutableLiveData<List<MedicamentSubstances>> = MutableLiveData()

    val readAllData: LiveData<List<MedicamentData>>
    private val databaseRepository: DatabaseRepository

    init {
        val medicamentDao = MedicamentDatabase.getDatabase(
            application
        ).medicamentDao()
        databaseRepository = DatabaseRepository(medicamentDao)
        readAllData = databaseRepository.readAllData
    }

    fun getMedicamentsAndCategories() {
        viewModelScope.launch {
            apiRepository.getMedicamentsAndCategories { medicamentData, medicamentCategories ->
                medicamentsData.value = medicamentData
                categories.value = medicamentCategories
            }
        }
    }

    fun search(text: String, searchDone: ((List<MedicamentData>) -> Unit)) {
        val searchResult = emptyList<MedicamentData>().toMutableList()
        medicamentsData.value?.map {
            if (it.medicament.name?.lowercase()?.contains(text.lowercase()) == true) {
                searchResult.add(it)
            }
        }
        searchDone(searchResult)
    }

    fun searchCategory(categoryMark: String, searchDone: ((List<MedicamentData>) -> Unit)) {
        val searchResult = emptyList<MedicamentData>().toMutableList()
        medicamentsData.value?.map {
            if (it.category.mark.lowercase() == categoryMark.lowercase()) {
                searchResult.add(it)
            }
        }
        searchDone(searchResult)
    }

    fun getMedicamentSubstances(drugId: Int) {
        viewModelScope.launch {
            val response = try {
                apiRepository.getMedicamentSubstances(drugId)
            } catch (e: IOException) {
                return@launch
            } catch (e: HttpException) {
                return@launch
            }
            if (response.isSuccessful) {
                medicamentSubstances.value = response.body()
            }
        }
    }

    fun addMedicament(medicamentData: MedicamentData){
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.addMedicament(medicamentData)
        }
    }

    fun deleteMedicament(medicamentData: MedicamentData){
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.deleteMedicament(medicamentData)
        }
    }
}