package com.example.smsbankinganalitics.view.widgets


import android.graphics.Shader
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.smsbankinganalitics.view_models.UiEffectsViewModel

@Composable
fun EmptyScreenInfo(resId: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .size(320.dp),
            imageVector = ImageVector.vectorResource(resId),
            contentDescription = "no data",
            tint = MaterialTheme.colorScheme.secondary.copy(0.7f)
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