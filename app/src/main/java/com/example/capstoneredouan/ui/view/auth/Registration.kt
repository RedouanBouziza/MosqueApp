package com.example.capstoneredouan.ui.view.auth

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.capstoneredouan.R
import com.example.capstoneredouan.ui.view.Screen
import com.example.capstoneredouan.ui.viewmodel.LoginViewModel

@Composable
fun Registration(navController: NavHostController, viewModel: LoginViewModel) {
    RegistrationFields(navController, viewModel)
}

@Composable
private fun RegistrationFields(navController: NavHostController, viewModel: LoginViewModel) {
    val context = LocalContext.current
    var firstName by remember { mutableStateOf(String()) }
    var lastName by remember { mutableStateOf(String()) }
    var email by remember { mutableStateOf(String()) }
    var password by remember { mutableStateOf(String()) }
    var verifyPassword by remember { mutableStateOf(String()) }
    val displayName = "$firstName $lastName"

    var blankFirstName by remember { mutableStateOf(false) }
    var blankLastName by remember { mutableStateOf(false) }
    var blankEmail by remember { mutableStateOf(false) }
    var blankPassword by remember { mutableStateOf(false) }
    var blankVerifyPassword by remember { mutableStateOf(false) }


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
                .background(colorResource(R.color.white)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 20.dp),
                    text = stringResource(id = R.string.registration),
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.black)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    value = firstName,
                    onValueChange = {
                        firstName = it
                        blankFirstName = it.isBlank()
                    },
                    placeholder = { Text(text = stringResource(id = R.string.firstName)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = colorResource(id = R.color.light_gray),
                        focusedBorderColor = if (blankFirstName) Color.Red
                        else Color.Transparent,
                        unfocusedBorderColor = if (blankFirstName) Color.Red
                        else Color.Transparent,
                        placeholderColor = if (blankFirstName) Color.Red
                        else MaterialTheme.colors.onSurface,
                        cursorColor = if (blankFirstName) Color.Red else Color.Black,
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    value = lastName,
                    onValueChange = {
                        lastName = it
                        blankLastName = it.isBlank()
                    },
                    placeholder = { Text(text = stringResource(id = R.string.lastName)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = colorResource(id = R.color.light_gray),
                        focusedBorderColor = if (blankLastName) Color.Red
                        else Color.Transparent,
                        unfocusedBorderColor = if (blankLastName) Color.Red
                        else Color.Transparent,
                        placeholderColor = if (blankLastName) Color.Red
                        else MaterialTheme.colors.onSurface,
                        cursorColor = if (blankLastName) Color.Red else Color.Black,
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
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
                        .fillMaxWidth()
                        .height(55.dp),
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

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    value = verifyPassword,
                    onValueChange = {
                        verifyPassword = it
                        blankVerifyPassword = it.isBlank()
                    },
                    placeholder = { Text(text = stringResource(id = R.string.verify_password)) },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = colorResource(id = R.color.light_gray),
                        focusedBorderColor = if (blankVerifyPassword) Color.Red
                        else Color.Transparent,
                        unfocusedBorderColor = if (blankVerifyPassword) Color.Red
                        else Color.Transparent,
                        placeholderColor = if (blankVerifyPassword) Color.Red
                        else MaterialTheme.colors.onSurface,
                        cursorColor = if (blankVerifyPassword) Color.Red else Color.Black,
                    )
                )

                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 30.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .height(55.dp),
                    onClick = {
                        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank() || verifyPassword.isBlank()) {
                            if (firstName.isBlank()) {
                                blankFirstName = true
                                Toast.makeText(
                                    context,
                                    R.string.empty_firstname,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            if (lastName.isBlank()) {
                                blankLastName = true
                                Toast.makeText(
                                    context,
                                    R.string.empty_lastname,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            if (email.isBlank()) {
                                blankEmail = true
                                Toast.makeText(
                                    context,
                                    R.string.empty_email,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            if (password.isBlank()) {
                                blankPassword = true
                                Toast.makeText(
                                    context,
                                    R.string.empty_password,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            if (verifyPassword.isBlank()) {
                                blankVerifyPassword = true
                                Toast.makeText(
                                    context,
                                    R.string.empty_password,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else if (password != verifyPassword) {
                            blankPassword = true
                            blankVerifyPassword = true
                            Toast.makeText(
                                context,
                                R.string.password_mismatch,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            blankFirstName = false
                            blankLastName = false
                            blankEmail = false
                            blankPassword = false
                            blankVerifyPassword = false

                            viewModel.registerUser(
                                email,
                                password,
                                displayName,
                                context,
                                navController
                            )
                        }
                    }

                ) {
                    Text(
                        modifier = Modifier.padding(top = 5.dp),
                        text = stringResource(id = R.string.register),
                        style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(id = R.string.already_have_account),
                        style = MaterialTheme.typography.body1.copy(),
                        color = colorResource(id = R.color.gray)
                    )

                    Spacer(modifier = Modifier.padding(5.dp))

                    ClickableText(
                        text = AnnotatedString(stringResource(id = R.string.sign_in)),
                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                        onClick = {
                            navController.navigate(Screen.Login.route)
                        },
                    )
                }
            }
        }
    }
}