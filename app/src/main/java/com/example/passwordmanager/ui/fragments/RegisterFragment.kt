package com.example.passwordmanager.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.passwordmanager.ui.viewmodels.LoginViewModel
import com.example.passwordmanager.ui.viewmodels.LoginViewModelFactory
import com.example.passwordmanager.R
import com.example.passwordmanager.others.Resource
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var viewModel: LoginViewModel



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this, LoginViewModelFactory(requireActivity().application)).get(
            LoginViewModel::class.java)


        register.setOnClickListener {
            val email=emailReg.text.toString()
            val pwd=passReg.text.toString()
            val cnfp=cnfpwd.text.toString()
            viewModel.register(email,pwd,cnfp)
        }


        viewModel.registerResult.observe(viewLifecycleOwner,  {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    val user = it.data
                    val bundle = Bundle().apply {
                        putParcelable("user", user)
                    }
                    Navigation.findNavController(view)
                        .navigate(R.id.action_registerFragment_to_passwordListFragment, bundle)
                }
                is Resource.Error -> {
                    hideProgressBar()
                    val msg = it.message
                    Toast.makeText(this.context, msg, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }


    private fun showProgressBar(){
        progressBar2.visibility=View.VISIBLE
    }

    private fun hideProgressBar(){
        progressBar2.visibility=View.INVISIBLE
    }


}