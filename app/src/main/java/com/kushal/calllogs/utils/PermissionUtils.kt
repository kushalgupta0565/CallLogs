package com.kushal.calllogs.utils

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.kushal.calllogs.MainActivity
import com.kushal.calllogs.callbacks.IPermissionCallback

class PermissionUtils(val activity: Activity, val callback: IPermissionCallback) {

    @TargetApi(Build.VERSION_CODES.M)
    fun checkPermissionToExecute(permissions: Array<String>, requestCode: Int) {
        val logs = ContextCompat.checkSelfPermission(
            activity.applicationContext,
            permissions[0]
        ) !== PackageManager.PERMISSION_GRANTED
        val contacts = ContextCompat.checkSelfPermission(
            activity.applicationContext,
            permissions[1]
        ) !== PackageManager.PERMISSION_GRANTED
        val call = ContextCompat.checkSelfPermission(
            activity.applicationContext,
            permissions[2]
        ) !== PackageManager.PERMISSION_GRANTED
        if (logs || contacts || call) {
            requestPermissions(activity, permissions, requestCode)
        } else {
            callback.onAllPermissionGranted()
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        if (requestCode == MainActivity.READ_LOGS && permissions[0] == Manifest.permission.READ_CALL_LOG && permissions[1] == Manifest.permission.READ_CONTACTS
        ) {
            if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED && grantResults[1] == PermissionChecker.PERMISSION_GRANTED && grantResults[2] == PermissionChecker.PERMISSION_GRANTED) {
                callback.onAllPermissionGranted()
            } else {
                AlertDialog.Builder(activity)
                    .setMessage("The app needs these permissions to work, Exit?")
                    .setTitle("Permission Denied")
                    .setCancelable(false)
                    .setPositiveButton(
                        "Retry",
                         { dialog, which ->
                            dialog.dismiss()
                             callback.onRetry()

                        })
                    .setNegativeButton(
                        "Exit App",
                         { dialog, which ->
                            dialog.dismiss()
                             callback.onExit()
                        }).show()
            }
        }
    }
}