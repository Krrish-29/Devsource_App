package com.example.devsource.App

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.outlined.Copyright
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Row as Row1
import androidx.core.net.toUri
import com.example.devsource.Homepage.AuthState
import com.example.devsource.Homepage.AuthViewModel


@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    selectedCategory: MutableState<String>,
    membersMap: MutableState<Map<String, List<String>>>,
    aboutmap: MutableState<Map<String, List<String>>>,
    tasksmap: MutableState<Map<String, List<Pair<String, String>>>>
) {
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }
    val totalTasks = remember {  mutableIntStateOf(tasksmap.value.values.sumOf { it.size }) }
    val bottomnavItemList=listOf(
        BottomNavItem("Home", Icons.Default.Home,0),
        BottomNavItem("Members", Icons.Default.Groups,0),
        BottomNavItem("Task", Icons.Default.DateRange,totalTasks.intValue)
    )
    val context = LocalContext.current
    val topNavItemList=listOf(
        TopNavItem("Menu", Icons.Filled.Menu),
        TopNavItem("More", Icons.Filled.MoreVert),
    )
    var selectedIndexforbottomnav by remember {
        mutableIntStateOf(0)
    }
    var selectedIndexfortopnav by remember {
        mutableIntStateOf(3)
    }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val expanded = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var showdialogAboutus by remember { mutableStateOf(false) }
    var showdialogTermsandcondition by remember { mutableStateOf(false)}
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp),
                drawerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
            ) {
                Spacer(Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    Text(
                        text = "usernamefordisplay.value",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "useremailfordisplay.value",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                HorizontalDivider()
                Spacer(Modifier.height(8.dp))

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedIndexforbottomnav == 0,
                    onClick = {
                        selectedIndexforbottomnav = 0
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Groups, contentDescription = "Members") },
                    label = { Text("Members") },
                    selected = selectedIndexforbottomnav == 1,
                    onClick = {
                        selectedIndexforbottomnav = 1
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Tasks") },
                    label = { Text("Tasks") },
                    selected = selectedIndexforbottomnav == 2,
                    onClick = {
                        selectedIndexforbottomnav = 2
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
                        authViewModel.signOut(context)
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
                        val isSelected = selectedIndexforbottomnav == index
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
                                .clickable { selectedIndexforbottomnav = index }
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
fun ContentPages(modifier: Modifier=Modifier, selectedIndexforbottomnav:Int, authViewModel: AuthViewModel, navController: NavController, selectedCategory: MutableState<String>, membersMap: MutableState<Map<String, List<String>>>, aboutmap: MutableState<Map<String, List<String>>>,tasksmap: MutableState<Map<String, List<Pair<String, String>>>>,totalTasks:MutableState<Int>){
    when (selectedIndexforbottomnav){
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
data class TopNavItem(
    val label:String,
    val icon: ImageVector
)