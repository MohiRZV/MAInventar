package com.mohi.mainventar.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohi.mainventar.model.domain.Entity
import com.mohi.mainventar.model.viewmodel.EntitiesViewModel
import com.mohi.mainventar.utils.InternetStatus
import com.mohi.mainventar.utils.InternetStatusLive
import com.mohi.parkingappma.components.CircularIndeterminateProgressBar

@ExperimentalMaterialApi
@Composable
fun DisplayAllScreen(
    viewModel: EntitiesViewModel = hiltViewModel(),
    onClick: () -> Unit,
    onSwitchToTopView: () -> Unit
) {
    val loading by remember { viewModel.loading }
    val listOfEntities by remember { viewModel.listOfEntities }

    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        LazyColumn {
            items(listOfEntities) { item ->
                SingleEntityItem(entity = item)
            }
        }
        CircularIndeterminateProgressBar(isDisplayed = loading)
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(25.dp),
            onClick = { onClick() }
        ) {
            Text(text = "Add")
        }
        val context = LocalContext.current
        Button(
            onClick = {

                if (InternetStatusLive.status.value == InternetStatus.ONLINE)
                    onSwitchToTopView()
                else
                    Toast.makeText(context, "Only available online!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(end = 25.dp, bottom = 25.dp)
        ) {
            Text(text = "See top")
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SingleEntityItem(
    entity: Entity
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Text(text = "nume: ${entity.nume}")
                    Spacer(modifier = Modifier.padding(start = 25.dp, end = 25.dp))
                    Text(text = "cantitate: ${entity.cantitate}")
                }
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Text(text = "pret: ${entity.pret}")
                    Spacer(modifier = Modifier.padding(start = 25.dp, end = 25.dp))
                    Text(text = "tip: ${entity.tip}")
                }
            }
        }
    }
}