package com.mohi.mainventar.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohi.mainventar.model.viewmodel.EntitiesViewModel

@Composable
fun AddEntityScreen(
    viewModel: EntitiesViewModel = hiltViewModel(),
    onClick: (String, String, Int, Int, Int, Boolean) -> Unit
) {
    var textNume by rememberSaveable { mutableStateOf("") }
    var textTip by rememberSaveable { mutableStateOf("") }
    var textCantitate by rememberSaveable { mutableStateOf("0") }
    var textPret by rememberSaveable { mutableStateOf("0") }
    var textDiscount by rememberSaveable { mutableStateOf("0") }
    var textStatus by rememberSaveable { mutableStateOf("false") }

    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.9f)) {
        Column() {
            TextField(
                value = textNume,
                onValueChange = {
                    textNume = it
                },
                label = { Text("Nume") }
            )
            TextField(
                value = textTip,
                onValueChange = {
                    textTip = it
                },
                label = { Text("Tip") }
            )
            TextField(
                value = textCantitate,
                onValueChange = {
                    textCantitate = it
                },
                label = { Text("Cantitate") }
            )
            TextField(
                value = textPret,
                onValueChange = {
                    textPret = it
                },
                label = { Text("Pret") }
            )
            TextField(
                value = textDiscount,
                onValueChange = {
                    textDiscount = it
                },
                label = { Text("Discount") }
            )
            TextField(
                value = textStatus,
                onValueChange = {
                    textStatus = it
                },
                label = { Text("Status") }
            )
        }
        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd).padding(25.dp),
            onClick = {
                try {
                    onClick(
                        textNume,
                        textTip,
                        textCantitate.toInt(),
                        textPret.toInt(),
                        textDiscount.toInt(),
                        textStatus.toBoolean()
                    )
                } catch (ex: Exception) {
                    Toast.makeText(context, ex.message, Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "Save")
        }
    }
}