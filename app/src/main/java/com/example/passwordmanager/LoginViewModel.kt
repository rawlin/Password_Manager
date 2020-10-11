package com.example.passwordmanager

import android.app.Application
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(app:Application) :AndroidViewModel(app){
    private val context=getApplication<Application>().applicationContext
    private val TAG="LoginViewModel"
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    val loginResult:MutableLiveData<Resource<FirebaseUser>> = MutableLiveData()
    val registerResult:MutableLiveData<Resource<FirebaseUser>> = MutableLiveData()

    fun login(email:String,password: String){

        if(!email.isValidEmail()&&password.isNullOrBlank()){
            Toast.makeText(context,"Enter Valid Email and Password",Toast.LENGTH_LONG).show()
        }else{
            loginResult.postValue(Resource.Loading())
            viewModelScope.launch {
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener {task->
                        if (task.isSuccessful){
                            Log.d(TAG,"Successful Login")
                            val user =auth.currentUser
                            loginResult.postValue(Resource.Success(user!!))
                        }else{
                            Log.w(TAG,"Authentication Failure")
                        }
                    }
            }
        }

    }

    fun logout(){
        auth.signOut()
        //todo update ui
    }

    fun register(email:String,password: String,cnfPassword: String){
        if(email.isValidEmail()&&isSamePassword(password,cnfPassword)){
            viewModelScope.launch {
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener {task->
                        if(task.isSuccessful){
                            Log.d(TAG,"Created user")
                            val user=auth.currentUser
                            registerResult.postValue(Resource.Success(user!!))
                        }else{
                            Log.w(TAG,"Failed to Create User")
                            registerResult.postValue(Resource.Error("Failed to Create User"))
                        }
                    }
            }
        }else if (!email.isValidEmail()){
            registerResult.postValue(Resource.Error(""))
        }else if(!isSamePassword(password,cnfPassword)){
            registerResult.postValue(Resource.Error("Enter Same Passwords"))
        }


    }

    private fun CharSequence?.isValidEmail()=!isNullOrBlank()&&Patterns.EMAIL_ADDRESS.matcher(this).matches()
    private fun CharSequence?.isGoodPassword()=!isNullOrBlank()&&length>6

    private fun isSamePassword(p1:String,p2:String)= p1==p2


}