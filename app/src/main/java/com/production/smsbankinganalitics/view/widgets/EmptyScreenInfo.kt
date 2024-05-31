package com.production.smsbankinganalitics.view.widgets


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.production.smsbankinganalitics.R
import com.production.smsbankinganalitics.view_models.UiEffectsViewModel
import com.production.smsbankinganalitics.view_models.utils.Localization

@Composable
fun EmptyScreenInfo(resId: Int) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .size(180.dp),
            imageVector = ImageVector.vectorResource(resId),
            contentDescription = "no data",
            tint = MaterialTheme.colorScheme.secondary.copy(0.7f)
        )
        Text(
            text = Localization.withComposable(R.string.empty_screen),
            modifier = Modifier.padding(vertical = 8.dp),
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.secondary
            ),
            fontSize = 20.sp,
        )
    }
}


@Composable
fun EmptyShimmerBrushCard(uiEffectsViewModel: UiEffectsViewModel) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .size(62.dp)
                .clip(
                    RoundedCornerShape(10.dp)
                )
                .background(
                    uiEffectsViewModel.shimmerBrush(
                        Color.Gray,
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .size(31.dp)
                .clip(
                    RoundedCornerShape(10.dp)
                )
                .background(
                    uiEffectsViewModel.shimmerBrush(
                        Color.Gray,
                    )
                )
        )
    }
}

@Composable
fun EmptyShimmerBrushCardList(uiEffectsViewModel: UiEffectsViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(8) { index ->
            key(index) {
                EmptyShimmerBrushCard(uiEffectsViewModel)
            }
        }
    }
}