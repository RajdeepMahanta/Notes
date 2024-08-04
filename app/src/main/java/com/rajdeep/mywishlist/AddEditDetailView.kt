package com.rajdeep.mywishlist

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rajdeep.mywishlist.data.Wish
import com.rajdeep.mywishlist.ui.theme.AppBarColor
import com.rajdeep.mywishlist.ui.theme.AppColor
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
){

    val snackMessage = remember { mutableStateOf("") }
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    if (id != 0L){
        val wish = viewModel.getAWishById(id).collectAsState(initial = Wish(0L, "", ""))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
    }
    else{
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
    }

    Scaffold(
        topBar = { AppBarView(title =
        if (id != 0L) stringResource(id = R.string.update_note)
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
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(
                label = "Title",
                value = viewModel.wishTitleState,
                height = 56,
                onValueChanged = {
                    viewModel.onWishTitleChanged(it)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(
                label = "Description",
                value = viewModel.wishDescriptionState,
                height = 156,
                onValueChanged = {
                    viewModel.onWishDescriptionChanged(it)
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = {
                if(viewModel.wishTitleState.isNotEmpty() &&
                    viewModel.wishDescriptionState.isNotEmpty()){
                    if(id != 0L){
                        viewModel.updateWish(
                            Wish(
                                id = id,
                                title = viewModel.wishTitleState.trim(),
                                description = viewModel.wishDescriptionState.trim()
                            )
                        )
                        scope.launch {
                            //scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                            navController.navigateUp()
                        }
                        Toast.makeText(context, "Note Updated", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        viewModel.addWish(
                            Wish(title = viewModel.wishTitleState.trim(),
                                description = viewModel.wishDescriptionState.trim())
                        )
                        //snackMessage.value = "Wish has been created"
                        Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show()
                        scope.launch {
                            //scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                            navController.navigateUp()
                        }
                    }
                }
                else{
                    //snackMessage.value = "Enter fields to create a wish"
                    Toast.makeText(context, "Enter Title and Description to add note", Toast.LENGTH_LONG).show()
                }

            }, colors = ButtonDefaults.buttonColors(backgroundColor = AppBarColor  /*Color(0xFFDD1E5f)*/),
                ) {
                Text(
                    text = if (id != 0L) stringResource(id = R.string.update) else stringResource(id = R.string.add),
                    style = TextStyle(
                        fontSize = 18.sp
                    ),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun WishTextField(
    label: String,
    value: String,
    height: Int,
    onValueChanged: (String) -> Unit
){
    OutlinedTextField(value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label, color = Color.Black)},
        modifier = Modifier.fillMaxWidth().padding(4.dp).height(height.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            //Using predefined colors:
            textColor = Color.Black,
            //Using our own colors in res.values.colors:
            cursorColor = colorResource(id = R.color.black),
            focusedBorderColor = colorResource(id = R.color.black),
            unfocusedBorderColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            unfocusedLabelColor = colorResource(id = R.color.black)
        )
    )
}
