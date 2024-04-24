package com.example.smsbankinganalitics.view_models.services

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smsbankinganalitics.view_models.utils.PermissionGrantedResultCallback


object PermissionListener {
    fun onRequestPermissionsResult(
        context: Context,
        callBack:PermissionGrantedResultCallback
    ) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED
            && (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS),
                1
            )
        } else {
            callBack.invoke()
        }
    }
}

