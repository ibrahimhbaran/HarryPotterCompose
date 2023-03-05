package com.play.harrypottercompose.ui.view.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.play.harrypottercompose.data.Result
import com.play.harrypottercompose.data.entites.Character


@Composable
fun HomeScreen(
    onNavigateToDetailScreen: (Character) -> Unit = {},
    vieModel: HomeViewModel
) {
    val harryPotterCharacters = vieModel.result.observeAsState()
    when (harryPotterCharacters.value) {
        is Result.Success -> {
            val data = (harryPotterCharacters.value as Result.Success<List<Character>>).data!!
            CharacterList(characters = data, onItemClick = onNavigateToDetailScreen)
        }
    }
}

@Composable
fun CharacterList(
    characters: List<Character>,
    onItemClick: (Character) -> Unit
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    var selectedItem by remember {
        mutableStateOf(characters[0])
    }
    if (showDialog) {
        showWand(character = selectedItem) {
            showDialog = false
        }
    }
    LazyColumn {
        items(items = characters) { character ->
            CharacterCard(item = character,
                onItemClick = { onItemClick(it) }
            ) {
                selectedItem = it
                showDialog = true
            }
        }
    }
}

@Composable
fun CharacterCard(
    item: Character,
    onItemClick: (Character) -> Unit,
    onWandClick: (Character) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .clickable { onItemClick(item) },
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(
            modifier = Modifier.padding(all = 8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(item.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .border(5.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = item.name,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = item.species)

            }
            Spacer(Modifier.weight(1f))
            Button(onClick = { onWandClick(item) }) {
                Text(text = "Wand")
                //Icon(painter = , contentDescription = )
            }
        }
    }
}

@Composable
fun showWand(character: Character, onClose: () -> Unit) {

    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {
            Button(onClick = onClose) {
                Text(text = "OK")
            }
        },
        title = { Text(text = character.name) },
        text = {
            Text(text = "${character.wand.wood}-${character.wand.length}-${character.wand.core}")
        }
    )
}

