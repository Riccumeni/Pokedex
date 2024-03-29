package com.itsmobile.pokedex.ui.moveslist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.itsmobile.pokedex.R
import com.itsmobile.pokedex.domain.usecases.MoveDetailSuccess
import com.itsmobile.pokedex.domain.usecases.MoveError
import com.itsmobile.pokedex.domain.usecases.MoveInitial
import com.itsmobile.pokedex.domain.usecases.MoveLoading
import com.itsmobile.pokedex.ui.moveslist.ui.theme.Font
import com.itsmobile.pokedex.domain.viewmodels.MoveDetailViewModel

@Composable
fun MoveDetailActivity (url: String, navHostController: NavHostController, context: AppCompatActivity){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.background)
    ) {
        Column{
            TopBar(navHostController, context)
            if (url != null) {
                Body(url)
            }
        }
    }
}

@Composable
fun TopBar(navHostController: NavHostController, context: AppCompatActivity) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(colorResource(id = R.color.primary))
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
                if(navHostController.currentDestination?.id == navHostController.graph.startDestinationId){
                    context.finish()
                }else{
                    navHostController.popBackStack()
                }
            })
            Box(modifier = Modifier.weight(weight = 1.0f)){
                Text(text = "Pokédex", color = MaterialTheme.colorScheme.onPrimary, fontFamily = Font.poppinsFamily)
            }
        }
    }

}

@Composable
fun Body(url: String, dataViewModel: MoveDetailViewModel = viewModel()){
    val mContext = LocalContext.current

    var state = dataViewModel.state

    when(state){
        is MoveInitial -> {
            dataViewModel.setMove(mContext, url)
        }
        is MoveDetailSuccess -> {
            val move = state.move

            Column(modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(move.name.uppercase(), fontSize = 24.sp, modifier = Modifier.padding(vertical = 30.dp), fontFamily = Font.poppinsFamily, fontWeight = FontWeight.Bold)
                Stat(propertyName = "ID", propertyDescription = move.id.toString().uppercase())
                Stat(propertyName = "DAMAGE CLASS", propertyDescription = move.damage_class.name.uppercase())
                Stat(propertyName = "TARGET", propertyDescription = move.target.name.uppercase())
                Stat(propertyName = "TYPE", propertyDescription = move.type.name.uppercase())

                if(move.damage_class.name.uppercase() != "STATUS"){
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("POWER", fontFamily = Font.poppinsFamily, fontWeight = FontWeight.Medium)
                        Text(move.power.toString(), fontFamily = Font.poppinsFamily)
                        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                            Column(modifier = Modifier.width(80.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("PRIORITY", fontFamily = Font.poppinsFamily, fontWeight = FontWeight.Medium)
                                Text(move.priority.toString(), fontFamily = Font.poppinsFamily)
                            }
                            Box {
                                Spacer(
                                    modifier = Modifier
                                        .drawWithCache {
                                            val path = Path()

                                            path.moveTo(
                                                size.width / 2f,
                                                0f
                                            )
                                            path.lineTo(
                                                size.width,
                                                size.height / 2f
                                            )
                                            path.lineTo(
                                                size.width / 2f,
                                                size.height
                                            )
                                            path.lineTo(
                                                0f, size.height / 2f
                                            )
                                            path.close()
                                            onDrawBehind {
                                                drawPath(path, Color(0x80FEDEA6), style = Fill)
                                            }
                                        }
                                        .width(200.dp)
                                        .height(200.dp)
                                        .zIndex(0f)
                                )
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
                                                    size.height / 2.toDouble(),
                                                    0.0
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
                                                drawPath(path, Color(0xFFFFD283), style = Fill)
                                            }
                                        }
                                        .width(200.dp)
                                        .height(200.dp)
                                        .zIndex(1f)
                                )
                            }
                            Column(modifier = Modifier.width(80.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("PP", fontFamily = Font.poppinsFamily, fontWeight = FontWeight.Medium)
                                Text(move.pp.toString(), fontFamily = Font.poppinsFamily)
                            }
                        }
                        Text("ACCURACY", fontFamily = Font.poppinsFamily, fontWeight = FontWeight.Medium)
                        Text(move.accuracy.toString(), fontFamily = Font.poppinsFamily)
                    }
                }

                Text("Pokemon that can learn it".uppercase(), fontSize = 18.sp, modifier = Modifier.padding(vertical = 30.dp, horizontal = 10.dp), fontFamily = Font.poppinsFamily, fontWeight = FontWeight.SemiBold)

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 80.dp),
                    userScrollEnabled = false,
                    modifier = Modifier
                        .height((move.learned_by_pokemon.count() / 4 * 100).dp)
                        .padding(10.dp)

                ) {
                    items(move.learned_by_pokemon.count()) { pokemon ->
                        Column {
                            val regex = Regex("""\d+(?=/?$)""")
                            val matchResult = regex.find(move.learned_by_pokemon[pokemon].url)
                            val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${matchResult?.value}.png"
                            AsyncImage(model = url, contentDescription = "pokemon image", modifier = Modifier
                                .width(80.dp)
                                .height(80.dp))
                            Text(move.learned_by_pokemon[pokemon].name, textAlign = TextAlign.Center, fontFamily = Font.poppinsFamily,fontSize = 14.sp, modifier = Modifier.fillMaxWidth())
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
                    ElevatedButton(onClick = { dataViewModel.setMove(mContext, url) }, colors = ButtonDefaults.buttonColors(
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

@Composable
fun Stat(propertyName: String, propertyDescription: String){
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween){
        Text(propertyName, fontWeight = FontWeight.SemiBold, fontFamily = Font.poppinsFamily)
        Text(propertyDescription, fontFamily = Font.poppinsFamily)
    }
}

fun getPosition(minOldRange: Double, maxOldRange: Double, numberOldRange:Double, minNewRange:Double, maxNewRange:Double) : Double{

    val firstOperation = (numberOldRange - minOldRange) / (maxOldRange - minOldRange)
    val secondOperation = maxNewRange - minNewRange

    return firstOperation * secondOperation + minNewRange
}