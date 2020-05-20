package com.pustovit.marsrealestate.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pustovit.marsrealestate.R
import com.pustovit.marsrealestate.databinding.GridViewItemBinding
import com.pustovit.marsrealestate.network.MarsProperty

class PhotoGridAdapter(val clickListener: MarsPropertyClickListener) :
    ListAdapter<MarsProperty, PhotoGridAdapter.MarsPropertyViewHolder>(
        MarsPropertyDiffUtilItemCallback
    ) {

    companion object MarsPropertyDiffUtilItemCallback : DiffUtil.ItemCallback<MarsProperty>() {
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem == newItem
        }

    }

    override fun submitList(list: List<MarsProperty>?) {
        super.submitList(list)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPropertyViewHolder {
        return MarsPropertyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.bind(marsProperty, clickListener)
    }


    class MarsPropertyViewHolder private constructor(private val binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            marsProperty: MarsProperty,
            clickListener: MarsPropertyClickListener
        ) {
            binding.property = marsProperty
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MarsPropertyViewHolder {
                val binding = DataBindingUtil.inflate<GridViewItemBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.grid_view_item,
                    parent,
                    false
                )
                return MarsPropertyViewHolder(binding)
            }
        }

    }

}

class MarsPropertyClickListener(val clickListener: (marsProperty: MarsProperty) -> Unit) {

    fun onClick(marsProperty: MarsProperty) {
        clickListener.invoke(marsProperty)
    }
}

