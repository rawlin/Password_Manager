package com.example.passwordmanager

import android.app.Application
import timber.log.Timber

class PasswordApplication :Application(){
    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }


    }
}