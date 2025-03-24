package com.example.devsource.Homepage

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Divider
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Copyright
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Details
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.absoluteValue
import androidx.compose.foundation.layout.Row as Row1
import androidx.core.net.toUri
import com.example.devsource.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    selectedCategory: MutableState<String>,
    membersMap: MutableState<Map<String, List<String>>>,
    usernamefordisplay: MutableState<String>,
    useremailfordisplay: MutableState<String>,
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
        BottomNavItem("Task", Icons.Default.DateRange,totalTasks.value)
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
    val dialog=false
    var showDialog_aboutus by remember { mutableStateOf(false) }
    var showDialog_termsandcondition by remember { mutableStateOf(false) }
    val annotatedString = buildAnnotatedString {
        append("")
        pushStringAnnotation(
            tag = "URL",
            annotation = "https://www.example.com"
        )
        withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
            append("website")
        }
        pop()
        append(" for more information.")
    }
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
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$usernamefordisplay",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Divider()
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
                            showDialog_aboutus=true
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.Copyright, contentDescription = "Home") },
                    label = { Text("Terms & Conditions") },
                    selected = false,
                    onClick = {
                        showDialog_termsandcondition = true
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

                Divider()
                Spacer(Modifier.height(8.dp))

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.ExitToApp, contentDescription = "Sign Out") },
                    label = { Text("Sign Out") },
                    selected = false,
                    onClick = {
                        authViewModel.signOut(context)
                        navController.navigate("login")
                    }
                )
            }
            if (showDialog_termsandcondition && dialog != null) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog_termsandcondition = false
                    },
                    title = { Text(text = "Terms & Conditions ") },
                    text = { Text(text = "We only collect the user name , email and phone number .\nThe data collected is not shared or distributed.") },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog_termsandcondition = false
                        }) {
                            Text(text = "Close")
                        }
                    }
                )
            }
            if (showDialog_aboutus && dialog != null) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog_aboutus = false
                    }, // Close the dialog on dismissal
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
                        TextButton(onClick = { showDialog_aboutus = false }) {
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
@Composable
fun Home(modifier: Modifier = Modifier, authViewModel: AuthViewModel, navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = 0.8f,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "About DevSource Club",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Welcome to DevSource Club, a thriving community of developers under the USIC&T ACM Student Chapter. Our mission is to skill up our members and provide them with exposure to diverse development domains, including web development, game development, app development, and open-source collaboration.",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row1(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DomainBadge(
                        icon = Icons.Default.Android,
                        label = "Android",
                        color = Color(0xFF3DDC84)
                    )

                    DomainBadge(
                        icon = Icons.Default.Language,
                        label = "Web",
                        color = Color(0xFF4285F4)
                    )

                    DomainBadge(
                        icon = Icons.Default.SportsEsports,
                        label = "Game",
                        color = Color(0xFFE91E63)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))


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
        Row1(
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
@Composable
fun Tasks(modifier: Modifier = Modifier, tasksmap: MutableState<Map<String, List<Pair<String, String>>>>, selectedCategory: MutableState<String>, totalTasks: MutableState<Int>) {
    totalTasks.value = 0
    var expandedTaskId by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tasks",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val tasksList = tasksmap.value[selectedCategory.value] ?: emptyList()

            if (tasksList.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No tasks available for ${selectedCategory.value}",
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            itemsIndexed(tasksList) { index, (taskName, taskDescription) ->
                val isExpanded = expandedTaskId == taskName
                val taskNumber = index + 1

                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = 0.8f,
                                stiffness = Spring.StiffnessMedium
                            )
                        )
                        .clickable {
                            expandedTaskId = if (isExpanded) null else taskName
                        },
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row1(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val iconTint = when {
                                selectedCategory.value.contains("Android", ignoreCase = true) -> Color(0xFF3DDC84)
                                selectedCategory.value.contains("Web", ignoreCase = true) -> Color(0xFF4285F4)
                                selectedCategory.value.contains("Game", ignoreCase = true) -> Color(0xFFE91E63)
                                else -> MaterialTheme.colorScheme.primary
                            }

                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(
                                        color = iconTint.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = taskNumber.toString(),
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    ),
                                    color = iconTint
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = taskName,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                if (!isExpanded) {
                                    Spacer(modifier = Modifier.height(4.dp))

                                    val truncatedDescription = if (taskDescription.length > 80) {
                                        "${taskDescription.take(80)}..."
                                    } else {
                                        taskDescription
                                    }

                                    Text(
                                        text = truncatedDescription,
                                        style = TextStyle(
                                            fontSize = 14.sp
                                        ),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 2
                                    )
                                }
                            }

                            Icon(
                                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = if (isExpanded) "Collapse" else "Expand",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        if (isExpanded) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Divider()
                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = taskDescription,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
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