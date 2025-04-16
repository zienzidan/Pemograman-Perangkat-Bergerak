package com.example.mycalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CalculatorScreen()
            }
        }
    }
}

@Composable
fun CalculatorScreen() {
    var input by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    // Elegant Dark Mode Colors
    val backgroundColor = Color(0xFF121212)
    val textColor = Color.White
    val numberColor = Color(0xFF2E2E2E)
    val operatorColor = Color(0xFF00C853)
    val actionColor = Color(0xFFFF6D00)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = if (result.isNotEmpty()) result else input,
            fontSize = 72.sp,
            color = textColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val buttons = listOf(
                listOf("1", "2", "3", "+"),
                listOf("4", "5", "6", "-"),
                listOf("7", "8", "9", "x"),
                listOf(".", "0", "C", "/")
            )

            for (row in buttons) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEach {
                        val buttonColor = when {
                            it == "C" -> actionColor
                            it in listOf("+", "-", "x", "/") -> operatorColor
                            else -> numberColor
                        }

                        CalculatorButton(text = it, color = buttonColor) {
                            onButtonClick(it, input, operator, result) { i, o, r ->
                                input = i
                                operator = o
                                result = r
                            }
                        }
                    }
                }
            }

            // Equal button
            Button(
                onClick = {
                    calculateResult(input, operator)?.let {
                        result = it
                        input = ""
                        operator = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = actionColor,
                    contentColor = Color.White
                )
            ) {
                Text("=", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun CalculatorButton(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.White
        ),
        modifier = Modifier
            .size(80.dp)
    ) {
        Text(text, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

fun onButtonClick(
    value: String,
    inputState: String,
    operatorState: String,
    resultState: String,
    updateStates: (input: String, operator: String, result: String) -> Unit
) {
    var input = inputState
    var operator = operatorState
    var result = resultState

    when (value) {
        "+", "-", "x", "/" -> {
            if (input.isNotEmpty() && operator.isEmpty()) {
                operator = value
                input += " $value "
            }
        }
        "C" -> {
            input = ""
            operator = ""
            result = ""
        }
        else -> {
            input += value
        }
    }
    updateStates(input, operator, result)
}

fun calculateResult(input: String, operator: String): String? {
    val parts = input.split(" ")
    if (parts.size < 3) return null

    val num1 = parts[0].toDoubleOrNull()
    val num2 = parts[2].toDoubleOrNull()

    if (num1 == null || num2 == null) return null

    return when (operator) {
        "+" -> (num1 + num2).toString()
        "-" -> (num1 - num2).toString()
        "x" -> (num1 * num2).toString()
        "/" -> if (num2 != 0.0) (num1 / num2).toString() else "Error"
        else -> null
    }
}
