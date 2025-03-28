package com.example.devsource.Homepage

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.devsource.R

@Composable
fun OtpPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val authState = authViewModel.authState.observeAsState()

    var otpValue by remember { mutableStateOf(TextFieldValue("", TextRange(0))) }
    val maxChar = 6

    val neonOrange = Color(0xFFFF6B00)
    val brightOrange = Color(0xFFFF9D45)
    val deepRed = Color(0xFFD13300)
    val darkBackground = Color(0xFF1A0D0A)

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate("fetchdata")
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_LONG
            ).show()
            else -> Unit
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
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
        )

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
                        text = "Verify Your Email",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Please enter the 6-digit code sent to your email",
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    BasicTextField(
                        value = otpValue,
                        onValueChange = {
                            if (it.text.length <= maxChar) {
                                otpValue = it
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        decorationBox = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val charArray = if (otpValue.text.length <= maxChar) {
                                    otpValue.text.padEnd(maxChar)
                                } else {
                                    otpValue.text.substring(0, maxChar)
                                }

                                for (i in charArray.indices) {
                                    val char = charArray[i]
                                    val isActive = i < otpValue.text.length

                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(58.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(
                                                color = if (isActive)
                                                    Color(0xFF3A2113)
                                                else
                                                    Color(0xFF2A1813),
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                            .border(
                                                width = 1.5.dp,
                                                brush = if (isActive) {
                                                    Brush.linearGradient(
                                                        colors = listOf(
                                                            neonOrange,
                                                            brightOrange
                                                        )
                                                    )
                                                } else {
                                                    Brush.linearGradient(
                                                        colors = listOf(
                                                            Color.White.copy(alpha = 0.3f),
                                                            Color.White.copy(alpha = 0.1f)
                                                        )
                                                    )
                                                },
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                    ) {
                                        Text(
                                            text = if (char != ' ') char.toString() else "",
                                            style = MaterialTheme.typography.headlineMedium,
                                            color = if (isActive) Color.White else Color.White.copy(alpha = 0.6f),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(36.dp))

                    Button(
                        onClick = {
                            if (otpValue.text.length == maxChar) {
                                navController.navigate("fetchdata")
                            } else {
                                Toast.makeText(context, "Please enter a valid 6-digit OTP", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = neonOrange
                        ),
                        enabled = otpValue.text.length == maxChar
                    ) {
                        Text(
                            text = "Verify & Continue",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Didn't receive code? ",
                            color = Color.White.copy(alpha = 0.7f)
                        )

                        Text(
                            text = "Resend",
                            color = brightOrange,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    }
}