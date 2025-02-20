package com.example.devsource.Homepage

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.devsource.R

@Composable
fun LoginPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState=authViewModel.authState.observeAsState()
    val context= LocalContext.current
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate("home")
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_LONG).show()
            else -> Unit
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", fontSize = 34.sp , fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painterResource(R.drawable.logo2),
            contentDescription = "logo",
        )
        //Text(text = "Login", fontSize = 27.sp)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 35.dp),
            leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = null) }
            ,
            shape = RoundedCornerShape(23.dp),
            singleLine = true,
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") }
        )

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 35.dp),
            leadingIcon = { Icon(imageVector = Icons.Filled.Lock, contentDescription = null) }
            ,
            shape = RoundedCornerShape(23.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") }

        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),onClick = {
            authViewModel.login(email, password)
        }, enabled=authState.value!=AuthState.Loading) {
            Text(text="Login",
                fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text ="Don't have an account ? ")
        TextButton(
            onClick = {
            navController.navigate("signup")
        }) {
            Text(text="Sign Up !!" , fontSize = 18.sp)
        }
    }
}