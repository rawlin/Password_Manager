package com.example.passwordmanager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var viewModel: LoginViewModel
    private lateinit var auth: FirebaseAuth
    private val TAG="LoginFragment"
    override fun onStart() {
        super.onStart()
        val currentUser=auth.currentUser
        if(currentUser!=null){
            //updateUI(currentuser)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= FirebaseAuth.getInstance()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(this,LoginViewModelFactory( requireActivity().application)).get(LoginViewModel::class.java)
        button.setOnClickListener {
            val id=emailLogin.text.toString()
            val pwd=passwordLogin.text.toString()
            viewModel.login(id,pwd)

        }

        button2.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        viewModel.loginResult.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success->{
                    hideProgressBar()
                    val user=it.data
                    val bundle=Bundle().apply {
                        putParcelable("user",user)
                    }
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_passwordListFragment,bundle)
                }
                is Resource.Error->{
                    hideProgressBar()
                    val msg=it.message
                    Log.e(TAG,msg!!)
                    Toast.makeText(this.context,msg,Toast.LENGTH_LONG).show()
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }
        })

    }


    private fun showProgressBar(){
        progressBar.visibility=View.VISIBLE
    }

    private fun hideProgressBar(){
        progressBar.visibility=View.INVISIBLE
    }
}