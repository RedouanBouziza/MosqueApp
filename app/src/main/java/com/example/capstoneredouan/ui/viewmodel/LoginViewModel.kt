package com.example.capstoneredouan.ui.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.capstoneredouan.data.repository.LoginRepository
import com.example.capstoneredouan.data.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val loginRepository = LoginRepository()

    val currentUserId = loginRepository.loadingUser

    fun loginUser(
        email: String,
        password: String,
        context: Context,
        navController: NavHostController
    ) {
        viewModelScope.launch {
            try {
                loginRepository.loginUser(email, password, context, navController)
            } catch (e: LoginRepository.UserLoginError) {
                val errorMsg = "Something went wrong while logging in, in the viewModel"
                Log.e(TAG, e.message ?: errorMsg)
            }
        }
    }

    fun registerUser(
        email: String,
        password: String,
        displayName: String,
        context: Context,
        navController: NavHostController
    ) {
        viewModelScope.launch {
            try {
                loginRepository.registerUser(email, password, displayName, context, navController)
            }catch (e: LoginRepository.UserRegisterError){
                val errorMsg = "Something went wrong while registering user, in the viewModel"
                Log.e(TAG, e.message ?: errorMsg)
            }
        }
    }

}

