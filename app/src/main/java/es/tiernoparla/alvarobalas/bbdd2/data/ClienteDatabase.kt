package es.tiernoparla.alvarobalas.bbdd2.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ClienteEntity::class, TelefonoEntity::class], version = 2)
abstract class ClienteDatabase: RoomDatabase() {

    abstract fun clienteDao(): ClienteDao

    abstract fun telefonoDao(): TelefonoDao
}