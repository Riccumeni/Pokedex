package com.itsmobile.pokedex.ui.moveslist
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.itsmobile.pokedex.ui.moveslist.ui.theme.PokédexTheme

class MainMoveActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokédexTheme {
                navController = rememberNavController()
                NavHost(navController = navController, startDestination = "move_list"){
                    composable(route = "move_list"){
                        MoveListScreen(navController)
                    }
                    composable(route = "move_detail?url={url}", arguments = listOf(navArgument("url"){
                        type = NavType.StringType
                    })){
                        val url = requireNotNull(it.arguments).getString("url")
                        if (url != null) {
                            MoveDetailActivity(url, navController)
                        }
                    }
                }
            }
        }
    }
}




