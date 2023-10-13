package com.itsmobile.pokedex.ui.moveslist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.ui.moveslist.ui.theme.Font
import com.itsmobile.pokedex.viewmodels.MovesViewModel


@Composable
fun MoveListScreen(navController: NavHostController){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.background)
    ) {
        Column {
            TopBar(navController)
            ListMoves(navController = navController)
        }
    }
}

@Composable
fun ListMoves(dataViewModel: MovesViewModel = viewModel(), navController: NavHostController){

    val moves = dataViewModel.state.value

    LazyColumn(modifier = Modifier.padding(horizontal = 10.dp).height((100 * moves.count()).dp)){
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