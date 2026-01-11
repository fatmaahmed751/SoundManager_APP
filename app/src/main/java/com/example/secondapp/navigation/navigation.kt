package com.example.secondapp.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.secondapp.DetailsScreen
import com.example.secondapp.home.HomeScreen

@Composable
fun AppNavigation() {
    //لعقل المدبر
    //ده اللي بينفذ التنقل
    //علشان ميتعملش إعادة إنشاءremeber=>
    //NavController → يعرف إحنا في أنهي شاشة
    val navController = rememberNavController()
    // val navBackStackEntry by navController.currentBackStackEntryAsState()
    // val currentRoute = navBackStackEntry?.destination?.route
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(navController)
        }

        composable(
            route = "details/{title}/{soundRes}",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("soundRes") { type = NavType.IntType }
            )
        ) {
            val title = it.arguments?.getString("title")
            val soundRes = it.arguments?.getInt("soundRes")
            DetailsScreen(title, soundRes)
        }
    }

}


//@Composable
//fun DetailsScreen(title: String?, soundRes: Int?) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(text = title ?: "")
//        Button(onClick = {
//            soundRes?.let { SoundManager.play(it) }
//        }) {
//            Text("Play Sound")
//        }
//    }
//}
