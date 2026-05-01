package com.example.themixologist.feature.cocktail_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themixologist.databinding.ItemCocktailBinding
import com.example.themixologist.domain.model.Cocktail

class CocktailAdapter(
    private val onItemClick: (Cocktail) -> Unit
) : ListAdapter<Cocktail, CocktailAdapter.CocktailViewHolder>(DiffCallback) {

    // 1. Create the View Wrapper
    inner class CocktailViewHolder(private val binding: ItemCocktailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cocktail: Cocktail) {
            // Pass the data object to the XML
            binding.cocktail = cocktail

            // Manual image loading (or use BindingAdapter for cleaner code)
            Glide.with(itemView)
                .load(cocktail.imageUrl)
                .into(binding.ivCocktail)

            binding.root.setOnClickListener { onItemClick(cocktail) }

            // Forces the data binding to execute immediately
            binding.executePendingBindings()
        }
    }

    // 2. Inflate the XML
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        return CocktailViewHolder(
            ItemCocktailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    // 3. Bind Data
    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // 4. Calculate Diffs (For smooth animations)
    companion object DiffCallback : DiffUtil.ItemCallback<Cocktail>() {
        override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
            return oldItem == newItem
        }
    }
}