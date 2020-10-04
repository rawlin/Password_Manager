package com.example.passwordmanager

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_password_list.*


class PasswordListFragment : Fragment(R.layout.fragment_password_list) {

    private lateinit var viewModel: PasswordViewModel
    private lateinit var passwordAdapter: PasswordRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.pass_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.genpwd->{
                true
            }
            R.id.add->{
                true
            }
            R.id.rmv->{
                true
            }

            else->super.onOptionsItemSelected(item)
        }

    }

    private fun recyclerSetup(){
        passwordAdapter= PasswordRecyclerAdapter()
        recycler_view.apply {
            adapter=passwordAdapter
            layoutManager=LinearLayoutManager(activity)
        }
    }
}