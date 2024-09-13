package com.wandev.v0to.navigation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.wandev.v0to.R
import com.wandev.v0to.models.Camera
import com.wandev.v0to.models.Category
import com.wandev.v0to.services.ApiService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedOption by remember {
        mutableStateOf(Category(-1, ""))
    }

    var options by remember {
        mutableStateOf(emptyList<Category>())
    }

    var search by remember {
        mutableStateOf("")
    }

    var cameraLists by remember {
        mutableStateOf(emptyList<Camera>())
    }

    LaunchedEffect(selectedOption) {
        GlobalScope.launch {
            cameraLists = ApiService.getCamera(selectedOption.id, null)
        }
    }

    LaunchedEffect(search) {
        GlobalScope.launch {
            cameraLists = ApiService.getCamera(null, search)
        }
    }


    LaunchedEffect(Unit) {
        GlobalScope.launch {
            cameraLists = ApiService.getCamera(null, null)
            options = ApiService.getCategory()
        }
    }

    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.primary))
            .fillMaxSize()
    )
    LazyColumn(modifier = Modifier
        .padding(15.dp)
        .fillMaxHeight()) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.logosmall),
                    contentDescription = ""
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Image(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = R.drawable.baseline_emoji_events_24),
                        contentDescription = ""
                    )
                    Image(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = R.drawable.baseline_shopping_bag_24),
                        contentDescription = ""
                    )
                    Image(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = R.drawable.baseline_receipt_long_24),
                        contentDescription = ""
                    )
                }
            }

            Text(
                text = "All Products",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 15.dp, bottom = 15.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                Column(modifier = Modifier.width(180.dp)) {
                    Text(text = "Category", color = Color.Gray)
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }) {
                        OutlinedTextField(
                            readOnly = true,
                            modifier = Modifier
                                .menuAnchor(),
                            value = selectedOption.name,
                            onValueChange = {},
                            keyboardOptions = KeyboardOptions().copy(
                                imeAction = ImeAction.Done
                            ),
                            label = { Text(text = "") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }) {
                            options.forEach { option ->
                                DropdownMenuItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = { Text(option.name) },
                                    onClick = {
                                        selectedOption = Category(option.id, option.name)
                                        expanded = false
                                    })
                            }
                        }
                    }
                }

                Column(modifier = Modifier.width(180.dp)) {
                    Text(text = "Search", color = Color.Gray)
                    OutlinedTextField(
                        value = search,
                        onValueChange = { search = it }, label = {
                            Text(text = "")
                        },
                        keyboardOptions = KeyboardOptions().copy(
                            imeAction = ImeAction.Done
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                }
            }
        }

        if (cameraLists.isEmpty()) {
            item {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Not found",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.fillMaxWidth().padding(top = 15.dp)
                )
            }
        }

        item {
            LazyVerticalGrid(
                userScrollEnabled = true,
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .fillParentMaxSize()
                    .padding(top = 15.dp),
                columns = GridCells.Fixed(2)
            ) {
                items(cameraLists) { item ->
                    Box(
                        modifier = Modifier
                            .background(colorResource(id = R.color.background))
                            .clickable {
                                navController.navigate("detail/${item.id}") {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                    ) {
                        Column {
                            Image(
                                modifier = Modifier.size(200.dp),
                                contentScale = ContentScale.Crop,
                                bitmap = item.photo.asImageBitmap(),
                                contentDescription = ""
                            )
                            Text(
                                text = item.name,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(
                                    top = 15.dp,
                                    start = 15.dp,
                                    end = 15.dp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = item.resolution,
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                            )
                            Text(
                                text = item.price.toString(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(15.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


