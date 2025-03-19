package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceroller.ui.theme.DiceRollerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DiceRollerApp()
                }
            }
        }
    }
}

@Preview
@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8D9E6)) // Ganti warna background di sini
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    var result1 by remember { mutableStateOf(1) }
    var result2 by remember { mutableStateOf(1) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp)) // Menurunkan posisi dadu

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(getDiceImage(result1)),
                    contentDescription = result1.toString()
                )
                Text(text = result1.toString(), fontSize = 24.sp) // Menampilkan angka dadu pertama
            }

            Spacer(modifier = Modifier.width(16.dp)) // Spasi antara dua dadu

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(getDiceImage(result2)),
                    contentDescription = result2.toString()
                )
                Text(text = result2.toString(), fontSize = 24.sp) // Menampilkan angka dadu kedua
            }
        }

        Spacer(modifier = Modifier.height(32.dp)) // Menyesuaikan tata letak tombol

        Button(
            onClick = {
                result1 = (1..6).random()
                result2 = (1..6).random()
            },
        ) {
            Text(text = stringResource(R.string.roll), fontSize = 24.sp)
        }
    }
}

// Fungsi untuk mendapatkan gambar dadu berdasarkan angka
fun getDiceImage(result: Int): Int {
    return when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
}
