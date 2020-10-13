package com.example.passwordmanager

import android.app.Application
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(app:Application) :AndroidViewModel(app){
    private val context=getApplication<Application>().applicationContext
    private val TAG="LoginViewModel"

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }


    private val _loginResult:MutableLiveData<Resource<FirebaseUser>> = MutableLiveData()
    val loginResult:LiveData<Resource<FirebaseUser>>
        get() = _loginResult
    private val _registerResult:MutableLiveData<Resource<FirebaseUser>> = MutableLiveData()
    val registerResult:LiveData<Resource<FirebaseUser>>
        get() = _registerResult

    fun login(email:String,password: String){


        _loginResult.postValue(Resource.Loading())
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {task->
                    if (task.isSuccessful){
                        val user =auth.currentUser
                        Timber.d("Logged in Successfully ${user?.uid}")
                        _loginResult.postValue(Resource.Success(user!!))
                    }else{
                        Timber.e("Authentication Failure")
                        _loginResult
                    }
                }
        }


    }



    fun logout(){
        auth.signOut()
        //todo update ui
    }

    fun register(email:String,password: String,cnfPassword: String){


        if(validation(email,password,cnfPassword)){
            viewModelScope.launch {
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener {task->
                        if(task.isSuccessful){
                            Timber.d("User Create ${auth.currentUser?.displayName}")
                            val user=auth.currentUser
                            _registerResult.postValue(Resource.Success(user!!))
                        }else{
                            Timber.e("Failed to Create User")
                            _registerResult.postValue(Resource.Error("Failed to Create User"))
                        }
                    }
            }
        }else{
            _registerResult.value=Resource.Error("Invalid Credentials")
            Timber.e("Invalid Credentials")
        }




    }

    private fun validation(email: String,password: String,cnfPassword: String?):Boolean{

        if(!email.isValidEmail()){
            return false
        }
        if (cnfPassword!=null){
            if(password!=cnfPassword){
                return false
            }
        }
        if(password.length<6){
            return false
        }

        return true

    }

    private fun CharSequence?.isValidEmail()=!isNullOrBlank()&&Patterns.EMAIL_ADDRESS.matcher(this).matches()




}