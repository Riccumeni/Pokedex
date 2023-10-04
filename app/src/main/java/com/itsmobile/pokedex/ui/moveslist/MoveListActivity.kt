package com.itsmobile.pokedex.ui.moveslist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.model.moves.Move
import com.itsmobile.pokedex.ui.moveslist.ui.theme.PokédexTheme
import com.itsmobile.pokedex.viewmodels.MovesViewModel
import com.itsmobile.pokedex.viewmodels.PokedexViewModel
import kotlinx.coroutines.launch

class MoveFire(val id: String, val name: String, val category: String, val type: String)

class MoveListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokédexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        TopBar()
                        ListMoves()
                    }
                }
            }
        }
    }
}

@Composable
fun ListMoves(dataViewModel: MovesViewModel = viewModel()){

    val mContext = LocalContext.current

    val moves = dataViewModel.state.value

    LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)){
        items(moves.count()){
            Spacer(modifier = Modifier
                .width(10.dp)
                .height(10.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFDAD6), shape = RoundedCornerShape(10.dp))
                .padding(all = 10.dp)
                .clickable {
                    val intent = Intent(mContext, MoveDetailActivity::class.java)
                    intent.putExtra("url", moves[it].url)
                    mContext.startActivity(intent)
                }
            ){
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = moves[it].id,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = moves[it].name,
                        textAlign = TextAlign.Center
                    )
                    Column {
                        Text(text = moves[it].type, textAlign = TextAlign.Center)
                        Text(text = moves[it].category, textAlign = TextAlign.Center)
                    }
                }
            }

        }

    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier) {
        Column {
            Box(modifier = modifier
                .fillMaxWidth()
                .background(Color(0xFFBA1A20))
                .height(64.dp),
                contentAlignment = Alignment.Center
            ){
                Row(modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    ) {
                    Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24), contentDescription = "", tint = Color.White, modifier = Modifier.clickable {
                        val activity = this as Activity
                        activity.finish()
                    })
                    Box(modifier = Modifier.weight(weight = 1.0f)){
                        Text(text = "Pokédex", color = MaterialTheme.colorScheme.onPrimary)
                    }
                    Box (modifier =
                    Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .background(color = Color(0xFFFFDAD6), shape = RoundedCornerShape(50)),
                        contentAlignment = Alignment.Center
                            
                    ){
                        Text("I", color = Color(0xFF410003))
                    }
                }
            }

        }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokédexTheme {
        Column {
            TopBar()
            ListMoves()
        }
    }
}