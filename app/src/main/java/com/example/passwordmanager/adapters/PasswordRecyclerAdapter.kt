package com.example.passwordmanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanager.R
import com.example.passwordmanager.db.Password
import kotlinx.android.synthetic.main.password_item.view.*

class PasswordRecyclerAdapter :RecyclerView.Adapter<PasswordRecyclerAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    private val differCallback=object:DiffUtil.ItemCallback<Password>(){
        override fun areItemsTheSame(oldItem: Password, newItem: Password): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Password, newItem: Password): Boolean {
            return oldItem==newItem
        }
    }


    val differ=AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.password_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val password=differ.currentList[position]
        holder.itemView.apply {
            name.text=password.name
            email.text=password.email
            pass.text=password.encPwd
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}