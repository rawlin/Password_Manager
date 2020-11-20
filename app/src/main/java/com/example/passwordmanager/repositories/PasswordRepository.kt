package com.example.passwordmanager.repositories

import androidx.lifecycle.LiveData
import com.example.passwordmanager.db.Password
import com.example.passwordmanager.db.PasswordDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PasswordRepository(private val database: PasswordDatabase) {

    val passwords:LiveData<List<Password>> = database.passwordDao().getAll()

    suspend fun insert(password: Password){
        withContext(Dispatchers.IO){
            database.passwordDao().insert(password)
        }
    }

    suspend fun delete(password: Password){
        withContext(Dispatchers.IO){
            database.passwordDao().delete(password)
        }
    }
}