package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ucp2.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

@Dao
interface DokterDao {

    @Insert
    suspend fun insertDokter(dokter: Dokter)

    //fungsi get all data
    @Query("SELECT * FROM dokter ORDER BY namaDokter ASC")
    fun getAllDokter() : Flow<List<Dokter>>

    //get
    @Query("SELECT * FROM dokter WHERE idDokter = :idDokter")
    fun getDokter(idDokter: String): Flow<Dokter>
}