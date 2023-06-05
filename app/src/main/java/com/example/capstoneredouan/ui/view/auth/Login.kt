package com.example.capstoneredouan.ui.view.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.capstoneredouan.R
import com.example.capstoneredouan.data.utils.Resource
import com.example.capstoneredouan.ui.view.Screen
import com.example.capstoneredouan.ui.viewmodel.LoginViewModel

@Composable
fun Login(navController: NavHostController, viewModel: LoginViewModel) {
    val currentUserId by viewModel.currentUserId.observeAsState(initial = Resource.Empty())

    when (currentUserId) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.scale(2f)
                )
            }
        }

        else -> {
            // Render the login screen
            LoginScreen(navController, viewModel)
        }
    }

}

@Composable
private fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel) {
    val image: Painter = painterResource(id = R.drawable.mosque_login)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var blankEmail by remember { mutableStateOf(false) }
    var blankPassword by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val resources = context.resources


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.mosque_green_background))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(R.color.white))
            //TODO: Remove this border later on when the design is done and the app is ready to be published
//                .border(BorderStroke(2.dp, SolidColor(Color.Magenta)))
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
                    //TODO: Remove this border later on when the design is done and the app is ready to be published
//                    .border(BorderStroke(2.dp, SolidColor(Color.Red)))
                    .padding(15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = image,
                    contentDescription = "Mosque",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(id = R.string.sign_in),
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.black),
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    //TODO: Remove this border later on when the design is done and the app is ready to be published
//                    .border(BorderStroke(2.dp, SolidColor(Color.Green)))
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = email,
                    onValueChange = {
                        email = it
                        blankEmail = it.isBlank()
                    },
                    placeholder = { Text(text = stringResource(id = R.string.email)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = colorResource(id = R.color.light_gray),
                        focusedBorderColor = if (blankEmail) Color.Red
                        else Color.Transparent,
                        unfocusedBorderColor = if (blankEmail) Color.Red
                        else Color.Transparent,
                        placeholderColor = if (blankEmail) Color.Red
                        else MaterialTheme.colors.onSurface,
                        cursorColor = if (blankEmail) Color.Red else Color.Black,
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = password,
                    onValueChange = {
                        password = it
                        blankPassword = it.isBlank()
                    },
                    placeholder = { Text(text = stringResource(id = R.string.password)) },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = colorResource(id = R.color.light_gray),
                        focusedBorderColor = if (blankPassword) Color.Red
                        else Color.Transparent,
                        unfocusedBorderColor = if (blankPassword) Color.Red
                        else Color.Transparent,
                        placeholderColor = if (blankPassword) Color.Red
                        else MaterialTheme.colors.onSurface,
                        cursorColor = if (blankPassword) Color.Red else Color.Black,
                    )
                )

                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 30.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth(),
                    onClick = {
                        when {
                            email.isBlank() && password.isBlank() -> {
                                blankEmail = true
                                blankPassword = true
                                Toast.makeText(
                                    context,
                                    resources.getString(R.string.empty_email_and_password),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                            email.isBlank() -> {
                                blankEmail = true
                                Toast.makeText(
                                    context,
                                    resources.getString(R.string.empty_email),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                            password.isBlank() -> {
                                blankPassword = true
                                Toast.makeText(
                                    context,
                                    resources.getString(R.string.empty_password),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                            else -> {
                                blankEmail = false
                                blankPassword = false

                                viewModel.loginUser(email, password, context, navController)
                            }
                        }
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_in),
                        style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        //TODO: Remove this border later on when the design is done and the app is ready to be published
//                        .border(BorderStroke(2.dp, SolidColor(Color.Green)))
                        .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(id = R.string.no_account),
                        style = MaterialTheme.typography.body1.copy(),
                        color = colorResource(id = R.color.gray)
                    )

                    Spacer(modifier = Modifier.padding(5.dp))

                    ClickableText(
                        text = AnnotatedString(stringResource(id = R.string.sign_up)),
                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                        onClick = {
                            navController.navigate(Screen.Registration.route)
                        },
                    )
                }
            }
        }
    }
}

