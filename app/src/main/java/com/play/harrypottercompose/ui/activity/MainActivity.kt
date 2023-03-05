package com.play.harrypottercompose.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.play.harrypottercompose.ui.AppNavHost
import com.play.harrypottercompose.ui.theme.HarryPotterComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HarryPotterApp()
        }
    }

    @Composable
    private fun HarryPotterApp() {
        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        HarryPotterComposeTheme {
            Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                AppBar(currentScreenTitle = backStackEntry?.destination?.label ?: "No Screen",
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = {
                        navController.navigateUp()
                    }) {
                    // menu click
                }
            }) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    AppNavHost(
                        navController = navController,
                    )
                }
            }
        }
    }

    @Composable
    fun AppBar(
        currentScreenTitle: CharSequence,
        canNavigateBack: Boolean,
        navigateUp: () -> Unit,
        modifier: Modifier = Modifier,
        onMenuClicked: () -> Unit
    ) {
        TopAppBar(
            title = { Text(currentScreenTitle.toString()) },
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White
            ),
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = "back button"
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",

                        // When clicked trigger onClick
                        // Callback to trigger drawer open
                        modifier = Modifier.clickable(onClick = onMenuClicked),
                        tint = Color.White
                    )
                }
            }
        )
    }
}