package com.example.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.data.database.RumahSakitDatabase
import com.example.ucp2.repository.LocalRepositoryDokter
import com.example.ucp2.repository.LocalRepositoryJadwal
import com.example.ucp2.repository.RepositoryDokter
import com.example.ucp2.repository.RepositoryJadwal

interface InterfaceContainerApp {
    val dokterRepository: RepositoryDokter
    val jadwalRepository: RepositoryJadwal
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val dokterRepository: RepositoryDokter by lazy {
        LocalRepositoryDokter(RumahSakitDatabase.getDatabase(context).dokterDao())
    }

    override val jadwalRepository: RepositoryJadwal by lazy {
        LocalRepositoryJadwal(RumahSakitDatabase.getDatabase(context).jadwalDao())
    }
}