package com.itsmobile.pokedex.ui.moveslist

import android.content.Context
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.domain.usecases.MoveError
import com.itsmobile.pokedex.domain.usecases.MoveInitial
import com.itsmobile.pokedex.domain.usecases.MoveListSuccess
import com.itsmobile.pokedex.domain.usecases.MoveLoading
import com.itsmobile.pokedex.ui.moveslist.ui.theme.Font
import com.itsmobile.pokedex.domain.viewmodels.MovesViewModel


@Composable
fun MoveListScreen(navController: NavHostController, context: AppCompatActivity){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.background)
    ) {
        Column {
            TopBar(navController, context)
            ListMoves(navController = navController)
        }
    }
}

@Composable
fun ListMoves(dataViewModel: MovesViewModel = viewModel(), navController: NavHostController){

    val state = dataViewModel.state.value

    when(state){
        is MoveListSuccess -> {
            val moves = state.move
            LazyColumn(modifier = Modifier
                .padding(horizontal = 10.dp)
                .height((100 * moves.count()).dp)){
                items(moves.count()){
                    Spacer(modifier = Modifier
                        .width(10.dp)
                        .height(10.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            colorResource(id = R.color.primaryContainer),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(all = 10.dp)
                        .clickable {
                            navController.navigate(
                                "move_detail?url=${moves[it].url}"
                            )
                        }
                    ){
                        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                            Text(
                                text = moves[it].id,
                                textAlign = TextAlign.Center,
                                fontFamily = Font.poppinsFamily,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = moves[it].name.uppercase(),
                                textAlign = TextAlign.Center,
                                fontFamily = Font.poppinsFamily,
                                fontWeight = FontWeight.Medium
                            )
                            Column {
                                Text(text = moves[it].type.uppercase(), textAlign = TextAlign.Right, fontFamily = Font.poppinsFamily, fontWeight = FontWeight.Medium)
                                Text(text = moves[it].category.uppercase(), textAlign = TextAlign.Right, fontFamily = Font.poppinsFamily,fontWeight = FontWeight.Medium)
                            }
                        }
                    }

                }

            }
        }
        is MoveLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator(strokeWidth = 5.dp, modifier = Modifier
                    .width(50.dp)
                    .height(50.dp))
            }
        }
        is MoveError -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Column(modifier = Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(R.drawable.pikachu_sad), contentDescription = "pikachu sad", modifier = Modifier
                        .height(150.dp)
                        .width(150.dp))
                    Text(text = "Unable to fetch data, check your internet connection", color = colorResource(id = R.color.onBackground), fontFamily = Font.poppinsFamily, textAlign = TextAlign.Center)
                    ElevatedButton(onClick = {  }, colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.primaryContainer)
                    )) {
                        Icon(painter = painterResource(id = R.drawable.baseline_refresh_24), contentDescription = "", modifier = Modifier
                            .width(24.dp)
                            .height(24.dp),
                            tint = colorResource(id = R.color.onPrimaryContainer))
                        Box(modifier = Modifier.width(10.dp))
                        Text("Retry", color = colorResource(id = R.color.onPrimaryContainer), fontFamily = Font.poppinsFamily, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }


}