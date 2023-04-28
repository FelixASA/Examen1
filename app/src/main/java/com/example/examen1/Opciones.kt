package com.example.examen1

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.examen1.ui.theme.Examen1Theme

class Opciones : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val activity = context.findActivity()
            val intent = activity?.intent
            var tema = remember {
                mutableStateOf(intent?.extras?.getBoolean("tema")!!)
            }
            Examen1Theme(tema.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    val activity = context.findActivity()
                    val intent = activity?.intent
                    val turno = remember {
                        mutableStateOf(intent?.extras?.getInt("puntosAGanar")!!)
                    }
                    DefaultPreview3(onFinishActivity = {
                        val resultIntent = Intent().apply {
                            putExtra(RESULT_TAG, turno.value)
                            putExtra("tema", tema.value)
                        }
                        setResult(RESULT_OK, resultIntent)
                        finish()
                    }, turno, tema)
                }

            }
        }
    }
}

@Composable
fun Greeting2(
    modifier: Modifier = Modifier,
    turnos: MutableState<Int>,
    onFinishActivity: () -> Unit,
    tema: MutableState<Boolean>
) {
    val clicked = remember { mutableStateOf(false) }

    val radioOptions = stringArrayResource(R.array.array_turnos).asList()
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(turnos.value.toString()) }
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(clicked.value) {
            AlertSingleChoiceView(state = clicked, selectedOption, onOptionSelected, radioOptions)
            turnos.value = selectedOption.toInt()
        }
        Button(modifier = Modifier.size(150.dp, 50.dp),
            shape = RoundedCornerShape(20.dp),
            onClick = {
            clicked.value = true
        }){
            Text(text = stringResource(R.string.num_puntos) + ": ${turnos.value}")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(modifier = Modifier.size(150.dp, 50.dp),
            shape = RoundedCornerShape(20.dp),
            onClick = {
                tema.value = !tema.value
            }) {
            Text(text = stringResource(R.string.elegir_tema))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(modifier = Modifier.size(150.dp, 50.dp),
            shape = RoundedCornerShape(20.dp),
            onClick = {
            onFinishActivity()
        }) {
            Text(text = "Ok")
        }
    }

}

@Composable
fun CommonDialog(
    title: String?,
    state: MutableState<Boolean>,
    content: @Composable (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = {
            state.value = false
        },
        title = title?.let {
            {
                Column( Modifier.fillMaxWidth() ) {
                    Text(text = title)
                }
            }
        },
        text = content,
        confirmButton = {
            TextButton(onClick = { state.value = false }) { Text("OK") }
        },
        dismissButton = {
            TextButton(onClick = { state.value = false }) { Text("Cancel") }
        }
    )
}

@Composable
fun AlertSingleChoiceView(state: MutableState<Boolean>, selectedOption: String, onOptionSelected: (String) -> Unit, radioOptions: List<String>) {
    CommonDialog(title = stringResource(R.string.elegir_puntos), state = state) { SingleChoiceView(selectedOption, onOptionSelected, radioOptions) }
}

@Composable
fun SingleChoiceView(selectedOption: String, onOptionSelected: (String) -> Unit, radioOptions: List<String>) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text) }
                )
                Text(text = text)
            }
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Composable
fun DefaultPreview3(onFinishActivity: () -> Unit, turno: MutableState<Int>, tema: MutableState<Boolean>) {
    Examen1Theme {
        Greeting2(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
            turno,
            onFinishActivity = onFinishActivity,
            tema
        )
    }
}

