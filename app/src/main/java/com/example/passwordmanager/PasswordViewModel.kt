package com.example.passwordmanager

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.PasswordDatabase.Companion.getInstance
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import timber.log.Timber

class PasswordViewModel( application: Application) :AndroidViewModel(application){

    private val database= getInstance(application)
    private val repository=PasswordRepository(database)
    private val context=getApplication<Application>().applicationContext


    val passwords:LiveData<List<Password>>


    init {
        passwords=repository.passwords
    }

    fun insert(password: Password)=viewModelScope.launch(IO){

        repository.insert(password)
        Timber.d("Inserted ${password.name}")

    }

    fun delete(password: Password){
        viewModelScope.launch(IO) {
            repository.delete(password)
        }

        Timber.d("Deleted user")
    }



}