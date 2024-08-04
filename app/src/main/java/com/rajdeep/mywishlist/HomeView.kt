package com.rajdeep.mywishlist

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rajdeep.mywishlist.data.Wish
import com.rajdeep.mywishlist.ui.theme.AppColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: WishViewModel
){
    val context = LocalContext.current
    Scaffold(
        topBar = {AppBarView(title = "Notes", {})},
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                contentColor = Color.White,
                backgroundColor = Color.Black,
                onClick = {
                    navController.navigate(Screen.AddScreen.route + "/0L")
                }
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }, backgroundColor = AppColor
    ){
        val wishlist = viewModel.getAllWishes.collectAsState(initial = listOf())
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it)){
            items(wishlist.value, key = {wish -> wish.id}){
                wish ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if(it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd){
                            viewModel.deleteWish(wish)
                            Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show()
                        }
//                        else if(it == DismissValue.DismissedToEnd){
//                            viewModel.updateWish(wish)
//                            val id = wish.id
//                            navController.navigate(Screen.AddScreen.route + "/$id")
//                        }
                        true
                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    background = {
                                 val color by animateColorAsState(
                                     if(dismissState.dismissDirection
                                         == DismissDirection.EndToStart) Color.Red
//                                     else if(dismissState.dismissDirection
//                                         == DismissDirection.StartToEnd) Color(0xFF00D668)
                                     else Color.Transparent
                                    ,label = ""
                                 )
                        val alignment = Alignment.CenterEnd
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = alignment
                        ){
//                            Row(
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                verticalAlignment = Alignment.CenterVertically,
//                                modifier = Modifier.fillMaxSize()
//                            ){
//                                Icon(imageVector = Icons.Default.Edit,
//                                    contentDescription = "Edit Icon",
//                                    tint = Color.White)
//                                Spacer(modifier = Modifier.width(14.dp))
                                Icon(imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Icon",
                                    tint = Color.White)
//                            }
                        }
                    },
                    directions = setOf(DismissDirection.EndToStart/*, DismissDirection.StartToEnd*/),
                    dismissThresholds = {FractionalThreshold(0.75f)},
                    dismissContent = {
                        WishItem(wish = wish, editClick = {
                            navController.navigate(Screen.AddScreen.route +"/${wish.id}")
                        }) {
                            val id = wish.id
                            navController.navigate(Screen.DetailScreen.route + "/$id")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun WishItem(wish: Wish, editClick: () -> Unit, onClick: () -> Unit){
    Card(modifier = Modifier
        .height(85.dp)
        .fillMaxWidth()
        .padding(top = 8.dp, start = 8.dp, end = 12.dp)
        .clickable {
            onClick()
        },
        elevation = 10.dp,
        shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp, topEnd = 8.dp, bottomEnd = 8.dp),
        backgroundColor = Color.White
    ){
        Row(modifier = Modifier.padding(4.dp),
            //verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = wish.title, fontWeight = FontWeight.ExtraBold, modifier = Modifier.fillMaxWidth(0.9f))
                Text(text = wish.description, modifier = Modifier.fillMaxWidth(0.9f))
            }
        }
        Row(modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = editClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
        }
    }
}