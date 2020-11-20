package com.example.passwordmanager.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class Password(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val name:String,
    val email:String,
    val encPwd:String,
    val uid:String
)