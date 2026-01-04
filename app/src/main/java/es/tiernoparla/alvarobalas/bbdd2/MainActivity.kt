package es.tiernoparla.alvarobalas.bbdd2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import es.tiernoparla.alvarobalas.bbdd2.data.ClienteDatabase
import es.tiernoparla.alvarobalas.bbdd2.data.ClienteEntity
import es.tiernoparla.alvarobalas.bbdd2.data.TelefonoEntity
import es.tiernoparla.alvarobalas.bbdd2.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var database: ClienteDatabase
        const val DATABASE_NAME = "cliente-db"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MainActivity.database =  Room.databaseBuilder(this,
            ClienteDatabase::class.java,
            DATABASE_NAME).build()

    }

    fun guardar(view: View) {

        val nombre = binding.tvNombre.text.toString()
        val apellidos = binding.tvApellidos.text.toString()
        val telefonosTexto = binding.tvTelefonos.text.toString()

        val clienteDao = database.clienteDao()
        val telefonoDao = database.telefonoDao()

        val cliente = ClienteEntity(0, nombre, apellidos)

        CoroutineScope(Dispatchers.IO).launch {

            val clienteId = clienteDao.insert(cliente)

            val listaTelefonos = telefonosTexto.split(",")
                .map { it.trim() }
                .filter { it.isNotEmpty() }

            for (numero in listaTelefonos) {
                val telefono = TelefonoEntity(0, numero, clienteId)
                telefonoDao.insert(telefono)
            }

        }


    }

    fun mostrar(view: View) {
        CoroutineScope(Dispatchers.IO).launch {
            val lista = database.clienteDao().getClientesTelefonos()

            runOnUiThread {
                binding.tvResultado.text = lista.joinToString("\n\n") { rel ->
                    buildString {
                        append("${rel.cliente.nombre} ${rel.cliente.apellidos}\n")
                        rel.telefonos.forEach {
                            append("${it.numero}\n")
                        }
                    }
                }
            }
        }
    }
}
