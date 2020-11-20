package com.example.passwordmanager.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.passwordmanager.ui.viewmodels.PasswordViewModelFactory
import com.example.passwordmanager.R
import com.example.passwordmanager.adapters.PasswordRecyclerAdapter
import com.example.passwordmanager.db.Password
import com.example.passwordmanager.ui.viewmodels.PasswordViewModel
import kotlinx.android.synthetic.main.add_dialog.view.*
import kotlinx.android.synthetic.main.fragment_password_list.*
import timber.log.Timber


class PasswordListFragment : Fragment(R.layout.fragment_password_list) {

    private lateinit var viewModel: PasswordViewModel
    private lateinit var passwordAdapter: PasswordRecyclerAdapter
    private val regArgs:RegisterFragmentArgs by navArgs()
    private val logArgs:LoginFragmentArgs by navArgs()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavView.background=null
        bottomNavView.menu.getItem(2).isEnabled=false
        viewModel=ViewModelProvider(this, PasswordViewModelFactory(requireActivity().application)).get(
            PasswordViewModel::class.java)
        recyclerSetup()
        viewModel.passwords.observe(viewLifecycleOwner, {
            passwordAdapter.differ.submitList(it)
        })
        
        fab.setOnClickListener {

            val dialogBuilder=AlertDialog.Builder(this.context)
            dialogBuilder.setTitle("Enter Details")
            val view=layoutInflater.inflate(R.layout.add_dialog,null)
            dialogBuilder.setView(view)

            val alertDialog=dialogBuilder.create()
            alertDialog.show()
            view.cancelD.setOnClickListener {
                alertDialog.cancel()
            }
            view.enterD.setOnClickListener {
                val name=view.editTextTextPersonName.text.toString()
                val email=view.editTextTextEmailAddress.text.toString()
                val pass=view.editTextTextPassword.text.toString()
                val password= Password(email = email,encPwd = pass,name = name,uid = getUserUID())
                Timber.d("Input"+name+" "+email+" "+pass)

                viewModel.insert(password)
                alertDialog.cancel()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.pass_menu,menu)
    }



    private fun recyclerSetup(){
        passwordAdapter= PasswordRecyclerAdapter()
        recycler_view.apply {
            adapter=passwordAdapter
            layoutManager=LinearLayoutManager(activity)
        }

    }

    private fun getUserUID():String{
        if(regArgs.user==null){
            return logArgs.user.uid
        }else{
            return regArgs.user.uid
        }
    }


}

