package com.example.themixologist.feature.cocktail_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.themixologist.R
import com.example.themixologist.core.base.BaseFragment
import com.example.themixologist.core.base.BaseViewModel.UiEvent
import com.example.themixologist.databinding.FragmentCocktailDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CocktailDetailsFragment :
    BaseFragment<FragmentCocktailDetailsBinding, CocktailDetailsViewModel>(
        R.layout.fragment_cocktail_list
    ) {

    // Hilt provides the ViewModel (which already grabbed the ID via SavedStateHandle!)
    override val viewModel: CocktailDetailsViewModel by viewModels()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCocktailDetailsBinding.inflate(inflater, container, false)

    private val ingredientAdapter by lazy { IngredientAdapter() }
    private val instructionAdapter by lazy { InstructionAdapter() }

    override fun initViews() {
        setupToolbar()

        binding.rvIngredients.adapter = ingredientAdapter
        binding.rvInstructions.adapter = instructionAdapter

        binding.fabFavorite.setOnClickListener {
            viewModel.toggleFavorite()
        }
    }

    private fun setupToolbar() {
        // Setup the back button to use the Navigation Component
        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            // repeatOnLifecycle ensures we only collect data when the UI is visible
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.state.collect { state ->

                    binding.fabFavorite.setImageResource(R.drawable.favorite)
                    binding.fabFavorite.imageTintList = android.content.res.ColorStateList.valueOf(
                        androidx.core.content.ContextCompat.getColor(
                            requireContext(),
                            if (state.isFavorite) R.color.favorite_active
                            else R.color.favorite_inactive
                        )
                    )

                    state.cocktail?.let { cocktail ->

                        binding.cocktail = cocktail

                        Glide.with(this@CocktailDetailsFragment)
                            .load(cocktail.imageUrl)
                            .centerCrop()
                            .into(binding.ivHeaderImage)

                        // Update Adapters
                        ingredientAdapter.submitList(cocktail.ingredients)
                        instructionAdapter.submitList(cocktail.instructions)
                    }


                }
            }
        }

        // Collect One-Time Side Effects (like Toasts)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect { event ->
                    when (event) {
                        is UiEvent.ShowSnackbar -> {
                            Toast.makeText(requireContext(), event.message, Toast.LENGTH_LONG).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}