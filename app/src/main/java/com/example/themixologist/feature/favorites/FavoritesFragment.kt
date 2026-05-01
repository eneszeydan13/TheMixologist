package com.example.themixologist.feature.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.themixologist.R
import com.example.themixologist.core.base.BaseFragment
import com.example.themixologist.databinding.FragmentFavoritesBinding
import com.example.themixologist.feature.cocktail_list.CocktailAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>(
    R.layout.fragment_favorites
) {
    override val viewModel: FavoritesViewModel by viewModels()

    private val adapter = CocktailAdapter { cocktail ->
        val action = FavoritesFragmentDirections.actionFavoritesToDetail(cocktailId = cocktail.id)
        findNavController().navigate(action)
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentFavoritesBinding.inflate(inflater, container, false)

    override fun initViews() {
        binding.recyclerView.adapter = adapter
    }

    override fun observeState() {
        super.observeState()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteCocktails.collect { favorites ->
                    binding.tvEmpty.isVisible = favorites.isEmpty()
                    adapter.submitList(favorites)
                }
            }
        }
    }
}
