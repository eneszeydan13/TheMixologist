package com.example.themixologist.core.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemePreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)

    private val _themeModeFlow = MutableStateFlow(
        prefs.getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    )
    val themeModeFlow: StateFlow<Int> = _themeModeFlow.asStateFlow()

    var themeMode: Int
        get() = _themeModeFlow.value
        set(value) {
            prefs.edit().putInt("theme_mode", value).apply()
            AppCompatDelegate.setDefaultNightMode(value)
            _themeModeFlow.value = value
        }
}
