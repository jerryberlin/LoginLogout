package com.example.loginlogout.presentation.screen.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.loginlogout.ui.theme.White500
import com.example.loginlogout.ui.theme.White700

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember { Animatable(0f) }
    
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 200
            )
        )
    }

    Splash(scale = scale.value)
}

@Composable
fun Splash(scale: Float) {
    if(isSystemInDarkTheme()){
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            , contentAlignment = Alignment.Center) {
            Text(
                text = "Splash Screen",
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier.scale(scale)
            )
        }
    }else{
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(White500, White700)))
            , contentAlignment = Alignment.Center) {
            Text(
                text = "Splash Screen",
                fontSize = 30.sp,
                color = Color.Black,
                modifier = Modifier.scale(scale)
            )
        }
    }
}

@Preview
@Composable
fun PrevSplash() {
    Splash(scale = 0f)
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PrevSplashNightMode() {
    Splash(scale = 0f)
}

