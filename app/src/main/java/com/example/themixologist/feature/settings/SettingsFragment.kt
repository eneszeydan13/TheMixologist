package com.example.themixologist.feature.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.themixologist.R
import com.example.themixologist.core.base.BaseFragment
import com.example.themixologist.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>(
    R.layout.fragment_settings
) {

    override val viewModel: SettingsViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSettingsBinding.inflate(inflater, container, false)

    override fun initViews() {
        binding.rgThemeOptions.setOnCheckedChangeListener { _, checkedId ->
            val selectedMode = when (checkedId) {
                binding.rbLight.id -> AppCompatDelegate.MODE_NIGHT_NO
                binding.rbDark.id -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            viewModel.updateTheme(selectedMode)
        }
    }

    override fun observeState() {
        super.observeState()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.themeMode.collect { mode ->
                    val expectedCheckedId = when (mode) {
                        AppCompatDelegate.MODE_NIGHT_NO -> binding.rbLight.id
                        AppCompatDelegate.MODE_NIGHT_YES -> binding.rbDark.id
                        else -> binding.rbSystem.id
                    }

                    // Only set if different, to avoid looping with the listener
                    if (binding.rgThemeOptions.checkedRadioButtonId != expectedCheckedId) {
                        binding.rgThemeOptions.check(expectedCheckedId)
                    }
                }
            }
        }
    }
}
