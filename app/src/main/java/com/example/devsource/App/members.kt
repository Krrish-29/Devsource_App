package com.example.devsource.App

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Members(modifier: Modifier = Modifier, selectedCategory: MutableState<String>, membersMap: MutableState<Map<String, List<String>>>, aboutmap: MutableState<Map<String, List<String>>>) {
    val teamColor = when {
        selectedCategory.value.contains("Android", ignoreCase = true) -> Color(0xFF3DDC84)
        selectedCategory.value.contains("Web", ignoreCase = true) -> Color(0xFF4285F4)
        selectedCategory.value.contains("Game", ignoreCase = true) -> Color(0xFFE91E63)
        else -> MaterialTheme.colorScheme.primary
    }

    val cardBackgroundColor = teamColor.copy(alpha = 0.05f)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val teamCategories = membersMap.value.keys.toList()
            teamCategories.take(3).forEach { category ->
                val isSelected = selectedCategory.value == category
                val teamIcon = when {
                    category.contains("Android", ignoreCase = true) -> Icons.Default.Android
                    category.contains("Web", ignoreCase = true) -> Icons.Default.Language
                    category.contains("Game", ignoreCase = true) -> Icons.Default.SportsEsports
                    else -> Icons.Default.Person
                }
                val iconTint = when {
                    category.contains("Android", ignoreCase = true) -> Color(0xFF3DDC84)
                    category.contains("Web", ignoreCase = true) -> Color(0xFF4285F4)
                    category.contains("Game", ignoreCase = true) -> Color(0xFFE91E63)
                    else -> MaterialTheme.colorScheme.onSurface
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = if (isSelected) iconTint.copy(alpha = 0.2f) else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { selectedCategory.value = category }
                        .padding(vertical = 8.dp, horizontal = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = teamIcon,
                            contentDescription = "Team icon",
                            tint = if (isSelected) iconTint else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = category,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) iconTint else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            maxLines = 1,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val teamIcon = when {
            selectedCategory.value.contains("Android", ignoreCase = true) -> Icons.Default.Android
            selectedCategory.value.contains("Web", ignoreCase = true) -> Icons.Default.Language
            selectedCategory.value.contains("Game", ignoreCase = true) -> Icons.Default.SportsEsports
            else -> Icons.Default.Person
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Members",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = teamColor
                    ),
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }

            val members = membersMap.value[selectedCategory.value] ?: emptyList()
            items(members) { member ->
                Card(
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, teamColor.copy(alpha = 0.5f))
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(cardBackgroundColor)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = member,
                            style = TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "About ${selectedCategory.value} Team",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = teamColor
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            item {
                val about = aboutmap.value[selectedCategory.value]?.joinToString(separator = "\n") ?: "No information available."
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, teamColor.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(cardBackgroundColor)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = teamIcon,
                            contentDescription = "About icon",
                            tint = teamColor,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(bottom = 12.dp)
                        )

                        Text(
                            text = about,
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}