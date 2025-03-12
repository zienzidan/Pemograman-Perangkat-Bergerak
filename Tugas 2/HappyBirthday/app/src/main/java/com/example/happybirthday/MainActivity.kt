package com.example.happybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimatedBirthdayGreeting(
                message = "Happy Birthday,",
                name = "Tama",
                from = "From Zien"
            )
        }
    }
}

@Composable
fun AnimatedBirthdayGreeting(message: String, name: String, from: String) {
    var startAnimation by remember { mutableStateOf(false) }

    // Animasi Fade-in
    val fadeAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 2000, easing = LinearEasing),
        label = "fade-in"
    )

    // Animasi Bounce
    val bounceAnim = rememberInfiniteTransition(label = "bounce")
    val bounceOffset by bounceAnim.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ), label = "bounce"
    )

    // Jalankan animasi setelah 500ms
    LaunchedEffect(Unit) {
        delay(500)
        startAnimation = true
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFA500), Color(0xFFFF4500))
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = "$message\n$name! 🎉",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White.copy(alpha = fadeAlpha),
            lineHeight = 52.sp,
            modifier = Modifier.offset(y = bounceOffset.dp) // Efek naik-turun
        )
        Spacer(modifier = Modifier.height(16.dp))
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White.copy(alpha = 0.2f)
        ) {
            Text(
                text = from,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = Color.White.copy(alpha = fadeAlpha),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BirthdayPreview() {
    AnimatedBirthdayGreeting(message = "Happy Birthday,", name = "Tama", from = "From Zien")
}
