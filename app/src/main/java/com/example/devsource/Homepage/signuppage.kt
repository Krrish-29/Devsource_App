package com.example.devsource.Homepage

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.devsource.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpPage(modifier: Modifier=Modifier, navController: NavController, authViewModel: AuthViewModel){
    val scrollState = rememberScrollState()
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    val pno = 10
    var password by remember { mutableStateOf("") }
    val authState=authViewModel.authState.observeAsState()
    val context= LocalContext.current
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate("home")
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message,Toast.LENGTH_LONG).show()
            else -> Unit
        }
    }
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Sign Up", fontSize = 34.sp, fontWeight = FontWeight.Bold)
            }
        },windowInsets = WindowInsets.statusBars
    )
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(scrollState)
            .padding(WindowInsets.ime.asPaddingValues()) ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {

        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painterResource(R.drawable.logo2),
            contentDescription = "logo",
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 35.dp),
            leadingIcon = { Icon(imageVector = Icons.Filled.Person, contentDescription = null) }
            ,
            shape = RoundedCornerShape(23.dp),
            singleLine = true,
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(

            modifier = Modifier.fillMaxWidth().padding(horizontal = 35.dp),
            leadingIcon = { Icon(imageVector = Icons.Filled.Call, contentDescription = null) }
            ,
            shape = RoundedCornerShape(23.dp),
            singleLine = true,
            value = phone,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            onValueChange = { if (it.length <= pno) phone = it},
            label = { Text(text = "Phone") }
        )
        Spacer(modifier = Modifier.height(8.dp))

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
            visualTransformation = PasswordVisualTransformation(),
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") }

        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            authViewModel.signup(email, password)
        }, enabled=authState.value!=AuthState.Loading)  {
            Text(text="Create Account",fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(5.dp))
        TextButton(onClick = {
            navController.navigate("login")
        }) {
            Text(text="Already have an account!",fontSize = 16.sp)
        }
    }
}