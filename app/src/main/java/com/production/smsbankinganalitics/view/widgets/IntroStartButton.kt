package com.production.smsbankinganalitics.view.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.production.smsbankinganalitics.R
import com.production.smsbankinganalitics.view_models.utils.Localization
import com.production.smsbankinganalitics.view_models.utils.Navigation

@Composable
fun IntroStartButton(
    navHostController:NavHostController
) {
    ElevatedButton(
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 10.dp,
        ),
        onClick = {
            Navigation.goToSmsBanking(navHostController, isFirstLoad = true)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.elevatedButtonColors().copy(
            containerColor = MaterialTheme.colorScheme.tertiary.copy(0.9f)
        )
    ) {
        Text(
            Localization.withComposable(R.string.start_button),
            modifier = Modifier.padding(vertical = 4.dp),
            style = TextStyle(
                color = MaterialTheme.colorScheme.onTertiary,
                textAlign = TextAlign.Center
            ),
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
        )
    }
}