package com.itsmobile.pokedex.ui.moveslist

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.model.moves.moveDetail.DamageClass
import com.itsmobile.pokedex.model.moves.moveDetail.LearnedByPokemon
import com.itsmobile.pokedex.model.moves.moveDetail.Move
import com.itsmobile.pokedex.model.moves.moveDetail.Target
import com.itsmobile.pokedex.model.moves.moveDetail.Type
import com.itsmobile.pokedex.ui.moveslist.ui.theme.PokédexTheme
import com.itsmobile.pokedex.viewmodels.MoveDetailViewModel
import com.itsmobile.pokedex.viewmodels.MovesViewModel

class MoveDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.getStringExtra("url")
        setContent {
            PokédexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        TopBar()
                        if (url != null) {
                            Body(url)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFFBA1A20))
        .clickable { }
        .height(64.dp),
        contentAlignment = Alignment.Center
    ){
        Row(modifier = Modifier
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

@Composable
fun Body(url: String, dataViewModel: MoveDetailViewModel = viewModel()){
    val mContext = LocalContext.current
    dataViewModel.setMove(mContext, url)

    val move = dataViewModel.move

    // ACCURACY 30 ~ 100
    // POWER 10 ~ 250
    // PP 1 ~ 35
    if(move != null){
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(move.name.uppercase(), fontSize = 24.sp, modifier = Modifier.padding(vertical = 30.dp))
            Stat(propertyName = "ID", propertyDescription = move.id.toString().uppercase())
            Stat(propertyName = "DAMAGE CLASS", propertyDescription = move.damage_class.name.uppercase())
            Stat(propertyName = "TARGET", propertyDescription = move.target.name.uppercase())
            Stat(propertyName = "TYPE", propertyDescription = move.type.name.uppercase())

            // positionY = (move.power - 10)/ (250 - 10) * (size.height - size.height/2) + size.height/2
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("POWER")
                Text(move.power.toString())
                Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                    Column(modifier = Modifier.width(80.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("PRIORITY")
                        Text(move.priority.toString())
                    }
                    Spacer(
                        modifier = Modifier
                            .drawWithCache {
                                val path = Path()

                                path.moveTo(
                                    size.width / 2f,
                                    getPosition(
                                        10.0,
                                        250.0,
                                        move.power.toDouble(),
                                        0.0,
                                        size.height / 2.toDouble()
                                    ).toFloat()
                                )
                                path.lineTo(
                                    getPosition(
                                        1.0,
                                        35.0,
                                        move.pp.toDouble(),
                                        size.width / 2.toDouble(),
                                        size.width.toDouble()
                                    ).toFloat(), size.height / 2f
                                )
                                path.lineTo(
                                    size.width / 2f,
                                    getPosition(
                                        30.0,
                                        100.0,
                                        move.accuracy.toDouble(),
                                        size.height / 2.toDouble(),
                                        size.height.toDouble()
                                    ).toFloat()
                                )
                                path.lineTo(
                                    getPosition(
                                        0.0,
                                        1.0,
                                        move.priority.toDouble(),
                                        size.width / 2.toDouble(),
                                        0.0
                                    ).toFloat(), size.height / 2f
                                )
                                path.close()
                                onDrawBehind {
                                    drawPath(path, Color(0xFFFEDEA6), style = Fill)
                                }
                            }
                            .width(200.dp)
                            .height(200.dp)
                    )
                    Column(modifier = Modifier.width(80.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("PP")
                        Text(move.pp.toString())
                    }
                }
                Text("ACCURACY")
                Text(move.accuracy.toString())
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 50.dp)
            ) {
                items(move.learned_by_pokemon.count()) { pokemon ->
                    Column {
                        val regex = Regex("""\d+(?=/?$)""")
                        val matchResult = regex.find(move.learned_by_pokemon[pokemon].url)
                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${matchResult?.value}.png"
                        //GlideImage(model = url, contentDescription = "pokemon image")
                        Text(move.learned_by_pokemon[pokemon].name)
                    }
                }
            }
        }
    }

}

@Composable
fun Stat(propertyName: String, propertyDescription: String){
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween){
        Text(propertyName, fontWeight = FontWeight(700))
        Text(propertyDescription)
    }
}

fun getPosition(minOldRange: Double, maxOldRange: Double, numberOldRange:Double, minNewRange:Double, maxNewRange:Double) : Double{

    val firstOperation = (numberOldRange - minOldRange) / (maxOldRange - minOldRange)
    val secondOperation = maxNewRange - minNewRange

    return firstOperation * secondOperation + minNewRange
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    PokédexTheme {
        Column {
            TopBar()
            Body("")
        }
    }
}