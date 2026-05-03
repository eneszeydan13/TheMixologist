package com.example.themixologist.feature.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.themixologist.core.theme.Dimens
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.themixologist.R

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Dimens.SpaceMedium)
        ) {
            Text(
                text = stringResource(id = R.string.theme_settings),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = Dimens.SpaceMedium)
            )

            ThemeOptionRow(
                text = stringResource(id = R.string.light_mode),
                selected = themeMode == AppCompatDelegate.MODE_NIGHT_NO,
                onClick = { viewModel.updateTheme(AppCompatDelegate.MODE_NIGHT_NO) }
            )
            
            ThemeOptionRow(
                text = stringResource(id = R.string.dark_mode),
                selected = themeMode == AppCompatDelegate.MODE_NIGHT_YES,
                onClick = { viewModel.updateTheme(AppCompatDelegate.MODE_NIGHT_YES) }
            )
            
            ThemeOptionRow(
                text = stringResource(id = R.string.system_default),
                selected = themeMode == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
                onClick = { viewModel.updateTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) }
            )
        }
    }
}

@Composable
private fun ThemeOptionRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Spacer(modifier = Modifier.width(Dimens.SpaceSmall))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
