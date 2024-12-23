package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.repository.RepositoryDokter

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
}