package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.RumahSakitApp

object PenyediaViewModel {
    val Factory = viewModelFactory {

        initializer {
            DokterViewModel(
                RumahSakitApp().containerApp.dokterRepository
            )
        }

        initializer {
            HomeScreenViewModel(
                RumahSakitApp().containerApp.dokterRepository
            )
        }

        initializer {
            InsertJadwalViewModel(
                RumahSakitApp().containerApp.jadwalRepository,
                RumahSakitApp().containerApp.dokterRepository
            )
        }

        initializer {
            HomeJadwalViewModel(
                RumahSakitApp().containerApp.jadwalRepository
            )
        }

        initializer {
            UpdateJadwalViewModel(
                createSavedStateHandle(),
                RumahSakitApp().containerApp.jadwalRepository

            )
        }
    }
}

fun CreationExtras.RumahSakitApp(): RumahSakitApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RumahSakitApp)