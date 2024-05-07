package com.example.smsbankinganalitics.view.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun IntroScreenPage(
    iconResId: Int,
    header: String,
    description: String,
) {
    ElevatedCard(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onTertiary)
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 48.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            val (icon, headerEl, descriptionEl) = createRefs()
            val bottomGuideLine = createGuidelineFromBottom(0.5F)
            Box(
                modifier = Modifier
                    .constrainAs(icon) {
                        bottom.linkTo(bottomGuideLine)
                        top.linkTo(parent.top, (16).dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
                    .background(
                        MaterialTheme.colorScheme.primary.copy(0.4f)
                    )
            ) {
                Icon(
                    modifier = Modifier
                        .size(240.dp),
                    imageVector = ImageVector.vectorResource(iconResId),
                    contentDescription = "$iconResId",
                    tint =  MaterialTheme.colorScheme.tertiary
                )
            }
            Text(
                header,
                modifier = Modifier.constrainAs(headerEl) {
                    top.linkTo(icon.bottom, (16).dp)
                    start.linkTo(icon.start)
                    end.linkTo(icon.end)
                },
                style = TextStyle(
                    color = MaterialTheme.colorScheme.tertiary,
                    textAlign = TextAlign.Center
                ),
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
            )
            Text(
                description,
                modifier = Modifier
                    .constrainAs(descriptionEl) {
                        top.linkTo(headerEl.bottom, (32).dp)
                        start.linkTo(headerEl.start)
                        end.linkTo(headerEl.end)
                    },
                style = TextStyle(
                    color = MaterialTheme.colorScheme.tertiary.copy(0.5f),
                    textAlign = TextAlign.Center
                ),
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
            )
        }
    }
}

