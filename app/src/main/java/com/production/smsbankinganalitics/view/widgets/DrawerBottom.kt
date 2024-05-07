package com.production.smsbankinganalitics.view.widgets


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.production.smsbankinganalitics.R
import com.production.smsbankinganalitics.view_models.utils.Localization

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun DrawerBottom(
    context: Context = LocalContext.current
) {
    val packageManager = remember {
        context.applicationContext.packageManager
    }
    val packageName = remember {
        context.applicationContext.packageName
    }
    val versionCode = remember {
        packageManager.getPackageInfo(packageName, 0).longVersionCode
    }
    val versionName = remember {
        packageManager.getPackageInfo(packageName, 0).versionName
    }

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .padding(4.dp),
            imageVector = ImageVector.vectorResource(R.drawable.version),
            contentDescription = "version",
            tint = MaterialTheme.colorScheme.tertiary
        )
        Text(
            text = "${Localization.withComposable(R.string.version)} ${versionCode}.$versionName", //stringResource(R.string.app_name),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.tertiary,
            ),
        )
    }
}