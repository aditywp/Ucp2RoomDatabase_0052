package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.repository.RepositoryDokter
import kotlinx.coroutines.launch

class DokterViewModel(
    private val repositoryDokter: RepositoryDokter
) : ViewModel() {

    private val _dokterEvent = MutableLiveData<List<Dokter>>()

    val dokterEvent: LiveData<List<Dokter>> get() = _dokterEvent


    fun setDokterList(dokters: List<Dokter>) {
        _dokterEvent.value = dokters
    }

    fun getDokterList(): List<Dokter> {
        return _dokterEvent.value ?: emptyList()
    }

    var uiState by mutableStateOf(DokterUIState())
        private set

    fun updateState(dokterEvent: DokterEvent) {
        uiState = uiState.copy(
            dokterEvent = dokterEvent
        )
    }



data class DokterUIState(
    val dokterEvent: DokterEvent = DokterEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null
)


data class DokterEvent(
    val idDokter: String = "",
    val nama: String = "",
    val spesialis: String = "",
    val klinik: String = "",
    val noHp: String = "",
    val jamKerja: String = ""
){
    fun toDokterEntity(): Dokter = Dokter(
        idDokter = idDokter,
        namaDokter = nama,
        spesialis = spesialis,
        klinik = klinik,
        noHp = noHp,
        jamKerja = jamKerja
    )

}

data class FormErrorState(
    val idDokter: String? =null,
    val nama: String? =null,
    val spesialis: String? =null,
    val klinik: String? =null,
    val noHp: String? =null,
    val jamKerja: String? =null
){
    fun isValid(): Boolean {
        return idDokter == null
                && nama == null
                && spesialis == null
                && klinik == null
                && noHp == null
                && jamKerja == null
    }
}
}