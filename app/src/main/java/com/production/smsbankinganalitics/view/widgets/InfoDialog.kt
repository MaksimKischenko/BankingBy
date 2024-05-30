package com.production.smsbankinganalitics.view.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.production.smsbankinganalitics.R
import com.production.smsbankinganalitics.view_models.utils.Localization

@Composable
fun InfoDialog(
    openDialog: MutableState<Boolean>
) {
    when {
        openDialog.value -> {
            Dialog(
                onDismissRequest = {
                    openDialog.value = false
                }
            ) {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(8.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.tertiary)
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Image(
                            painterResource(id = R.drawable.banking),
                            contentDescription = "banking",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(vertical = 4.dp)
                        )
                        Text(
                            text = Localization.withComposable(R.string.info_dialog_example),
                            modifier = Modifier.padding(vertical = 8.dp),
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onTertiary
                            ),
                            fontSize = 20.sp,
                        )
                    }
                }
            }
        }
    }
}