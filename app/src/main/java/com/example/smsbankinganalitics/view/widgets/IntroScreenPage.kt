package com.example.smsbankinganalitics.view.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.view_models.utils.Navigation
import com.example.smsbankinganalitics.view_models.utils.Localization
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroScreenPage(
    navHostController: NavHostController,
    pagerState: PagerState,
    iconResId: Int,
    header: String,
    description: String,
) {
    val scope = rememberCoroutineScope()
    val targetPage = pagerState.targetPage

    ElevatedCard(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onTertiary)
            .fillMaxSize(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onTertiary)
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (icon, headerEl, descriptionEl, pageIndicator, nextButton, skipButton) = createRefs()
                val bottomGuideLine = createGuidelineFromBottom(0.5F)
                Box(
                    modifier = Modifier
                        .constrainAs(icon) {
                            bottom.linkTo(bottomGuideLine)
                            top.linkTo(parent.top)
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
                            top.linkTo(headerEl.bottom, (16).dp)
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
                Box(
                    modifier = Modifier
                        .constrainAs(pageIndicator) {
                            top.linkTo(descriptionEl.bottom, (16).dp)
                            start.linkTo(descriptionEl.start)
                            end.linkTo(descriptionEl.end)
                        },
                ) {
                    PageIndicator(pagerState, this, 12)
                }
                if (targetPage != 2) {
                    Button(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(nextButton) {
                                top.linkTo(pageIndicator.bottom, (16).dp)
                                start.linkTo(pageIndicator.start)
                                end.linkTo(pageIndicator.end)
                            },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.elevatedButtonColors().copy(
                            containerColor = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        Text(
                            Localization.withComposable(R.string.next_button),
                            modifier = Modifier.padding(vertical = 4.dp),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onTertiary,
                                textAlign = TextAlign.Center
                            ),
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                        )
                    }
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(skipButton) {
                                top.linkTo(nextButton.bottom, (8).dp)
                                start.linkTo(nextButton.start)
                                end.linkTo(nextButton.end)
                            },
                        onClick = {
                            Navigation.goToSmsBanking(navHostController, isFirstLoad = true)
                        }) {
                        Text(
                            Localization.withComposable(R.string.scip_button),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.tertiary.copy(0.5f),
                                textAlign = TextAlign.Center
                            ),
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                        )
                    }
                } else {
                    Button(
                        onClick = {
                            Navigation.goToSmsBanking(navHostController, isFirstLoad = true)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(nextButton) {
                                top.linkTo(pageIndicator.bottom, (16).dp)
                                start.linkTo(pageIndicator.start)
                                end.linkTo(pageIndicator.end)
                            },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.elevatedButtonColors().copy(
                            containerColor = MaterialTheme.colorScheme.tertiary
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
            }
        }
    }
}

