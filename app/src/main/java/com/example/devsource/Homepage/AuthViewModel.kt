package com.example.devsource.Homepage

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.runBlocking

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty()) {
            _authState.value = AuthState.Error("Email cannot be empty")
            return
        }
        if (password.isEmpty()) {
            _authState.value = AuthState.Error("Password cannot be empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(
                        task.exception?.message ?: "Something went wrong, please try again"
                    )
                }
            }
    }

    fun signup(email: String, password: String) {
        if (email.isEmpty()) {
            _authState.value = AuthState.Error("Email cannot be empty")
            return
        }
        if (password.isEmpty()) {
            _authState.value = AuthState.Error("Password cannot be empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(
                        task.exception?.message ?: "Something went wrong, please try again"
                    )
                }
            }
    }
    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }
    fun loginWithGoogle(context: Context) = runBlocking {
        _authState.value = AuthState.Loading
        try {
            val signInResult = GoogleAuthClient(context).signIn()
            if (signInResult) {
                _authState.value = AuthState.Authenticated
            } else {
                _authState.value = AuthState.Error("Google sign-in failed")
            }
        } catch (e: Exception) {
            _authState.value = AuthState.Error("Google sign-in failed: ${e.message}")
            if (e is CancellationException) throw e
        }
    }
}
sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}