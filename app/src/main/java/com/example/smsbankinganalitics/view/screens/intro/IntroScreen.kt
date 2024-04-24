package com.example.smsbankinganalitics.view.screens.intro


import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.smsbankinganalitics.view_models.IntroVewModel

@Composable
fun IntroScreen(
    navHostController: NavHostController,
    introVewModel: IntroVewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {

        }) { padding ->
        IntroScreenBody(padding, introVewModel, navHostController)
    }

}