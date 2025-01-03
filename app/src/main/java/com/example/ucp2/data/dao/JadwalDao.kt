package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

@Dao
interface JadwalDao {
    @Insert
    suspend fun addJadwal(jadwal: Jadwal)

    @Query("SELECT * FROM jadwal ORDER BY namaPasien")
    fun getAllJadwal(): Flow<List<Jadwal>>

    @Query("SELECT*FROM jadwal WHERE idJadwal = :idJadwal")
    fun getJadwal(idJadwal: String): Flow<Jadwal>

    @Update
    suspend fun updateJadwal(jadwal: Jadwal)

    @Delete
    suspend fun deleteJadwal(jadwal: Jadwal)

}