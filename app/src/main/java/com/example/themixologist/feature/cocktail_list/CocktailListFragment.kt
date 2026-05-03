package com.example.themixologist.feature.cocktail_list

import com.example.themixologist.core.base.BaseFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.themixologist.R
import com.example.themixologist.databinding.FragmentCocktailListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CocktailListFragment : BaseFragment<FragmentCocktailListBinding, CocktailListViewModel>(
    R.layout.fragment_cocktail_list
) {
    override val viewModel: CocktailListViewModel by viewModels()

    private val adapter = CocktailAdapter { cocktail ->
        val action = CocktailListFragmentDirections.actionListToDetail(cocktailId = cocktail.id)
        findNavController().navigate(action)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCocktailListBinding.inflate(inflater, container, false)

    override fun initViews() {
        binding.recyclerView.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.search(it) }
                return true
            }
        })
    }

    override fun observeState() {
        super.observeState()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.progressBar.isVisible = state.isLoading

                    adapter.submitList(state.cocktails)

                    binding.tvError.isVisible = state.error != null
                    binding.tvError.text = state.error
                }
            }
        }
    }
}