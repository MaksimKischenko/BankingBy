package com.example.smsbankinganalitics.view.screens

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.view_models.SplashViewModel
import com.example.smsbankinganalitics.view_models.utils.Localization


@Composable
fun SplashScreen(
    navHostController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {

    val launcher = splashViewModel.initLauncherAndCheckFirstLoad(navHostController)

    LaunchedEffect(Unit) {
        launcher.launch(arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS))

    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.onTertiary)
            .fillMaxSize()
    ) {
        Image(
            painterResource(id = R.drawable.banking),
            contentDescription = "banking",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
        )
        Text(
            text = Localization.withComposable(R.string.wait),
            modifier = Modifier.padding(vertical = 8.dp),
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.tertiary
            ),
            fontSize = 20.sp,
        )
    }
}

