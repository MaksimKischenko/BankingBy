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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.smsbankinganalitics.model.Navigation
import com.example.smsbankinganalitics.view.theme.Palette14
import com.example.smsbankinganalitics.view_models.utils.Localization
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroScreenPage(
    navHostController: NavHostController,
    pagerState: PagerState,
    iconResId: Int,
    headerId: Int,
    descriptionId: Int,
) {
    val scope = rememberCoroutineScope()
    val targetPage = pagerState.targetPage
    var isLastPage by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(pagerState.targetPage) {
        withContext(Dispatchers.Default) {
            if(targetPage != pagerState.pageCount-1)
                isLastPage = true
        }
    }

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
                val (icon, header, description, pageIndicator, nextButton, skipButton) = createRefs()
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
                            Palette14.copy(0.4f)
                        )
                ) {
                    Icon(
                        modifier = Modifier
                            .size(240.dp),
                        imageVector = ImageVector.vectorResource(iconResId),
                        contentDescription = "$iconResId",
                        tint = Palette14
                    )
                }
                Text(
                    Localization.withComposable(headerId),
                    modifier = Modifier.constrainAs(header) {
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
                    Localization.withComposable(descriptionId),
                    modifier = Modifier
                        .constrainAs(description) {
                            top.linkTo(header.bottom, (16).dp)
                            start.linkTo(header.start)
                            end.linkTo(header.end)
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
                            top.linkTo(description.bottom, (16).dp)
                            start.linkTo(description.start)
                            end.linkTo(description.end)
                        },
                ) {
                    PageIndicator(pagerState, this, 12)
                }
                if (isLastPage) {
                    Button(
                        onClick = {
                            scope.launch(Dispatchers.IO) {
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
                            navHostController.navigate(Navigation.SmsBanking.route) {
                                popUpTo(Navigation.SmsBanking.route) {
                                    inclusive = true
                                }
                            }
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
                            navHostController.navigate(Navigation.SmsBanking.route) {
                                popUpTo(Navigation.SmsBanking.route) {
                                    inclusive = true
                                }
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

