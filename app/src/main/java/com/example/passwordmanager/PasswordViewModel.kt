package com.example.passwordmanager

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.passwordmanager.PasswordDatabase.Companion.getInstance

class PasswordViewModel( application: Application) :AndroidViewModel(application){

    private val database= getInstance(application)
    private val repository=PasswordRepository(database)
    private val context=getApplication<Application>().applicationContext


    val passwords:LiveData<List<Password>>


    init {
        passwords=repository.passwords
    }

    suspend fun insert(password: Password){
        repository.insert(password)
        Toast.makeText(context,"Inserted for ${password.name}",Toast.LENGTH_SHORT).show()
    }

    suspend fun delete(password: Password){
        repository.delete(password)
        Toast.makeText(context,"Deleted for ${password.name}",Toast.LENGTH_SHORT).show()
    }



}