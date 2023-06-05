package com.example.capstoneredouan.ui.view.auth

import android.widget.Toast
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

    LazyColumn() {
        item {
            RegistrationFields(navController, viewModel)
        }
    }
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

    Column(

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.registration),
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                color = colorResource(id = R.color.black)
            )
            OutlinedTextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                    blankFirstName = it.isBlank()
                },
                label = { Text(text = stringResource(id = R.string.firstName)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = if (blankFirstName) Color.Red else MaterialTheme.colors.onSurface,
                    focusedLabelColor = if (blankFirstName) Color.Red else MaterialTheme.colors.onSurface,
                )
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                    blankLastName = it.isBlank()
                },
                label = { Text(text = stringResource(id = R.string.lastName)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = if (blankLastName) Color.Red else MaterialTheme.colors.onSurface,
                    focusedLabelColor = if (blankLastName) Color.Red else MaterialTheme.colors.onSurface,
                )
            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    blankEmail = it.isBlank()
                },
                label = { Text(text = stringResource(id = R.string.email)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = if (blankEmail) Color.Red else MaterialTheme.colors.onSurface,
                    focusedLabelColor = if (blankEmail) Color.Red else MaterialTheme.colors.onSurface,
                )
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    blankPassword = it.isBlank()
                },
                label = { Text(text = stringResource(id = R.string.password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = if (blankPassword) Color.Red else MaterialTheme.colors.onSurface,
                    focusedLabelColor = if (blankPassword) Color.Red else MaterialTheme.colors.onSurface,
                )
            )
            OutlinedTextField(
                value = verifyPassword,
                onValueChange = { verifyPassword = it },
                label = { Text(text = stringResource(id = R.string.verify_password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .width(160.dp),
                onClick = {
                    when {
                        firstName.isBlank() -> {
                            blankFirstName = true
                            Toast.makeText(
                                context,
                                R.string.empty_firstname,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        lastName.isBlank() -> {
                            blankLastName = true
                            Toast.makeText(
                                context,
                                R.string.empty_lastname,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        email.isBlank() -> {
                            blankEmail = true
                            Toast.makeText(
                                context,
                                R.string.empty_email,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        password.isBlank() -> {
                            blankPassword = true
                            Toast.makeText(
                                context,
                                R.string.empty_password,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        password != verifyPassword -> {
                            Toast.makeText(
                                context,
                                R.string.password_mismatch,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {
                            blankFirstName = false
                            blankLastName = false
                            blankEmail = false
                            blankPassword = false

                            viewModel.registerUser(
                                email,
                                password,
                                displayName,
                                context,
                                navController
                            )

                            Toast.makeText(context, R.string.successful_registration, Toast.LENGTH_SHORT).show()

                        }
                    }

                }
            ) {
                Text(
                    text = stringResource(id = R.string.register),
                    Modifier.padding(10.dp),
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
                )
            }
            ClickableText(
                text = AnnotatedString(stringResource(id = R.string.already_have_account)),
                modifier = Modifier.padding(top = 25.dp),
                onClick = {
                    navController.navigate(Screen.Login.route)
                },
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            )
        }
    }
}