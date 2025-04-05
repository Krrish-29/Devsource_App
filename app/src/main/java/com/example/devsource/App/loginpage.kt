package com.example.devsource.App

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.devsource.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay

@Composable
fun LoginPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel,useremail:MutableState<String>) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                navController.navigate("home")
            }
            is AuthState.Error -> Toast.makeText(context,(authState.value as AuthState.Error).message, Toast.LENGTH_LONG).show()
            else -> Unit
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier= Modifier.height(14.dp))
            Image(
                painterResource(R.drawable.logo2),
                contentDescription = "logo",
            )
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 35.dp),
//            ) {
//                Button(
//                    onClick = { authViewModel.loginWithGoogle(context) },
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(26.dp),
//                ) {
//                    Row(
//                        modifier = Modifier.padding(8.dp),
//                        verticalAlignment = Alignment.CenterVertically,
//
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.google),
//                            contentDescription = "Google",
//                            modifier = Modifier.size(22.dp).background(color = Color.White, shape = RoundedCornerShape(20.dp))
//                        )
//                        Spacer(modifier = Modifier.width(12.dp))
//                        Text(
//                            text = "Login With Google",
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                }
//                Spacer(modifier = Modifier.height(12.dp))
//                Button(
//                    onClick = { authViewModel.loginWithGithub(context) },
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(26.dp)
//                ) {
//                    Row(
//                        modifier = Modifier.padding(8.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.github),
//                            contentDescription = "Github",
//                            modifier = Modifier.size(22.dp).background(color = Color.White,shape = RoundedCornerShape(23.dp))
//                        )
//                        Spacer(modifier = Modifier.width(12.dp))
//                        Text(
//                            text = "Login With Github",
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 16.sp
//                        )
//                    }
//                }
//            }
//            Spacer(modifier = Modifier.height(15.dp))
//            Text(
//                text = "━━━━ Or Continue With Email ━━━━",
//                fontSize = 16.sp
//            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp),
                leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = null) },
                shape = RoundedCornerShape(23.dp),
                singleLine = true,
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp),
                leadingIcon = { Icon(imageVector = Icons.Filled.Lock, contentDescription = null) },
                shape = RoundedCornerShape(23.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 35.dp),

                horizontalArrangement = Arrangement.End,){
            Text(text = "Forgotten Password ?", fontSize = 14.sp, modifier = Modifier.clickable {
                navController.navigate("email-input-for-otp")
            })
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                onClick = { authViewModel.login(email, password) },
                enabled = authState.value != AuthState.Loading
            ) {
                Text(
                    text = "Login !",
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Don’t Have Account ?", fontSize = 14.sp)

                TextButton(
                    onClick = {
                        navController.navigate("signup")
                    }
                ) {
                    Text(text = "Sign Up !!", fontSize = 18.sp , fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
@Composable
fun Fetchdata(modifier: Modifier = Modifier, navController: NavController, selectedCategory: MutableState<String>, membersMap: MutableState<Map<String, List<String>>>,aboutteamsmap: MutableState<Map<String, List<String>>>, tasksmap:MutableState<Map<String, List<Pair<String, String>>>>) {
    val database = FirebaseDatabase.getInstance()
    val membersref = database.getReference("Members")
    val hasNavigated = remember { mutableStateOf(false) }
    DisposableEffect(Unit) {
        val memberslistener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categorizedMembers = mutableMapOf<String, MutableList<String>>()
                for (categorySnapshot in snapshot.children) {
                    val category = categorySnapshot.key.orEmpty()
                    val members = mutableListOf<String>()
                    for (memberSnapshot in categorySnapshot.children) {
                        val member = memberSnapshot.key.orEmpty()
                        members.add(member)
                    }
                    categorizedMembers[category] = members
                }
                membersMap.value = categorizedMembers

//                if (categorizedMembers.isNotEmpty()) {
//                    selectedCategory.value = categorizedMembers.keys.first()
//                    if (!hasNavigated.value) {
//                        hasNavigated.value = true
//                        navController.navigate("home") {
//                            popUpTo("fetchdata") {
//                                inclusive = true
//                            }
//                        }
//                    }
//                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Failed to read data: ${error.message}")
            }
        }
        membersref.addValueEventListener(memberslistener)
        onDispose {
            membersref.removeEventListener(memberslistener)
        }
    }
//    if (membersMap.value.isEmpty()) {
//        LoadingScreen()
//    }
    val aboutref=database.getReference("About")
    DisposableEffect(Unit) {
        val aboutlisterner = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categorizedAbout = mutableMapOf<String, String>()
                    for (teamSnapshot in snapshot.children) {
                        val teamName = teamSnapshot.key.orEmpty() // Team name
                        val description = teamSnapshot.getValue(String::class.java).orEmpty()
                        categorizedAbout[teamName] = description
                    }
                    aboutteamsmap.value = categorizedAbout.mapValues { listOf(it.value) }

//                if (categorizedAbout.isNotEmpty()) {
//                    selectedCategory.value = categorizedAbout.keys.first()
//                    if (!hasNavigated.value) {
//                        hasNavigated.value = true
//                        navController.navigate("home") {
//                            popUpTo("fetchdata") {
//                                inclusive = true
//                            }
//                        }
//                    }
//                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Failed to read data: ${error.message}")
            }
        }
        aboutref.addValueEventListener(aboutlisterner)
        onDispose {
            aboutref.removeEventListener(aboutlisterner)
        }
    }
//    if (aboutteamsmap.value.isEmpty()) {
//        LoadingScreen()
//    }
    val tasksref=database.getReference("Tasks")
    DisposableEffect(Unit) {
        val taskslistener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categorizedtasks = mutableMapOf<String, MutableList<Pair<String, String>>>()
                for (categorySnapshot in snapshot.children) {
                    val category = categorySnapshot.key.orEmpty()
                    val tasks  = mutableListOf<Pair<String, String>>()
                    for (taskSnapshot in categorySnapshot.children) {
                        val taskName = taskSnapshot.key.orEmpty()
                        val taskDescription = taskSnapshot.getValue(String::class.java).orEmpty()
                        tasks.add(taskName to taskDescription)
                    }
                    categorizedtasks[category]=tasks
                }
                tasksmap.value = categorizedtasks


                if (categorizedtasks.isNotEmpty()) {
                    selectedCategory.value = categorizedtasks.keys.first()
                    if (!hasNavigated.value) {
                        hasNavigated.value = true
                        navController.navigate("home") {
                            popUpTo("fetchdata") {
                                inclusive = true
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Failed to read data: ${error.message}")
            }
        }
        tasksref.addValueEventListener(taskslistener)
        onDispose {
            tasksref.removeEventListener(taskslistener)
        }
    }
    if (tasksmap.value.isEmpty()) {
        LoadingScreen()
    }
}
@Composable
fun LoadingScreen() {
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(3000)
        isLoading = false
    }
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Please Wait",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
