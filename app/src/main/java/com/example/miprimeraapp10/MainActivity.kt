package com.example.miprimeraapp10

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.miprimeraapp10.ui.theme.MiprimeraApp10Theme
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private val REQUEST_ENABLE_BT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar y habilitar Bluetooth
        checkAndEnableBluetooth()

        setContent {
            var signalStrength by remember { mutableStateOf(0) }

            LaunchedEffect(Unit) {
                while (true) {
                    signalStrength = obtenerIntensidadDeSenalBluetooth()
                    delay(1000) // Actualiza cada segundo (ajusta según sea necesario)
                }
            }

            MiprimeraApp10Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting(signalStrength)
                }
            }
        }
    }

    private fun checkAndEnableBluetooth() {
        // Verificar si el dispositivo admite Bluetooth
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            // El dispositivo no admite Bluetooth, manejar según tus necesidades
            // Aquí puedes mostrar un mensaje o realizar acciones específicas
        } else {
            // Bluetooth está disponible
            if (!bluetoothAdapter.isEnabled) {
                // Pide al usuario que habilite Bluetooth
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            }
        }
    }

    private fun obtenerIntensidadDeSenalBluetooth(): Int {
        // Simula una intensidad de señal aleatoria entre 0 y 100 (ajusta según tus necesidades)
        return Random.nextInt(0, 101)
    }
}

@Composable
fun Greeting(signalStrength: Int, modifier: Modifier = Modifier) {
    Text(
        text = "Bluetooth Signal Strength: $signalStrength dB",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MiprimeraApp10Theme {
        Greeting(50)
    }
}
