package compose.project.hospitalvisualkotlin

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import compose.project.hospitalvisualkotlin.ui.screens.home.HomeScreen
import compose.project.hospitalvisualkotlin.ui.theme.HospitalVisualKotlinTheme

@Composable
@Preview
fun App() {
    HospitalVisualKotlinTheme {
        HomeScreen()
    }
}