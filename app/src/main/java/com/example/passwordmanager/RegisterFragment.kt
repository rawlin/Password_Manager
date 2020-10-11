package com.example.passwordmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var viewModel: LoginViewModel



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this,LoginViewModelFactory(requireActivity().application)).get(LoginViewModel::class.java)

        viewModel.registerResult.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success->{
                    val user=it.data
                    val bundle=Bundle().apply {
                        putParcelable("user",user)
                    }
                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_passwordListFragment,bundle)
                }
                is Resource.Error->{
                    val msg=it.message
                    Toast.makeText(this.context,msg,Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}