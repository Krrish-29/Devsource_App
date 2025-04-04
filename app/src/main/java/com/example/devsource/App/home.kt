package com.example.devsource.App

import android.util.Log
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.ColorFilter
import com.example.devsource.R
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.devsource.Homepage.AuthState
import com.example.devsource.Homepage.AuthViewModel
import com.google.firebase.database.*

@Composable
fun Home(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    navController: NavController,
) {
    val context = LocalContext.current
    val authState = authViewModel.authState.observeAsState()
    var newsItems by remember { mutableStateOf<List<NewsItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Use theme colors instead of hardcoded values
    val colorScheme = MaterialTheme.colorScheme

    // Using theme colors
    val primaryColor = colorScheme.primary
    val secondaryColor = colorScheme.secondary
    val tertiaryColor = colorScheme.tertiary
    val primaryContainerColor = colorScheme.primaryContainer
    val secondaryContainerColor = colorScheme.secondaryContainer

    // Dark card background
    val darkCardBackground = Color(0xFF252525)

    // Text colors
    val primaryText = Color.White
    val secondaryText = Color.White.copy(alpha = 0.7f)
    val tertiaryText = Color.White.copy(alpha = 0.5f)

    DisposableEffect(Unit) {
        val database = FirebaseDatabase.getInstance()
        val newsRef = database.getReference("news")

        val newsListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newsList = mutableListOf<NewsItem>()
                for (newsSnapshot in snapshot.children) {
                    val title = newsSnapshot.child("title").getValue(String::class.java) ?: ""
                    val content = newsSnapshot.child("content").getValue(String::class.java) ?: ""
                    val timestamp = newsSnapshot.child("timestamp").getValue(String::class.java) ?: ""
                    newsList.add(NewsItem(title, content, timestamp))
                }
                newsItems = newsList
                isLoading = false
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Failed to read news data: ${error.message}")
                isLoading = false
            }
        }

        newsRef.addValueEventListener(newsListener)
        onDispose {
            newsRef.removeEventListener(newsListener)
        }
    }

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {


            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = darkCardBackground
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(24.dp),
                            spotColor = tertiaryColor.copy(alpha = 0.3f)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(primaryColor, secondaryColor)
                                        )
                                    )
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo2),
                                    contentDescription = "DevSource Logo",
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Welcome to DevSource",
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = primaryText
                                    ),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Explore, learn and build with us!",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = secondaryText,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "DevSource is a community of developers working together to build amazing projects. Join our teams and start creating!",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = secondaryText
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor
                            )
                        ) {
                            Text(
                                "Explore Teams",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Our Focus Areas",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = secondaryColor
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
                ) {
                    item { DomainBadge(Icons.Default.Language, "Web Dev", primaryColor) }
                    item { DomainBadge(Icons.Default.Android, "Android Dev", secondaryColor) }
                    item { DomainBadge(Icons.Default.SportsEsports, "Game Dev", tertiaryColor) }
                    item { DomainBadge(Icons.Default.Groups, "DevOps", primaryContainerColor) }
                    item { DomainBadge(Icons.Default.Science, "AI/ML", secondaryContainerColor) }
                }
            }

            item {
                Text(
                    text = "Weekly News",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = secondaryColor)
                    }
                }
            } else if (newsItems.isEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = darkCardBackground
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No news updates available",
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center,
                                color = tertiaryText
                            )
                        }
                    }
                }
            } else {
                items(newsItems) { newsItem ->
                    NewsCard(
                        newsItem,
                        primaryColor,
                        secondaryColor,
                        tertiaryColor,
                        primaryContainerColor
                    )
                }
            }
        }
    }
}

@Composable
fun NewsCard(
    newsItem: NewsItem,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    secondaryColor: Color = MaterialTheme.colorScheme.secondary,
    tertiaryColor: Color = MaterialTheme.colorScheme.tertiary,
    accentColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    var expanded by remember { mutableStateOf(false) }
    val darkCardBackground = Color(0xFF252525)

    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = darkCardBackground
        ),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = newsItem.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    ),
                    modifier = Modifier.weight(1f)
                )

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF121212)
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = newsItem.date,
                        style = MaterialTheme.typography.bodySmall,
                        color = secondaryColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = newsItem.content,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f),
                maxLines = if (expanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = tertiaryColor
                )
            ) {
                Text(
                    if (expanded) "Show Less" else "Read More",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun DomainBadge(icon: ImageVector, label: String, color: Color) {
    // Assign specific colors based on domain type
    val badgeColor = when (label) {
        "Android Dev" -> Color(0xFF3DDC84)  // Android green
        "Web Dev" -> Color(0xFF4285F4)      // Web blue
        "Game Dev" -> Color(0xFFE91E63)     // Game pink
        "DevOps" -> Color(0xFFFF9800)       // Orange for DevOps
        "AI/ML" -> Color(0xFFFFEB3B)        // Yellow for AI/ML
        else -> color
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF252525)
        ),
        modifier = Modifier.width(110.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = Color(0xFF101010),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = badgeColor,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = badgeColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

data class NewsItem(
    val title: String,
    val content: String,
    val date: String
)