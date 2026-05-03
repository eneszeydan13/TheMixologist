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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.themixologist.feature.cocktail_details.CocktailDetailsScreen
import com.example.themixologist.feature.cocktail_list.CocktailListScreen
import com.example.themixologist.feature.favorites.FavoritesScreen
import com.example.themixologist.feature.settings.SettingsScreen

sealed class Screen(val route: String, val title: String, val icon: ImageVector?) {
    object CocktailList : Screen("cocktail_list", "Cocktails", Icons.Default.List)
    object Favorites : Screen("favorites", "Favorites", Icons.Default.Favorite)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
    object CocktailDetail : Screen("cocktail_detail/{cocktailId}", "Detail", null) {
        fun createRoute(cocktailId: String) = "cocktail_detail/$cocktailId"
    }
}

val bottomNavItems = listOf(
    Screen.CocktailList,
    Screen.Favorites,
    Screen.Settings
)

@Composable
fun MixologistNavHost() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val isBottomBarVisible = bottomNavItems.any { it.route == currentDestination?.route }

            if (isBottomBarVisible) {
                NavigationBar {
                    bottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { screen.icon?.let { Icon(it, contentDescription = screen.title) } },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
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
            startDestination = Screen.CocktailList.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.CocktailList.route) {
                CocktailListScreen(
                    onNavigateToDetail = { cocktailId ->
                        navController.navigate(Screen.CocktailDetail.createRoute(cocktailId))
                    }
                )
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    onNavigateToDetail = { cocktailId ->
                        navController.navigate(Screen.CocktailDetail.createRoute(cocktailId))
                    }
                )
            }
            composable(Screen.Settings.route) {
                SettingsScreen()
            }
            composable(Screen.CocktailDetail.route) { backStackEntry ->
                val cocktailId = backStackEntry.arguments?.getString("cocktailId") ?: ""
                CocktailDetailsScreen(
                    onNavigateUp = { navController.navigateUp() }
                )
            }
        }
    }
}
