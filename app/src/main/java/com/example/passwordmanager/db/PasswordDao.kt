package com.example.passwordmanager.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PasswordDao {
    @Insert
    suspend fun insert(password: Password)

    @Query("SELECT * FROM users_table ")
    fun getAll():LiveData<List<Password>>

    @Delete
    suspend fun delete(password: Password)
}

