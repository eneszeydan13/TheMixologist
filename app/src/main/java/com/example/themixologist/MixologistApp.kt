package com.example.themixologist

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.themixologist.core.utils.ThemePreferences
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MixologistApp : Application() {

    @Inject
    lateinit var themePreferences: ThemePreferences

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(themePreferences.themeMode)
    }
}