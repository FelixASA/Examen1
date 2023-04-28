package com.example.examen1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examen1.ui.theme.Examen1Theme
import java.time.LocalDateTime

val map: HashMap<String, Int> = HashMap<String, Int>()

class Juego : ComponentActivity() {
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
                    iniciarMap()
                    DefaultPreview2()
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val tinyDB = remember {
        TinyDB(context)
    }
    var resultado by remember {
        mutableStateOf("")
    }
    var ocultar by remember {
        mutableStateOf(true)
    }
    val juego = remember {
        Blackjack()
    }
    var manoJugador by remember {
        mutableStateOf(juego.obtenerCartasJugador()?.toMutableStateList())
    }
    var manoCrupier by remember {
        mutableStateOf(juego.obtenerCartasCrupier()?.toMutableStateList())
    }
    val activity = context.findActivity()
    val intent = activity?.intent
    val puntosAGanar = remember {
        mutableStateOf(intent?.extras?.getInt("puntosAGanar")!!)
    }

    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = resultado, fontSize = 30.sp)
        Spacer(modifier = Modifier.height(30.dp))
        LazyRow(contentPadding = PaddingValues(horizontal = 10.dp)){
            items(manoCrupier!!.size) { index ->
                var nombre = ""
                if(index != 1 && ocultar){
                    nombre = "trasera"
                } else {
                    nombre = manoCrupier!![index]?.getFigura() + manoCrupier!![index]?.getValor()
                }
                Image(
                    modifier = Modifier.size(80.dp, 130.dp),
                    painter = painterResource(map[nombre]!!),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
        }
        Spacer(modifier = Modifier.height(170.dp))
        LazyRow(contentPadding = PaddingValues(horizontal = 10.dp)) {
            items(manoJugador!!.size){ index ->
                val nombre = manoJugador!![index]?.getFigura() + manoJugador!![index]?.getValor()
                Image(modifier = Modifier.size(80.dp,130.dp),
                    painter = painterResource(map[nombre]!!),
                    contentDescription = null,
                    contentScale = ContentScale.Fit)
            }
        }
        Spacer(modifier = Modifier.height(70.dp))
        Row() {
            if(ocultar) {
                Button(modifier = Modifier.size(120.dp, 50.dp),
                    shape = RoundedCornerShape(20.dp),
                    enabled = ocultar,
                    onClick = {
                        var winner = ""
                        when (hayVictoria(juego, puntosAGanar)) {
                            0 -> {
                                resultado = "Empate"
                                winner = "Empate;" + juego.jugador.mostrarMano() + ";" + juego.crupier.mostrarMano() + ";"+ LocalDateTime.now().toString()
                            }
                            1 -> {
                                resultado = "Has ganado"
                                winner = "Jugador;" + juego.jugador.mostrarMano() + ";" + juego.crupier.mostrarMano() + ";" + LocalDateTime.now().toString()
                            }
                            2 -> {
                                resultado = "Has perdido"
                                winner = "Crupier;" + juego.jugador.mostrarMano() + ";" + juego.crupier.mostrarMano() + ";" + LocalDateTime.now().toString()
                            }
                        }
                        manoCrupier = juego.obtenerCartasCrupier()?.toMutableStateList()
                        val winners = tinyDB.getListString("Winners")
                        winners.add(winner)
                        tinyDB.putListString("Winners", winners)
                        ocultar = false
                    }) {
                    Text(
                        text = stringResource(R.string.boton_terminar),
                        fontSize = integerResource(R.integer.FontSize).sp
                    )
                }
                Spacer(modifier = Modifier.width(30.dp))
                Button(modifier = Modifier.size(120.dp, 50.dp),
                    shape = RoundedCornerShape(20.dp),
                    enabled = ocultar,
                    onClick = {
                        juego.pedirCarta()
                        manoJugador = juego.obtenerCartasJugador()?.toMutableStateList()
                    }) {
                    Text(
                        text = stringResource(R.string.boton_pedir),
                        fontSize = integerResource(R.integer.FontSize).sp
                    )
                }
            } else {
                Button(modifier = Modifier.size(150.dp, 50.dp),
                    shape = RoundedCornerShape(20.dp),
                    onClick = {
                    reiniciarPartida(juego)
                    resultado = ""
                    ocultar = true
                    manoJugador = juego.obtenerCartasJugador()?.toMutableStateList()
                    manoCrupier = juego.obtenerCartasCrupier()?.toMutableStateList()
                }) {
                    Text(text = stringResource(R.string.Reinciar), fontSize = integerResource(R.integer.FontSize).sp)
                }
            }
        }
    }
}

