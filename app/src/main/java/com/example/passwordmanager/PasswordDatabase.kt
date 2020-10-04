package com.example.passwordmanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.passwordmanager.database.PasswordDao

@Database(entities = [Password::class],version = 1,exportSchema = false)
abstract class PasswordDatabase:RoomDatabase(){

    abstract fun passwordDao():PasswordDao

    companion object{
        @Volatile
        private var INSTANCE:PasswordDatabase?=null

        fun getInstance(context: Context):PasswordDatabase{
            synchronized(this){
                var instance= INSTANCE

                if(instance==null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        PasswordDatabase::class.java,
                        "password_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE=instance
                }
                return instance
            }
        }
    }
}