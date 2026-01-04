package es.tiernoparla.alvarobalas.bbdd2.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ClienteDao {

    @Transaction
    @Query("SELECT * FROM cliente")
    suspend fun getClientesTelefonos(): List<ClienteTelefonos>


    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cliente: ClienteEntity):Long

}