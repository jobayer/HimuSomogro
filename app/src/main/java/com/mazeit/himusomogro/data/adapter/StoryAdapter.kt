package com.mazeit.himusomogro.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mazeit.himusomogro.data.listener.StoryOnClickListener
import com.mazeit.himusomogro.data.models.Story
import com.mazeit.himusomogro.databinding.LayoutItemStoryBinding

class StoryAdapter(private val onStoryClickListener: StoryOnClickListener): ListAdapter<Story, StoryAdapter.StoryViewHolder>(StoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        return StoryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(getItem(position), onStoryClickListener)
    }

    class StoryViewHolder private constructor(private val binding: LayoutItemStoryBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Story, listener: StoryOnClickListener) {
            binding.story = item
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {

            fun from(parent: ViewGroup): StoryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemStoryBinding.inflate(layoutInflater, parent, false)
                return StoryViewHolder(binding)
            }

        }

    }

    class StoryDiffCallback: DiffUtil.ItemCallback<Story>() {

        override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem == newItem
        }

    }

}