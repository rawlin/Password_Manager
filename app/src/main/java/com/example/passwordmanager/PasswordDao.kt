package com.example.passwordmanager.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.passwordmanager.Password

@Dao
interface PasswordDao {
    @Insert
    suspend fun insert(password:Password)

    @Query("SELECT * FROM users_table")
    fun getAll():LiveData<Password>

    @Delete
    suspend fun delete(password: Password)
}

