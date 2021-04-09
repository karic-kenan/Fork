/*
 * Created by Karic Kenan on 9.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.fork.ui.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import io.aethibo.domain.Repository
import io.aethibo.fork.R

class RepositoriesAdapter : ListAdapter<Repository, RepositoriesAdapter.RepositoryViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                oldItem.hashCode() == newItem.hashCode()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder =
            RepositoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false))

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    inner class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(repository: Repository) = with(itemView) {

            /**
             * Init views
             */
            val name = itemView.findViewById<TextView>(R.id.tvRepoName)
            val description = itemView.findViewById<TextView>(R.id.tvRepoDescription)
            val language = itemView.findViewById<TextView>(R.id.tvRepoLanguage)
            val updatedDate = itemView.findViewById<TextView>(R.id.tvRepoUpdated)
            val forksCount = itemView.findViewById<TextView>(R.id.tvForksCount)
            val watchersCount = itemView.findViewById<TextView>(R.id.tvWatchersCount)
            val stargazersCount = itemView.findViewById<TextView>(R.id.tvStargazersCount)
            val lock = itemView.findViewById<ImageView>(R.id.ivRepoLock)

            /**
             * Init values
             */
            name.text = repository.name
            description.text = repository.description
            language.text = "Language: ${repository.language}"
            updatedDate.text = "Updated: ${repository.updatedAt}"
            forksCount.text = repository.forksCount.toString()
            watchersCount.text = repository.watchersCount.toString()
            stargazersCount.text = repository.stargazersCount.toString()

            when (repository.private) {
                true -> lock.load(R.drawable.ic_lock) {
                    crossfade(true)
                }
                false -> lock.load(R.drawable.ic_unlock) {
                    crossfade(true)
                }
            }
        }
    }
}