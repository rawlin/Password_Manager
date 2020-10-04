package com.example.passwordmanager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class Password(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val email:String,
    val encPwd:String,
    val uid:String
)