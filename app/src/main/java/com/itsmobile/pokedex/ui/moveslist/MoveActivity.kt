package com.itsmobile.pokedex.ui.moveslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.itsmobile.pokedex.R

class MoveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move)

        val composeView = findViewById<ComposeView>(R.id.composeView)

        window.statusBarColor = getColor(R.color.primary)

        composeView.apply {
            lateinit var navController: NavHostController
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "move_list"){
                        composable(route = "move_list"){
                            MoveListScreen(navController, this@MoveActivity)
                        }
                        composable(route = "move_detail?url={url}", arguments = listOf(navArgument("url"){
                            type = NavType.StringType
                        })){
                            val url = requireNotNull(it.arguments).getString("url")
                            if (url != null) {
                                MoveDetailActivity(url, navController, this@MoveActivity)
                            }
                        }
                    }
                }
            }
        }
    }
}