package com.example.devsource.App

import android.content.Intent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.outlined.Copyright
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.credentials.CredentialManager
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.devsource.Homepage.AuthState
import com.example.devsource.Homepage.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.foundation.layout.Row as Row1


@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    selectedCategory: MutableState<String>,
    membersMap: MutableState<Map<String, List<String>>>,
    aboutmap: MutableState<Map<String, List<String>>>,
    tasksmap: MutableState<Map<String, List<Pair<String, String>>>>,
    useremail:MutableState<String>,
    username:MutableState<String>,
) {
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current
    val credentialManager = remember { CredentialManager.create(context) }
    LaunchedEffect(authState.value) {
        when (val state = authState.value) {
            is AuthState.Authenticated -> {
                val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@LaunchedEffect
                authViewModel.fetchUserData(userId) { name ->
                    username.value = name ?: "Unknown"
                }
            }
            is AuthState.Unauthenticated -> {
                navController.navigate("login")
            }
            else -> Unit
        }
    }

    val totalTasks = remember {  mutableIntStateOf(tasksmap.value.values.sumOf { it.size }) }
    val bottomnavItemList=listOf(
        BottomNavItem("Home", Icons.Default.Home,0),
        BottomNavItem("Members", Icons.Default.Groups,0),
        BottomNavItem("Task", Icons.Default.DateRange,totalTasks.intValue)
    )
    val selectedIndexforbottomnav = remember { mutableStateOf(0) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val expanded = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var showdialogAboutus by remember { mutableStateOf(false) }
    var showdialogTermsandcondition by remember { mutableStateOf(false)}
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val photoUrl = auth.currentUser?.photoUrl?.toString()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp),
                drawerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
            ) {
                Spacer(Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .shadow(
                            elevation = 8.dp,
                            spotColor = Color.Black.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(24.dp)
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF121212)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF1E1E1E),
                                        Color(0xFF252525)
                                    ),
                                    start = Offset(0f, 0f),
                                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                                )
                            )
                            .padding(24.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val interactionSource = remember { MutableInteractionSource() }
                            val isPressed by interactionSource.collectIsPressedAsState()
                            val scale by animateFloatAsState(
                                targetValue = if (isPressed) 1.05f else 1f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                ),
                                label = "avatarScale"
                            )

                            Box(
                                modifier = Modifier
                                    .scale(scale)
                                    .clickable(
                                        interactionSource = interactionSource,
                                        indication = null
                                    ) { },
                                contentAlignment = Alignment.Center
                            ) {
                                if (photoUrl != null) {
                                    AsyncImage(
                                        model = photoUrl,
                                        contentDescription = "User Profile",
                                        modifier = Modifier
                                            .size(90.dp)
                                            .clip(CircleShape)
                                            .border(
                                                width = 3.dp,
                                                brush = Brush.sweepGradient(
                                                    colors = listOf(
                                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                                                        Color(0xFF404040),
                                                        Color(0xFF606060),
                                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                                                    )
                                                ),
                                                shape = CircleShape
                                            )
                                            .padding(3.dp)
                                            .background(Color(0xFF1A1A1A), CircleShape)
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .size(90.dp)
                                            .clip(CircleShape)
                                            .border(
                                                width = 3.dp,
                                                brush = Brush.sweepGradient(
                                                    colors = listOf(
                                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                                                        Color(0xFF404040),
                                                        Color(0xFF606060),
                                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                                                    )
                                                ),
                                                shape = CircleShape
                                            )
                                            .padding(3.dp)
                                            .background(Color(0xFF1A1A1A), CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Person,
                                            contentDescription = null,
                                            modifier = Modifier.size(45.dp),
                                            tint = Color(0xFFFF9800)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = username.value,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.White.copy(alpha = 0.95f)
                            )

                            Box(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .width(40.dp)
                                    .height(2.dp)
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                                                Color.Transparent
                                            )
                                        ),
                                        shape = RoundedCornerShape(1.dp)
                                    )
                            )

                            Box(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .background(
                                        color = Color(0xFF2A2A2A),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = auth.currentUser?.email ?: useremail.value,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White.copy(alpha = 0.8f)
                                    )
                                }
                            }
                        }
                    }
                }


                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedIndexforbottomnav.value == 0,
                    onClick = {
                        selectedIndexforbottomnav.value = 0
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Groups, contentDescription = "Members") },
                    label = { Text("Members") },
                    selected = selectedIndexforbottomnav.value== 1,
                    onClick = {
                        selectedIndexforbottomnav.value = 1
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Tasks") },
                    label = { Text("Tasks") },
                    selected = selectedIndexforbottomnav.value == 2,
                    onClick = {
                        selectedIndexforbottomnav.value = 2
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Home") },
                    label = { Text("About Us") },
                    selected = false,
                    onClick = {
                            showdialogAboutus=true
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.Copyright, contentDescription = "Home") },
                    label = { Text("Terms & Conditions") },
                    selected = false,
                    onClick = {
                        showdialogTermsandcondition = true
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Feedback, contentDescription = "Home") },
                    label = { Text("Feedback") },
                    selected = false,
                    onClick = {
                        val feedback = Intent(
                            Intent.ACTION_VIEW,
                            "https://docs.google.com/forms/d/e/1FAIpQLSftDN91vkdhso_oQV7uTmjvi50-bc_6_LRp8WVKyI4ANxIorQ/viewform?usp=dialog".toUri()
                        )
                        context.startActivity(feedback)
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Android, contentDescription = "Home") },
                    label = { Text("Report Bug") },
                    selected = false,
                    onClick = {
                        val bug = Intent(
                            Intent.ACTION_VIEW,
                            "https://github.com/Krrish-29/Devsource_App/issues/new".toUri()
                        )
                        context.startActivity(bug)
                    }
                )

                HorizontalDivider()
                Spacer(Modifier.height(8.dp))

                NavigationDrawerItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Sign Out") },
                    label = { Text("Sign Out") },
                    selected = false,
                    onClick = {
                        authViewModel.signOut(context, credentialManager)
                        navController.navigate("login")
                    }
                )
            }
            if (showdialogTermsandcondition) {
                AlertDialog(
                    onDismissRequest = {
                        showdialogTermsandcondition = false
                    },
                    title = { Text(text = "Terms & Conditions ") },
                    text = { Text(text = "We only collect the user name , email and phone number .\nThe data collected is not shared or distributed.") },
                    confirmButton = {
                        TextButton(onClick = {
                            showdialogTermsandcondition = false
                        }) {
                            Text(text = "Close")
                        }
                    }
                )
            }
            if (showdialogAboutus) {
                AlertDialog(
                    onDismissRequest = {
                        showdialogAboutus = false
                    },
                    title = { Text("About Us") },
                    text = {
                        Column {
                            Text(
                                text = "We are a team dedicated to providing the best services to our users.\n\nDevelopers:",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Anurag Kumar Singh ",
                                style = MaterialTheme.typography.titleLarge
                            )
                            ClickableLink(
                                label = "LinkedIn",
                                url = "https://www.linkedin.com/in/anurag-kumar-singh-56718427b/"
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ClickableLink(
                                label = "Github",
                                url = "https://github.com/shinobi04"
                            )
                            Text(
                                text = "Krrish Khowal",
                                style = MaterialTheme.typography.titleLarge
                            )
                            ClickableLink(
                                label = "LinkedIn",
                                url = "https://www.linkedin.com/in/krrish-khowal-150885311/"
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ClickableLink(
                                label = "Github",
                                url = "https://github.com/Krrish-29"
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { showdialogAboutus = false }) {
                            Text("Close")
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                Row1(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        scope.launch {
                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            expanded.value = false
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }

                    Text(
                        text = "DevSource",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(
                            onClick = { expanded.value = !expanded.value },
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val teamIcon = when {
                                selectedCategory.value.contains("Android", ignoreCase = true) -> Icons.Default.Android
                                selectedCategory.value.contains("Web", ignoreCase = true) -> Icons.Default.Language
                                selectedCategory.value.contains("Game", ignoreCase = true) -> Icons.Default.SportsEsports
                                else -> Icons.Default.Person
                            }

                            val iconTint = when {
                                selectedCategory.value.contains("Android", ignoreCase = true) -> Color(0xFF3DDC84)
                                selectedCategory.value.contains("Web", ignoreCase = true) -> Color(0xFF4285F4)
                                selectedCategory.value.contains("Game", ignoreCase = true) -> Color(0xFFE91E63)
                                else -> MaterialTheme.colorScheme.onSurface
                            }

                            Icon(
                                imageVector = teamIcon,
                                contentDescription = "Select team",
                                tint = iconTint,
                                modifier = Modifier.size(40.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false },
                            modifier = Modifier
                                .width(160.dp),
                            offset = DpOffset(x = 0.dp, y = 4.dp)
                        ) {
                            membersMap.value.keys.forEach { category ->
                                val itemIcon = when {
                                    category.contains("Android", ignoreCase = true) -> Icons.Default.Android
                                    category.contains("Web", ignoreCase = true) -> Icons.Default.Language
                                    category.contains("Game", ignoreCase = true) -> Icons.Default.SportsEsports
                                    else -> Icons.Default.Person
                                }

                                val itemIconTint = when {
                                    category.contains("Android", ignoreCase = true) -> Color(0xFF3DDC84)
                                    category.contains("Web", ignoreCase = true) -> Color(0xFF4285F4)
                                    category.contains("Game", ignoreCase = true) -> Color(0xFFE91E63)
                                    else -> MaterialTheme.colorScheme.onSurface
                                }

                                DropdownMenuItem(
                                    leadingIcon = {
                                        Icon(
                                            imageVector = itemIcon,
                                            contentDescription = null,
                                            tint = itemIconTint
                                        )
                                    },
                                    text = { Text(category) },
                                    onClick = {
                                        selectedCategory.value = category
                                        expanded.value = false
                                    }
                                )
                            }
                        }
                    }
                }
            },
            bottomBar = {
                Row1(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    bottomnavItemList.forEachIndexed { index, navItem ->
                        val isSelected = selectedIndexforbottomnav.value == index
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    color = if (isSelected) MaterialTheme.colorScheme.primary.copy(
                                        alpha = 0.8f
                                    ) else Color.Transparent,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { selectedIndexforbottomnav.value = index }
                                .padding(vertical = 4.dp)
                        ) {
                            BadgedBox(badge = {
                                if (navItem.badgeCount > 0)
                                    Badge {
                                        Text(text = navItem.badgeCount.toString())
                                    }
                            }) {
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = "Icon",
                                    tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                    modifier = if (isSelected) Modifier.size(25.dp) else Modifier.size(20.dp)
                                )
                            }
                            Text(
                                text = navItem.label,
                                fontSize = 12.sp,
                                fontWeight =  if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            })
        {innerpadding->
            ContentPages(modifier=Modifier.padding(innerpadding), selectedIndexforbottomnav, authViewModel, navController, selectedCategory, membersMap,aboutmap, tasksmap,totalTasks)
        }
    }
}

@Composable
fun ClickableLink(label: String, url: String) {
    val context = LocalContext.current
    Text(
        text = label,
        style = TextStyle(
            color = Color.Blue,
            textDecoration = TextDecoration.Underline,
            fontSize = 16.sp
        ),
        modifier = Modifier.clickable {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        }
    )
}

@Composable
fun ContentPages(modifier: Modifier=Modifier, selectedIndexforbottomnav: MutableState<Int>, authViewModel: AuthViewModel, navController: NavController, selectedCategory: MutableState<String>, membersMap: MutableState<Map<String, List<String>>>, aboutmap: MutableState<Map<String, List<String>>>, tasksmap: MutableState<Map<String, List<Pair<String, String>>>>, totalTasks:MutableState<Int>){
    when (selectedIndexforbottomnav.value){
        0 -> Home(modifier, authViewModel, navController)
        1 -> Members(modifier, selectedCategory, membersMap, aboutmap)
        2 -> Tasks(modifier, tasksmap, selectedCategory,totalTasks)
    }
}
data class BottomNavItem(
    val label:String,
    val icon: ImageVector,
    val badgeCount:Int
)
