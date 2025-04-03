package com.example.devsource.App

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.devsource.Homepage.AuthViewModel
import com.example.devsource.R

@Composable
fun Home(modifier: Modifier = Modifier, authViewModel: AuthViewModel, navController: NavController) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Animated background gradients
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0A0A0F),
                            Color(0xFF121218)
                        )
                    )
                )
        )

        // Blurred neon orbs in background
        Box(
            modifier = Modifier
                .size(250.dp)
                .offset(x = (-80).dp, y = 100.dp)
                .alpha(0.15f)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFF00E5FF), Color.Transparent),
                        radius = 250f
                    )
                )
        )

        Box(
            modifier = Modifier
                .size(180.dp)
                .offset(x = 180.dp, y = 400.dp)
                .alpha(0.12f)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFFFF00E5), Color.Transparent),
                        radius = 180f
                    )
                )
        )

        Box(
            modifier = Modifier
                .size(220.dp)
                .offset(x = 120.dp, y = 150.dp)
                .alpha(0.08f)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFF00FF85), Color.Transparent),
                        radius = 220f
                    )
                )
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // DevSource Club Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF00E5FF).copy(alpha = 0.7f),
                            Color(0xFFFF00E5).copy(alpha = 0.7f),
                            Color(0xFF00FF85).copy(alpha = 0.7f)
                        )
                    )
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF1A1A22).copy(alpha = 0.9f),
                                    Color(0xFF15151D).copy(alpha = 0.85f)
                                )
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Logo with glow
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            Color(0xFF00E5FF).copy(alpha = 0.2f),
                                            Color.Transparent
                                        ),
                                        radius = 120f
                                    ),
                                    shape = RoundedCornerShape(60.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.logo2),
                                contentDescription = "DevSource Logo",
                                modifier = Modifier.size(100.dp),
                                colorFilter = ColorFilter.tint(
                                    Color.White.copy(alpha = 0.9f),
                                    blendMode = BlendMode.SrcAtop
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Title with gradient
                        Text(
                            text = "DevSource Club",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF00E5FF),
                                        Color(0xFFFF00E5),
                                        Color(0xFF00FF85)
                                    )
                                )
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Description with soft glow
                        Text(
                            text = "A community of innovative developers building the future of technology",
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center,
                            lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Glowing button
                        Button(
                            onClick = { /* Handle action */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(50.dp)
                                .border(
                                    width = 1.dp,
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFF00E5FF),
                                            Color(0xFFFF00E5),
                                            Color(0xFF00FF85)
                                        )
                                    ),
                                    shape = RoundedCornerShape(25.dp)
                                )
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFF00E5FF).copy(alpha = 0.2f),
                                            Color(0xFFFF00E5).copy(alpha = 0.2f),
                                            Color(0xFF00FF85).copy(alpha = 0.2f)
                                        )
                                    ),
                                    shape = RoundedCornerShape(25.dp)
                                )
                        ) {
                            Text(
                                text = "Explore",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            // Additional content can be added here
        }
    }
}
@Composable
private fun DomainBadge(icon: ImageVector, label: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .background(
                    color = color.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}