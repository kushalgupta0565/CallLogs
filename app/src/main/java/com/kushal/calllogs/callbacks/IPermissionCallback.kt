package com.kushal.calllogs.callbacks

interface IPermissionCallback {

    fun onAllPermissionGranted()

    fun onRetry()

    fun onExit()
}