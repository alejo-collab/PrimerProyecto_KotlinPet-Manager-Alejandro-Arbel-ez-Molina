package compose.project.hospitalvisualkotlin.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import compose.project.hospitalvisualkotlin.model.Paciente

class PacienteViewModel : ViewModel() {

    // Lista observable: la vista puede leerla, pero solo el ViewModel la cambia
    var pacientes by mutableStateOf<List<Paciente>>(emptyList())
        private set

    // Estado del formulario
    var nombre by mutableStateOf("")
        private set
    var especie by mutableStateOf("")
        private set
    var edad by mutableStateOf("")
        private set
    var contacto by mutableStateOf("")
        private set
    var esUrgencia by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    private var siguienteId = 1

    // Eventos que envía la vista
    fun onNombreChange(valor: String) { nombre = valor }
    fun onEspecieChange(valor: String) { especie = valor }
    fun onEdadChange(valor: String) {
        if (valor.all { it.isDigit() } && valor.length <= 3) edad = valor
    }
    fun onContactoChange(valor: String) { contacto = valor }
    fun onUrgenciaChange(valor: Boolean) { esUrgencia = valor }

    fun agregarPaciente() {
        val edadNumero = edad.toIntOrNull()
        val mensaje = when {
            nombre.isBlank() -> "Escribe el nombre del paciente."
            especie.isBlank() -> "Escribe la especie o raza."
            edadNumero == null -> "Escribe la edad en años."
            contacto.isBlank() -> "Escribe un contacto de emergencia."
            else -> null
        }
        if (mensaje != null || edadNumero == null) {
            error = mensaje
            return
        }

        val nuevo = Paciente(
            id = siguienteId++,
            nombre = nombre.trim(),
            especie = especie.trim(),
            edad = edadNumero,
            contactoEmergencia = contacto.trim(),
            esUrgencia = esUrgencia
        )
        pacientes = pacientes + nuevo
        limpiarFormulario()
    }

    fun limpiarFormulario() {
        nombre = ""
        especie = ""
        edad = ""
        contacto = ""
        esUrgencia = false
        error = null
    }
}