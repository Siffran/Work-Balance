package com.example.workbalance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.email_list_item.view.*


class CustomEmailAdapter(private val emailList: List<EmailItem>): RecyclerView.Adapter<CustomEmailAdapter.CustomViewHolder>() {

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.imageViewOther
        val imageView2: ImageView = itemView.imageViewSquare
        val imageView3: ImageView = itemView.image_background
        val textView: TextView = itemView.textViewSender
        val textView2: TextView = itemView.textViewTopic
        val textView3: TextView = itemView.textViewContent
        val textView4: TextView = itemView.textViewSquareLetter
    }

    override fun getItemCount() = emailList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.email_list_item, parent, false)

        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = emailList[position]

    }
}