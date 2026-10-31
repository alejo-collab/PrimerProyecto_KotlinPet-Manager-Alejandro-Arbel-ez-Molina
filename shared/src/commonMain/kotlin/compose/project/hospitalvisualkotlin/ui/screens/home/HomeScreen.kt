package compose.project.hospitalvisualkotlin.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import compose.project.hospitalvisualkotlin.ui.components.PacienteCard
import compose.project.hospitalvisualkotlin.viewmodel.PacienteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: PacienteViewModel = viewModel { PacienteViewModel() }
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("KotlinPet Manager 🐾") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            FormularioPaciente(viewModel)

            Spacer(Modifier.height(16.dp))
            Text(
                "Pacientes registrados (${viewModel.pacientes.size})",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))

            if (viewModel.pacientes.isEmpty()) {
                Text(
                    "Aún no hay pacientes registrados.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(viewModel.pacientes, key = { it.id }) { paciente ->
                        PacienteCard(paciente)
                    }
                }
            }
        }
    }
}

@Composable
private fun FormularioPaciente(viewModel: PacienteViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = viewModel.nombre,
            onValueChange = viewModel::onNombreChange,
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = viewModel.especie,
            onValueChange = viewModel::onEspecieChange,
            label = { Text("Especie / Raza") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = viewModel.edad,
                onValueChange = viewModel::onEdadChange,
                label = { Text("Edad (años)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = viewModel.contacto,
                onValueChange = viewModel::onContactoChange,
                label = { Text("Contacto emergencia") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.weight(2f)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("¿Requiere atención inmediata?", modifier = Modifier.weight(1f))
            Switch(
                checked = viewModel.esUrgencia,
                onCheckedChange = viewModel::onUrgenciaChange
            )
        }
        viewModel.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = viewModel::agregarPaciente, modifier = Modifier.weight(1f)) {
                Text("Registrar Paciente")
            }
            OutlinedButton(onClick = viewModel::limpiarFormulario) {
                Text("Limpiar")
            }
        }
    }
}
