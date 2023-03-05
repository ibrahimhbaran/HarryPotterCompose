package com.play.harrypottercompose.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import com.play.harrypottercompose.ui.view.detail.DetailScreen
import com.play.harrypottercompose.ui.view.home.HomeScreen
import com.play.harrypottercompose.ui.view.home.HomeViewModel


@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destinations.HomeDestination.route
    ) {
        composable(route = Destinations.HomeDestination.route, label = "Harry Potter") {
            // Creates a ViewModel from the current BackStackEntry
            // Available in the androidx.hilt:hilt-navigation-compose artifact
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                onNavigateToDetailScreen = {
                    navController.navigate(
                        route = Destinations.DetailDestination.route +
                                Destinations.DetailDestination.buildRouteArgs(it)
                    )
                },
                vieModel = homeViewModel
            )
        }

        composable(
            route = Destinations.DetailDestination.route + "?name={name}&image={image}&info={info}",
            label = "Character",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("image") { type = NavType.StringType },
                navArgument("info") { type = NavType.StringType }
            )
        ) {
            val name = it.arguments?.getString("name")
            val image = it.arguments?.getString("image")
            val info = it.arguments?.getString("info")
            DetailScreen(name = name, image = image, info = info)
        }

    }
}

// overloading for setting your label if you want see the current screen at the tob bar as title
// if you use route it will show the appended arguments as  well , which is not good for UI
fun NavGraphBuilder.composable(
    route: String,
    label: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    addDestination(
        ComposeNavigator.Destination(provider[ComposeNavigator::class], content).apply {
            this.route = route
            this.label = label
            arguments.forEach { (argumentName, argument) ->
                addArgument(argumentName, argument)
            }
            deepLinks.forEach { deepLink ->
                addDeepLink(deepLink)
            }
        }
    )
}

