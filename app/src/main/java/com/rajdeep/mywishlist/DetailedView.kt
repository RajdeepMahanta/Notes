package com.rajdeep.mywishlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rajdeep.mywishlist.data.Wish
import com.rajdeep.mywishlist.ui.theme.AppColor


@Composable
fun DetailedView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
){
    val scaffoldState = rememberScaffoldState()
    if (id != 0L){
        val wish = viewModel.getAWishById(id).collectAsState(initial = Wish(0L, "", ""))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
    }
    Scaffold(
        topBar = { AppBarView(title =
        if (id != 0L) viewModel.wishTitleState
        else stringResource(id = R.string.add_note)
        ) {navController.navigateUp()} },
        scaffoldState = scaffoldState,
        //backgroundColor = AppColor
    ) {
        Column(modifier = Modifier
            .padding(it)
            .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.height(28.dp))
            Text(text = viewModel.wishDescriptionState,
                modifier = Modifier.fillMaxSize().padding(10.dp),
                color = Color.Black,
                fontSize = 16.sp)
        }
    }
}
