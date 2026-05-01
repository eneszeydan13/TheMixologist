package com.example.themixologist.feature.cocktail_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.themixologist.databinding.ItemInstructionBinding

class InstructionAdapter : ListAdapter<String, InstructionAdapter.InstructionViewHolder>(DiffCallback) {

    inner class InstructionViewHolder(private val binding: ItemInstructionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(instruction: String, position: Int) {
            binding.tvStepNumber.text = (position + 1).toString()
            binding.tvInstructionText.text = instruction
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionViewHolder {
        val binding = ItemInstructionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return InstructionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InstructionViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
