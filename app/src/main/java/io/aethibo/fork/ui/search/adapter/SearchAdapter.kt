/*
 * Created by Karic Kenan on 21.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.fork.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import io.aethibo.domain.Repository
import io.aethibo.fork.R

class SearchAdapter : ListAdapter<Repository, SearchAdapter.SearchViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(repository: Repository) = with(itemView) {

            /**
             * Init views
             */
            val avatar = findViewById<ImageView>(R.id.ivAvatar)
            val title = findViewById<TextView>(R.id.tvRepoName)
            val description = findViewById<TextView>(R.id.tvRepoDescription)
            val owner = findViewById<TextView>(R.id.tvRepoOwner)
            val language = findViewById<TextView>(R.id.tvRepoLanguage)
            val date = findViewById<TextView>(R.id.tvRepoUpdated)

            /**
             * Set values
             */
            avatar.load(repository.avatarUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }

            title.text = repository.name
            description.text = repository.description
            owner.text = context.getString(R.string.labelRepositoryOwner, repository.owner)
            repository.language?.let {
                language.isVisible = it.isNotEmpty()
                language.text = context.getString(R.string.labelRepositoryLanguage, it)
            }
            date.text = context.getString(R.string.labelRepositoryUpdatedDate, repository.updatedAt)

            setOnClickListener {
                onSearchItemClickListener?.let { click ->
                    click(repository)
                }
            }
        }
    }

    /**
     * Click listener
     */
    private var onSearchItemClickListener: ((Repository) -> Unit)? = null

    fun setOnSearchItemClickListener(listener: (Repository) -> Unit) {
        onSearchItemClickListener = listener
    }
}