fun reiniciarPartida(juego: Blackjack) {
    juego.reiniciarJuego()
}

fun hayVictoria(juego: Blackjack, puntosAGanar: MutableState<Int>): Int{
    val sumaJugador = juego.jugador.obtenerPuntaje()
    var sumaCrupier = juego.crupier.obtenerPuntaje()
    while (sumaCrupier <= sumaJugador && sumaCrupier < puntosAGanar.value && !(sumaJugador > 21)){
        juego.darCartaCrupier()
        sumaCrupier = juego.crupier.obtenerPuntaje()
    }
    return if (sumaJugador in (sumaCrupier + 1)..puntosAGanar.value || sumaCrupier > puntosAGanar.value) {
        1
    } else if (sumaJugador > puntosAGanar.value || sumaJugador < sumaCrupier) {
        2
    } else {
        0
    }
}

fun iniciarMap(){

    map.put("c14", R.drawable.c14)
    map.put("c2", R.drawable.c2)
    map.put("c3", R.drawable.c3)
    map.put("c4", R.drawable.c4)
    map.put("c5", R.drawable.c5)
    map.put("c6", R.drawable.c6)
    map.put("c7", R.drawable.c7)
    map.put("c8", R.drawable.c8)
    map.put("c9", R.drawable.c9)
    map.put("c10", R.drawable.c10)
    map.put("c11", R.drawable.c11)
    map.put("c12", R.drawable.c12)
    map.put("c13", R.drawable.c13)

    map.put("d14", R.drawable.d14)
    map.put("d2", R.drawable.d2)
    map.put("d3", R.drawable.d3)
    map.put("d4", R.drawable.d4)
    map.put("d5", R.drawable.d5)
    map.put("d6", R.drawable.d6)
    map.put("d7", R.drawable.d7)
    map.put("d8", R.drawable.d8)
    map.put("d9", R.drawable.d9)
    map.put("d10", R.drawable.d10)
    map.put("d11", R.drawable.d11)
    map.put("d12", R.drawable.d12)
    map.put("d13", R.drawable.d13)

    map.put("p14", R.drawable.p14)
    map.put("p2", R.drawable.p2)
    map.put("p3", R.drawable.p3)
    map.put("p4", R.drawable.p4)
    map.put("p5", R.drawable.p5)
    map.put("p6", R.drawable.p6)
    map.put("p7", R.drawable.p7)
    map.put("p8", R.drawable.p8)
    map.put("p9", R.drawable.p9)
    map.put("p10", R.drawable.p10)
    map.put("p11", R.drawable.p11)
    map.put("p12", R.drawable.p12)
    map.put("p13", R.drawable.p13)

    map.put("t14", R.drawable.t14)
    map.put("t2", R.drawable.t2)
    map.put("t3", R.drawable.t3)
    map.put("t4", R.drawable.t4)
    map.put("t5", R.drawable.t5)
    map.put("t6", R.drawable.t6)
    map.put("t7", R.drawable.t7)
    map.put("t8", R.drawable.t8)
    map.put("t9", R.drawable.t9)
    map.put("t10", R.drawable.t10)
    map.put("t11", R.drawable.t11)
    map.put("t12", R.drawable.t12)
    map.put("t13", R.drawable.t13)

    map.put("trasera", R.drawable.trasera)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    Examen1Theme {
        Greeting(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center))
    }
}