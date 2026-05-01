package com.example.themixologist.feature.cocktail_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.themixologist.R
import com.example.themixologist.databinding.FragmentCocktailDetailsBinding
import com.example.themixologist.databinding.ItemIngredientBinding
import com.example.themixologist.databinding.ItemInstructionBinding
import com.example.themixologist.feature.cocktail_details.CocktailDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class CocktailDetailsFragment : Fragment() {

    // Hilt provides the ViewModel (which already grabbed the ID via SavedStateHandle!)
    private val viewModel: CocktailDetailsViewModel by viewModels()

    // ViewBinding setup
    private var _binding: FragmentCocktailDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        observeState()
        
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

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            // repeatOnLifecycle ensures we only collect data when the UI is visible
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.state.collect { state ->

                    // 1. Handle Loading State
                    // (Omitted progress bar for layout simplicity)

                    // 2. Handle Success State
                    binding.fabFavorite.setImageResource(R.drawable.favorite)
                    binding.fabFavorite.imageTintList = android.content.res.ColorStateList.valueOf(
                        if (state.isFavorite) android.graphics.Color.parseColor("#FF4081") // Pink
                        else android.graphics.Color.parseColor("#000000") // Default Black
                    )

                    state.cocktail?.let { cocktail ->

                        // Bind the data object to the XML layout variables
                        binding.cocktail = cocktail

                        // Load the beautiful header image using Glide
                        Glide.with(this@CocktailDetailsFragment)
                            .load(cocktail.imageUrl)
                            .centerCrop()
                            .into(binding.ivHeaderImage)

                        // Inflate Ingredients
                        binding.llIngredientsContainer.removeAllViews()
                        cocktail.ingredients.forEach { ingredient ->
                            val itemBinding = ItemIngredientBinding.inflate(
                                LayoutInflater.from(requireContext()), 
                                binding.llIngredientsContainer, 
                                false
                            )
                            itemBinding.tvMeasure.text = ingredient.measure
                            itemBinding.tvIngredientName.text = ingredient.name
                            binding.llIngredientsContainer.addView(itemBinding.root)
                        }

                        // Inflate Instructions
                        binding.llInstructionsContainer.removeAllViews()
                        cocktail.instructions.forEachIndexed { index, step ->
                            val itemBinding = ItemInstructionBinding.inflate(
                                LayoutInflater.from(requireContext()), 
                                binding.llInstructionsContainer, 
                                false
                            )
                            itemBinding.tvStepNumber.text = (index + 1).toString()
                            itemBinding.tvInstructionText.text = step
                            binding.llInstructionsContainer.addView(itemBinding.root)
                        }
                    }

                    // 3. Handle Error State
                    if (state.error != null) {
                        Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevent memory leaks!
    }
}