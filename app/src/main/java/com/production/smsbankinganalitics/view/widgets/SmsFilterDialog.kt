package com.production.smsbankinganalitics.view.widgets

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.production.smsbankinganalitics.R
import com.production.smsbankinganalitics.view_models.utils.Localization

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmsFilterDialog(
    onSelect: () -> Unit,
    dateState: DatePickerState,
    openDialog: MutableState<Boolean>
) {

    when {
        openDialog.value -> {
            DatePickerDialog(
                tonalElevation = 10.dp,
                onDismissRequest = {
                    openDialog.value = false

                },
                confirmButton = {
                    Button(
                        onClick = {
                            onSelect.invoke()
                            openDialog.value = false
                        },
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                            disabledContentColor = MaterialTheme.colorScheme.tertiary,
                        )
                    ) {
                        Text(Localization.withComposable(R.string.select))
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { openDialog.value = false },
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                            disabledContentColor = MaterialTheme.colorScheme.tertiary,
                        )
                    ) {
                        Text(Localization.withComposable(R.string.cancel))
                    }
                }
            ) {
                DatePicker(
                    state = dateState,
                    showModeToggle = true,
                )
            }
        }
    }
}

