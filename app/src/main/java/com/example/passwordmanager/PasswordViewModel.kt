package com.example.passwordmanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PasswordViewModel(passwordRepository: PasswordRepository, app: Application) :AndroidViewModel(app){

    private val context=getApplication<Application>().applicationContext
    private val _passwords:MutableLiveData<Password> = MutableLiveData()

    val passwords:LiveData<Password>
        get() = _passwords


}