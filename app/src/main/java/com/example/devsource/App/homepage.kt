package com.example.devsource.App

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
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
                modifier = Modifier
                    .width(300.dp)
                    .background(
                        color = Color(0xFF121212)
                    ),
                drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                drawerContainerColor = Color.Transparent
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color(0xFF1A1A1A))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(bottom = 15.dp),
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
                                            width = 2.dp,
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = CircleShape
                                        )
                                        .background(Color(0xFF1A1A1A), CircleShape)
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(90.dp)
                                        .clip(CircleShape)
                                        .border(
                                            width = 2.dp,
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = CircleShape
                                        )
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

                        Text(
                            text = username.value,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = auth.currentUser?.email ?: useremail.value,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "NAVIGATION",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                    )

                    NavigationItem(
                        icon = Icons.Default.Home,
                        label = "Home",
                        selected = selectedIndexforbottomnav.value == 0,
                        onClick = {
                            selectedIndexforbottomnav.value = 0
                            scope.launch { drawerState.close() }
                        }
                    )

                    NavigationItem(
                        icon = Icons.Default.Groups,
                        label = "Members",
                        selected = selectedIndexforbottomnav.value == 1,
                        onClick = {
                            selectedIndexforbottomnav.value = 1
                            scope.launch { drawerState.close() }
                        }
                    )

                    NavigationItem(
                        icon = Icons.Default.DateRange,
                        label = "Tasks",
                        selected = selectedIndexforbottomnav.value == 2,
                        badgeCount = totalTasks.intValue,
                        onClick = {
                            selectedIndexforbottomnav.value = 2
                            scope.launch { drawerState.close() }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "ABOUT & HELP",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp, top = 8.dp)
                    )

                    NavigationItem(
                        icon = Icons.Default.Person,
                        label = "About Us",
                        selected = false,
                        onClick = { showdialogAboutus = true }
                    )

                    NavigationItem(
                        icon = Icons.Outlined.Copyright,
                        label = "Terms & Conditions",
                        selected = false,
                        onClick = { showdialogTermsandcondition = true }
                    )

                    NavigationItem(
                        icon = Icons.Default.Feedback,
                        label = "Feedback",
                        selected = false,
                        onClick = {
                            val feedback = Intent(
                                Intent.ACTION_VIEW,
                                "https://docs.google.com/forms/d/e/1FAIpQLSftDN91vkdhso_oQV7uTmjvi50-bc_6_LRp8WVKyI4ANxIorQ/viewform?usp=dialog".toUri()
                            )
                            context.startActivity(feedback)
                        }
                    )

                    NavigationItem(
                        icon = Icons.Default.Android,
                        label = "Report Bug",
                        selected = false,
                        onClick = {
                            val bug = Intent(
                                Intent.ACTION_VIEW,
                                "https://github.com/Krrish-29/Devsource_App/issues/new".toUri()
                            )
                            context.startActivity(bug)
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.White.copy(alpha = 0.1f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    NavigationItem(
                        icon = Icons.AutoMirrored.Filled.ExitToApp,
                        label = "Sign Out",
                        selected = false,
                        isSignOut = true,
                        onClick = {
                            authViewModel.signOut(context, credentialManager)
                            navController.navigate("login")
                        }
                    )
                }
            }

            if (showdialogTermsandcondition) {
                AlertDialog(
                    onDismissRequest = { showdialogTermsandcondition = false },
                    title = { Text(text = "Terms & Conditions") },
                    text = { Text(text = "We only collect the user name, email and phone number.\nThe data collected is not shared or distributed.") },
                    confirmButton = {
                        TextButton(onClick = { showdialogTermsandcondition = false }) {
                            Text(text = "Close")
                        }
                    }
                )
            }

            if (showdialogAboutus) {
                AlertDialog(
                    onDismissRequest = { showdialogAboutus = false },
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
    ){
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
private fun NavigationItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    badgeCount: Int = 0,
    isSignOut: Boolean = false,
    onClick: () -> Unit
) {
    val background = if (selected) {
        Brush.horizontalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            )
        )
    } else if (isSignOut) {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFB71C1C).copy(alpha = 0.2f),
                Color.Transparent
            )
        )
    } else {
        Brush.horizontalGradient(
            colors = listOf(
                Color.Transparent,
                Color.Transparent
            )
        )
    }

    val textColor = when {
        selected -> MaterialTheme.colorScheme.primary
        isSignOut -> Color(0xFFEF5350)
        else -> Color.White.copy(alpha = 0.8f)
    }

    val iconTint = when {
        selected -> MaterialTheme.colorScheme.primary
        isSignOut -> Color(0xFFEF5350)
        else -> Color.White.copy(alpha = 0.6f)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(background)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
            modifier = Modifier.weight(1f)
        )

        if (badgeCount > 0) {
            Badge(
                containerColor = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text(
                    text = badgeCount.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

        if (selected) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(24.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(2.dp)
                    )
            )
        }
    }
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
