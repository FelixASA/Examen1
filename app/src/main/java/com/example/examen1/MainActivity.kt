package com.example.examen1

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examen1.ui.theme.Examen1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var tema = remember {
                mutableStateOf(true)
            }
            Examen1Theme(tema.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DefaultPreview(tema)
                }
            }
        }
    }
}

@Composable
fun Menu(modifier: Modifier = Modifier, tema: MutableState<Boolean>){
    val context = LocalContext.current
    var puntosAGanar by remember {
        mutableStateOf(21)
    }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        if (result.resultCode == RESULT_OK) {
            puntosAGanar = result.data?.extras?.getInt(RESULT_TAG)!!
            tema.value = result.data?.extras?.getBoolean("tema")!!
        }
    }
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.blackjack),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(modifier = Modifier.size(150.dp, 50.dp),
            shape = RoundedCornerShape(20.dp),
            onClick = {
                val intent = Intent(context, Juego::class.java)
                intent.putExtra("puntosAGanar", puntosAGanar)
                intent.putExtra("tema", tema.value)
                launcher.launch(intent)

        }) {
            Text(text = stringResource(R.string.jugar),
                fontSize = integerResource(R.integer.FontSize).sp)
        }
        Spacer(modifier = Modifier.height(15.dp))
        
        Button(modifier = Modifier.size(150.dp, 50.dp),
            shape = RoundedCornerShape(20.dp),
            onClick = {
            val intent = Intent(context, Puntajes::class.java)
                intent.putExtra("tema", tema.value)
                launcher.launch(intent)

        }) {
            Text(text = stringResource(R.string.puntajes),
                fontSize = integerResource(R.integer.FontSize).sp)
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(modifier = Modifier.size(150.dp, 50.dp),
            shape = RoundedCornerShape(20.dp),
            onClick = {
            val intent = Intent(context, Opciones::class.java)
            intent.putExtra("puntosAGanar", puntosAGanar)
                intent.putExtra("tema", tema.value)
                launcher.launch(intent)
        }) {
            Text(text = stringResource(R.string.opciones),
                fontSize = integerResource(R.integer.FontSize).sp)
        }
    }
}




@Composable
fun DefaultPreview(tema: MutableState<Boolean>) {
    Examen1Theme {
        Menu(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center), tema)
    }

}