package com.example.capstoneredouan.data.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.example.capstoneredouan.data.model.User
import com.example.capstoneredouan.data.utils.Resource
import com.example.capstoneredouan.ui.view.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.withTimeout

class LoginRepository {
    val loadingUser: MutableLiveData<Resource<User>> get() = _loadingUser

    private val _loadingUser: MutableLiveData<Resource<User>> = MutableLiveData(Resource.Empty())

    suspend fun loginUser(
        email: String,
        password: String,
        context: Context,
        navController: NavHostController
    ) {
        try {
            _loadingUser.value = Resource.Loading()

            withTimeout(5000) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = FirebaseAuth.getInstance().currentUser
                            if (user != null) {
                                navController.navigate(Screen.PrayerTimesScreen.route)
                            }
                            _loadingUser.value = Resource.Success()
                        } else {
                            Toast.makeText(
                                context,
                                "Invalid login credentials. Please verify your email and password and try again.",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate(Screen.Login.route)
                        }
                    }
            }

        } catch (e: UserLoginError) {
            throw UserLoginError("Something went wrong in the repository while logging in!", e)
        }
    }

    suspend fun registerUser(
        email: String,
        password: String,
        displayName: String,
        context: Context,
        navController: NavHostController
    ) {
        withTimeout(5000) {
            try {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = hashMapOf(
                                "displayName" to displayName
                            )
                            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

                            FirebaseFirestore.getInstance().collection("users")
                                .document(userId)
                                .set(user)
                                .addOnSuccessListener {
                                    navController.navigate(Screen.Login.route)
                                    Toast.makeText(
                                        context,
                                        "You have successfully created an account",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        context,
                                        "Failed to create account please contact the staff",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        } else {
                            println("Registration failed")
                        }
                    }
            } catch (e: UserRegisterError) {
                throw UserRegisterError("Something went wrong in the repository while registering!", e)
            }
        }
    }

    class UserLoginError(message: String, cause: Throwable) : Exception(message, cause)
    class UserRegisterError(message: String, cause: Throwable) : Exception(message, cause)
}
