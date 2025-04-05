package com.example.devsource.App

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.devsource.R
import com.google.firebase.auth.oAuthProvider

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val neonOrange = Color(0xFFFF6B00)
    val brightOrange = Color(0xFFFF9D45)
    val deepRed = Color(0xFFD13300)
    val darkBackground = Color(0xFF1A0D0A)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        darkBackground,
                        Color(0xFF2A1409)
                    )
                )
            )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo2),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .alpha(0.15f),
                contentScale = ContentScale.Fit
            )
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .offset(x = (-50).dp, y = 350.dp)
                    .alpha(0.4f)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(neonOrange, Color.Transparent),
                            radius = 300f
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.BottomCenter)
                    .offset(y = 70.dp)
                    .alpha(0.3f)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(deepRed, Color.Transparent),
                            radius = 220f
                        )
                    )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        color = Color(0xFF231711).copy(alpha = 0.75f),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .border(
                        width = 1.5.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                neonOrange.copy(alpha = 0.8f),
                                brightOrange.copy(alpha = 0.6f),
                                deepRed.copy(alpha = 0.4f)
                            )
                        ),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(28.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Forgot Password",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Enter your email address and we'll send you a link to reset your password",
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            isEmailValid = it.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(it).matches()
                        },
                        isError = !isEmailValid,
                        label = { Text("Email") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email Icon",
                                tint = Color.White.copy(alpha = 0.7f)
                            )
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = brightOrange,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                            focusedLabelColor = brightOrange,
                            unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                            errorBorderColor = Color.Red,
                            errorLabelColor = Color.Red
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )

                    if (!isEmailValid) {
                        Text(
                            text = "Please enter a valid email address",
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 8.dp, top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            if (email.isEmpty()) {
                                isEmailValid = false
                            } else if (isEmailValid) {
                                isLoading = true
                                Toast.makeText(
                                    context,
                                    "Password reset link sent to $email",
                                    Toast.LENGTH_LONG
                                ).show()
                                navController.navigate("otp-page")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = neonOrange
                        ),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text(
                                text = "Send Reset Link",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Remember your password? ",
                            color = Color.White.copy(alpha = 0.7f)
                        )

                        TextButton(onClick = { navController.navigate("login") }) {
                            Text(
                                text = "Sign In",
                                color = brightOrange,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}