package com.example.rtd_android_xml_lab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class JSONPlaceholderPostsAdapter(private val posts: List<JSONPlaceholderPostModel>) : RecyclerView.Adapter<JSONPlaceholderPostsAdapter.JSONPlaceholderPostViewHolder>() {

    class JSONPlaceholderPostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: android.widget.TextView = view.findViewById(R.id.card_view_title)
        val bodyTextView: android.widget.TextView = view.findViewById(R.id.card_view_body)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JSONPlaceholderPostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view, parent, false)
        return JSONPlaceholderPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: JSONPlaceholderPostViewHolder, position: Int) {
        val post = posts[position]
        holder.titleTextView.text = post.title
        holder.bodyTextView.text = post.body
    }

    override fun getItemCount() = posts.size
}