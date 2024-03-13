package com.example.taskrestapi

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class RecyclerView_Adapter( val list: List<JsonApiResponse_DataClass>) : RecyclerView.Adapter<RecyclerView_Adapter.MyViewHolder>()  {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val user = view.findViewById<TextView>(R.id.userid)
        val id = view.findViewById<TextView>(R.id.id)
        val title = view.findViewById<TextView>(R.id.title)
        val body = view.findViewById<TextView>(R.id.body)
        //val deleteButton=view.findViewById<Button>(R.id.button)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView_Adapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView_Adapter.MyViewHolder, position: Int) {
        val currentItem=list[position]

        holder.user.text = "User Id: ${currentItem.userId.toString()}"
        holder.id.text = "Id: ${currentItem.id.toString()}"
        holder.title.text = "Title: ${currentItem.title}"
        holder.body.text = "Body: ${currentItem.body}"
    }

    override fun getItemCount(): Int {
        return list.size
    }
}