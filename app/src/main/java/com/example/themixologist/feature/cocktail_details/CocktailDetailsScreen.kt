package com.example.themixologist.feature.cocktail_details

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.themixologist.R
import com.example.themixologist.core.base.BaseViewModel
import com.example.themixologist.core.components.IngredientRow
import com.example.themixologist.core.components.InstructionStep
import com.example.themixologist.core.theme.IconBgOverlay
import com.example.themixologist.core.theme.IconColorWhite
import com.example.themixologist.core.theme.TransparentColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailDetailsScreen(
    onNavigateUp: () -> Unit,
    viewModel: CocktailDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is BaseViewModel.UiEvent.ShowSnackbar -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                else -> Unit
            }
        }
    }

    val imageHeight = 350.dp

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        state.cocktail?.let { cocktail ->
            // 1. Parallax & Expanding Image
            AsyncImage(
                model = cocktail.imageUrl,
                contentDescription = cocktail.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
                    .graphicsLayer {
                        val scrollValue = scrollState.value.toFloat()
                        if (scrollValue > 0) {
                            translationY = -scrollValue * 0.5f // Parallax effect
                        } else {
                            translationY = -scrollValue * 0.5f
                        }
                    }
            )

            // 2. Scrollable Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                // Transparent spacer to push content below the image
                Spacer(modifier = Modifier.height(imageHeight - 32.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    // White Surface with rounded corners
                    Surface(
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                        ) {
                            Spacer(modifier = Modifier.height(32.dp))

                            Text(
                                text = cocktail.name,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                lineHeight = MaterialTheme.typography.headlineLarge.fontSize
                            )
                            
                            Text(
                                text = stringResource(
                                    id = R.string.category_glass_format,
                                    cocktail.category,
                                    cocktail.alcoholic,
                                    cocktail.glass
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.outline,
                                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
                            )

                            Text(
                                text = stringResource(id = R.string.ingredients),
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            cocktail.ingredients.forEach { ingredient ->
                                IngredientRow(ingredient = ingredient)
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = stringResource(id = R.string.instructions),
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            cocktail.instructions.forEachIndexed { index, instruction ->
                                InstructionStep(
                                    stepNumber = index + 1,
                                    instruction = instruction
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(32.dp))
                        }
                    }

                    // Floating Action Button Hovering on the Seam
                    FloatingActionButton(
                        onClick = { viewModel.toggleFavorite() },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 24.dp)
                            .offset(y = (-28).dp) // Exactly half its height (56.dp / 2)
                            .zIndex(1f),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        shape = CircleShape
                    ) {
                        Icon(
                            imageVector = if (state.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = stringResource(id = R.string.content_desc_favorite),
                            tint = if (state.isFavorite) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }

        // 3. Transparent Top App Bar
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(
                    onClick = onNavigateUp,
                    modifier = Modifier
                        .padding(8.dp)
                        .background(IconBgOverlay, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.content_desc_back),
                        tint = IconColorWhite
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = TransparentColor,
                scrolledContainerColor = TransparentColor
            )
        )
    }
}
