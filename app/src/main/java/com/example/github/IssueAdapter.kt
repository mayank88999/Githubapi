package com.example.github

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class IssueAdapter : RecyclerView.Adapter<IssueAdapter.IssueViewHolder>() {
    private val issueList: MutableList<IssueItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.issue_item_layout, parent, false)
        return IssueViewHolder(view)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val currentIssue = issueList[position]

        holder.titleTextView.text = currentIssue.title
        holder.createdAtTextView.text = currentIssue.created_at
        holder.closedAtTextView.text = currentIssue.closed_at ?: "Not Closed"
        holder.userNameTextView.text = currentIssue.user.login

        // Load user image using Picasso, Glide, or any image loading library
        // For example, with Picasso:
        Picasso.get().load(currentIssue.user.avatar_url).into(holder.userImageView)
    }

    override fun getItemCount(): Int {
        return issueList.size
    }

    fun addAllIssue(issues: List<IssueItem>) {
        issueList.clear()
        issueList.addAll(issues)
        notifyDataSetChanged()
    }



    inner class IssueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titltextView)
        val createdAtTextView: TextView = itemView.findViewById(R.id.createdAttextView)
        val closedAtTextView: TextView = itemView.findViewById(R.id.closedAttextView)
        val userNameTextView: TextView = itemView.findViewById(R.id.userNametextView)
        val userImageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}
