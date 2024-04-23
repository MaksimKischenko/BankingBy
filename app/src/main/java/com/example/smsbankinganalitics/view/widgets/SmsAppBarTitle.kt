package com.example.smsbankinganalitics.view.widgets


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.view_models.SmsReceiverState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SmsAppBarTitle(
    smsReceiverViewModelState: SmsReceiverState
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.70f)
            .clip(
                RoundedCornerShape(10.dp)
            )
            .background(
                MaterialTheme.colorScheme.secondary.copy(0.5f)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(8.dp),
        ) {
            RotatingIcon(smsReceiverViewModelState)
//            Icon(
//                modifier = Modifier
//                    .size(24.dp),
//                imageVector = ImageVector.vectorResource(id = R.drawable.period),
//                contentDescription = "period",
//                tint = MaterialTheme.colorScheme.tertiary
//            )
            Text(
                "${smsReceiverViewModelState.smsCommonInfo?.dateFrom} ",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Normal
                )
            )
        }

    }
}

@Composable
fun RotatingIcon(
    smsReceiverViewModelState: SmsReceiverState
) {
    val scope = rememberCoroutineScope()
    val rotationState = remember { Animatable(36f) }
    val rotationSpec = remember {
        infiniteRepeatable<Float>(
            animation = tween(500, easing = LinearEasing),
        )
    }
    SideEffect {
        if (smsReceiverViewModelState.isLoading) {

            scope.launch {

                rotationState.animateTo(
                    targetValue = 360f,
                    animationSpec = rotationSpec
                )
                delay(100)
            }

        } else {
            scope.launch {
                rotationState.snapTo(0f)
                rotationState.stop()
            }
        }
    }

    Box(
        modifier = Modifier.size(24.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .rotate(rotationState.value),
            imageVector = ImageVector.vectorResource(id = R.drawable.period),
            contentDescription = "period",
            tint = MaterialTheme.colorScheme.tertiary
        )
    }


}