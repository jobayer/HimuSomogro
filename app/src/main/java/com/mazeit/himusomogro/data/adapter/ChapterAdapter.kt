package com.mazeit.himusomogro.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mazeit.himusomogro.data.listener.ChapterOnClickListener
import com.mazeit.himusomogro.data.models.Chapter
import com.mazeit.himusomogro.databinding.LayoutItemChapterBinding

class ChapterAdapter(private val onChapterClickListener: ChapterOnClickListener) : ListAdapter<Chapter, ChapterAdapter.ChapterViewHolder>(ChapterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        return ChapterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        holder.bind(getItem(position), onChapterClickListener)
    }

    class ChapterViewHolder private constructor(private val binding: LayoutItemChapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Chapter, listener: ChapterOnClickListener) {
            binding.chapter = item
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {

            fun from(parent: ViewGroup): ChapterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemChapterBinding.inflate(layoutInflater, parent, false)
                return ChapterViewHolder(binding)
            }

        }

    }

    class ChapterDiffCallback : DiffUtil.ItemCallback<Chapter>() {

        override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
            return oldItem == newItem
        }

    }

}