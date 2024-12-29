package com.test.shopeeclone.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.shopeeclone.data.remote.response.ItemResponse
import com.test.shopeeclone.databinding.CardItemBinding

class HomeAdapter: ListAdapter<ItemResponse, HomeAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(private val binding: CardItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun binding(item: ItemResponse) {
            Glide.with(binding.root.context)
                .load(item.image)
                .into(binding.imgItem)
            binding.tvTitle.text = item.title
            binding.tvPrice.text = item.price

        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ItemResponse> =
            object : DiffUtil.ItemCallback<ItemResponse>() {
                override fun areItemsTheSame(
                    oldItem: ItemResponse,
                    newItem: ItemResponse
                ): Boolean {
                    return oldItem.title == newItem.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: ItemResponse,
                    newItem: ItemResponse
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(getItem(position))
    }
}