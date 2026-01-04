package es.tiernoparla.alvarobalas.bbdd2.data

import androidx.room.Embedded
import androidx.room.Relation

data class ClienteTelefonos(
    @Embedded val cliente: ClienteEntity,
    @Relation(
        parentColumn = "id", //Entidad cliente
        entityColumn = "clienteId" // Entidad secundaria: telefono

    )
    val telefonos: List<TelefonoEntity>

)