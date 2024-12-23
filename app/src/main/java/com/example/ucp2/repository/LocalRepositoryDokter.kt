package com.example.ucp2.repository

import com.example.ucp2.data.dao.DokterDao
import com.example.ucp2.data.entity.Dokter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalRepositoryDokter(private val dokterDao: DokterDao) : RepositoryDokter {

    override suspend fun insertDokter(dokter: Dokter) {
        withContext(Dispatchers.IO) {
            dokterDao.insertDokter(dokter)
        }
    }

    override fun getAllDokter(): Flow<List<Dokter>> {
        return dokterDao.getAllDokter()
    }

    override fun getDokter(idDokter: String): Flow<Dokter> {
        return dokterDao.getDokter(idDokter)
    }
}