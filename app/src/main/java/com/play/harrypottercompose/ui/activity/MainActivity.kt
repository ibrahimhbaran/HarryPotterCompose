package com.play.harrypottercompose.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.play.harrypottercompose.ui.theme.HarryPotterComposeTheme
import com.play.harrypottercompose.ui.view.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HarryPotterComposeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar {
                            // do click
                        }
                    }
                ) {
                    innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }

        }
    }
}


// A function which will receive a
// callback to trigger to opening the drawer
@Composable
fun TopBar(onMenuClicked: () -> Unit) {

    // TopAppBar Composable
    TopAppBar(
        // Provide Title
        title = {
            Text(text = "Harry Potter", color = Color.White)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        // Provide the navigation Icon (Icon on the left to toggle drawer)
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",

                // When clicked trigger onClick
                // Callback to trigger drawer open
                modifier = Modifier.clickable(onClick = onMenuClicked),
                tint = Color.White
            )
        }
    )
}