package com.example.themixologist.feature.settings

import androidx.lifecycle.viewModelScope
import com.example.themixologist.core.base.BaseViewModel
import com.example.themixologist.core.utils.ThemePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themePreferences: ThemePreferences
) : BaseViewModel() {

    private val _themeMode = MutableStateFlow(themePreferences.themeMode)
    val themeMode: StateFlow<Int> = _themeMode.asStateFlow()

    fun updateTheme(mode: Int) {
        viewModelScope.launch {
            themePreferences.themeMode = mode
            _themeMode.value = mode
        }
    }
}
