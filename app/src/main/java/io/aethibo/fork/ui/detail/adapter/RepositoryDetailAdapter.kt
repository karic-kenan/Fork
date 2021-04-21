/*
 * Created by Karic Kenan on 21.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.fork.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import io.aethibo.domain.EventType
import io.aethibo.domain.response.RepositoryEventsResponse
import io.aethibo.fork.R

class RepositoryDetailAdapter : ListAdapter<RepositoryEventsResponse, RepositoryDetailAdapter.RepositoryDetailViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<RepositoryEventsResponse>() {
        override fun areItemsTheSame(
            oldItem: RepositoryEventsResponse,
            newItem: RepositoryEventsResponse
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: RepositoryEventsResponse,
            newItem: RepositoryEventsResponse
        ): Boolean = oldItem.hashCode() == newItem.hashCode()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryDetailViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_repo_event, parent, false)
        return RepositoryDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryDetailViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    inner class RepositoryDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(event: RepositoryEventsResponse) = with(itemView) {

            /**
             * Init views
             */
            val avatar = findViewById<ImageView>(R.id.tvRepoEventAvatar)
            val username = findViewById<TextView>(R.id.tvRepoEventUsername)
            val date = findViewById<TextView>(R.id.tvRepoEventDate)
            val title = findViewById<TextView>(R.id.tvRepoEventTitle)
            val description = findViewById<TextView>(R.id.tvRepoEventDescription)

            /**
             * Set values
             */
            avatar.load(event.actor.avatarUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }

            username.text = event.actor.displayLogin
            date.text = event.createdAt
            title.text = event.type

            when (event.type) {
                EventType.CreateEvent.name -> description.text = context.getString(
                    R.string.labelEventTypeCreate,
                    event.actor.displayLogin,
                    event.repo.name
                )
                EventType.IssueCommentEvent.name -> description.text = context.getString(
                    R.string.labelEventTypeIssueComment,
                    event.actor.displayLogin,
                    event.repo.name
                )
                EventType.IssuesEvent.name -> description.text = context.getString(
                    R.string.labelEventTypeIssue,
                    event.actor.displayLogin,
                    event.repo.name
                )
                EventType.PublicEvent.name -> description.text = context.getString(
                    R.string.labelEventTypePublic,
                    event.actor.displayLogin,
                    event.repo.name,
                    event.public.toString()
                )
                EventType.PushEvent.name -> description.text = context.getString(
                    R.string.labelEventTypePush,
                    event.actor.displayLogin,
                    event.repo.name
                )
                EventType.WatchEvent.name -> description.text = context.getString(
                    R.string.labelEventTypeWatch,
                    event.actor.displayLogin,
                    event.repo.name
                )
                EventType.ForkEvent.name -> description.text = context.getString(
                    R.string.labelEventTypeFork,
                    event.actor.displayLogin,
                    event.repo.name
                )
            }

            setOnClickListener {
                onEventClickListener?.let { click ->
                    click(event)
                }
            }
        }
    }

    /**
     * Click listeners
     */
    private var onEventClickListener: ((RepositoryEventsResponse) -> Unit)? = null

    fun setOnEventItemClickListener(listener: (RepositoryEventsResponse) -> Unit) {
        onEventClickListener = listener
    }
}