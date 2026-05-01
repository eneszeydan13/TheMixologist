package com.example.themixologist.feature.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.themixologist.core.utils.ThemePreferences
import com.example.themixologist.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    @Inject
    lateinit var themePreferences: ThemePreferences

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (themePreferences.themeMode) {
            AppCompatDelegate.MODE_NIGHT_NO -> binding.rgThemeOptions.check(binding.rbLight.id)
            AppCompatDelegate.MODE_NIGHT_YES -> binding.rgThemeOptions.check(binding.rbDark.id)
            else -> binding.rgThemeOptions.check(binding.rbSystem.id)
        }

        binding.rgThemeOptions.setOnCheckedChangeListener { _, checkedId ->
            val selectedMode = when (checkedId) {
                binding.rbLight.id -> AppCompatDelegate.MODE_NIGHT_NO
                binding.rbDark.id -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            themePreferences.themeMode = selectedMode
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
