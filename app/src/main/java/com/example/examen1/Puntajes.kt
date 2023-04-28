package com.example.examen1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examen1.ui.theme.Examen1Theme
import org.w3c.dom.Text

const val RESULT_TAG = "sampleTag"
class Puntajes : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val activity = context.findActivity()
            val intent = activity?.intent
            val tema by remember {
                mutableStateOf(intent?.extras?.getBoolean("tema")!!)
            }
            Examen1Theme(tema) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingPreview()
                }
            }
        }
    }
}

@Composable
fun Greeting3(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val tinydb = TinyDB(context)
    val winners = tinydb.getListString("Winners")
    iniciarMap()
    LazyColumn(modifier = modifier) {
        items(winners){ winner ->
            val textAux = winner.split(';')
            Spacer(modifier = Modifier.height(10.dp))
            Row() {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.width(10.dp))
                            Column() {
                                Box() {
                                    Text(
                                        text = stringResource(R.string.Ganador),
                                        color = Color.White,
                                        fontSize = integerResource(R.integer.FontSizePuntos).sp
                                    )
                                }
                                Box() {
                                    Text(
                                        text = textAux[0].toString(),
                                        color = Color.White,
                                        fontSize = integerResource(R.integer.FontSizePuntos).sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(40.dp))
                            Column {
                                Text(
                                    text = "Cartas jugador",
                                    color = Color.White,
                                    fontSize = integerResource(R.integer.FontSizePuntos).sp
                                )
                                LazyRow(contentPadding = PaddingValues(horizontal = 10.dp)) {
                                    val cartas = textAux[1].split(",")
                                    items(cartas.size - 1) { index ->
                                        Image(
                                            modifier = Modifier.size(48.dp,75.dp),
                                            painter = painterResource(map[cartas[index]]!!),
                                            contentDescription = null,
                                            contentScale = ContentScale.Fit
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Cartas Crupier",
                                    color = Color.White,
                                    fontSize = integerResource(R.integer.FontSizePuntos).sp
                                )
                                LazyRow(contentPadding = PaddingValues(horizontal = 10.dp)) {
                                    val cartas = textAux[2].split(",")
                                    items(cartas.size - 1) { index ->
                                        Image(
                                            modifier = Modifier.size(48.dp,75.dp),
                                            painter = painterResource(map[cartas[index]]!!),
                                            contentDescription = null,
                                            contentScale = ContentScale.Fit
                                        )
                                    }
                                }
                            }

                        }
                        val fechaSplit = textAux[3].split("T")
                        val fecha = fechaSplit[0]
                        val hora = fechaSplit[1].split(":")
                        Row() {
                            Text(text = fecha,
                                color = Color.White,
                                fontSize = integerResource(R.integer.FontSize).sp)
                            Spacer(modifier = Modifier.width(40.dp))
                            Text(text = hora[0] + ":" + hora[1],
                                color = Color.White,
                                fontSize = integerResource(R.integer.FontSize).sp)
                        }

                    }
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview(modifier: Modifier = Modifier) {
    Examen1Theme {
        Greeting3(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center))
    }
}