package com.example.themixologist.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.themixologist.feature.cocktail_details.CocktailDetailsScreen
import com.example.themixologist.feature.cocktail_list.CocktailListScreen
import com.example.themixologist.feature.favorites.FavoritesScreen
import com.example.themixologist.feature.settings.SettingsScreen
import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object CocktailList : Screen
    
    @Serializable
    data object Favorites : Screen
    
    @Serializable
    data object Settings : Screen
    
    @Serializable
    data class CocktailDetail(val cocktailId: String) : Screen
}

data class BottomNavItem(
    val screen: Screen,
    val title: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.CocktailList, "Cocktails", Icons.Default.List),
    BottomNavItem(Screen.Favorites, "Favorites", Icons.Default.Favorite),
    BottomNavItem(Screen.Settings, "Settings", Icons.Default.Settings)
)

@Composable
fun MixologistNavHost() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val isBottomBarVisible = currentDestination?.hasRoute<Screen.CocktailList>() == true ||
                                     currentDestination?.hasRoute<Screen.Favorites>() == true ||
                                     currentDestination?.hasRoute<Screen.Settings>() == true

            if (isBottomBarVisible) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val isSelected = when (item.screen) {
                            Screen.CocktailList -> currentDestination?.hasRoute<Screen.CocktailList>() == true
                            Screen.Favorites -> currentDestination?.hasRoute<Screen.Favorites>() == true
                            Screen.Settings -> currentDestination?.hasRoute<Screen.Settings>() == true
                            else -> false
                        }
                        
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.title) },
                            label = { Text(item.title) },
                            selected = isSelected,
                            onClick = {
                                navController.navigate(item.screen) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.CocktailList,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Screen.CocktailList> {
                CocktailListScreen(
                    onNavigateToDetail = { cocktailId ->
                        navController.navigate(Screen.CocktailDetail(cocktailId))
                    }
                )
            }
            composable<Screen.Favorites> {
                FavoritesScreen(
                    onNavigateToDetail = { cocktailId ->
                        navController.navigate(Screen.CocktailDetail(cocktailId))
                    }
                )
            }
            composable<Screen.Settings> {
                SettingsScreen()
            }
            composable<Screen.CocktailDetail> {
                CocktailDetailsScreen(
                    onNavigateUp = { navController.navigateUp() }
                )
            }
        }
    }
}
