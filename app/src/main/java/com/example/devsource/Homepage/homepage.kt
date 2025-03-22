package com.example.devsource.Homepage

import android.view.Menu
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import kotlinx.coroutines.CoroutineScope
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Row as Row1


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(modifier: Modifier=Modifier, navController: NavController, authViewModel: AuthViewModel, selectedCategory: MutableState<String>,membersMap: MutableState<Map<String, List<String>>>, usernamefordisplay:MutableState<String>,useremailfordisplay:MutableState<String>, aboutmap: MutableState<Map<String, List<String>>>) {
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }
    val bottomnavItemList=listOf(
        BottomNavItem("Home", Icons.Default.Home,0),
        BottomNavItem("Members", Icons.Default.Groups,0),
        BottomNavItem("Task", Icons.Default.DateRange,5)
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
    val focusRequester = remember { FocusRequester() }
    val isFocused = remember { mutableStateOf(false) }
    val expanded = remember { mutableStateOf(false) }
    LaunchedEffect(!isFocused.value) {
        if (!isFocused.value) {
            focusRequester.requestFocus()
            isFocused.value = false
        }
    }
    val scope = rememberCoroutineScope()
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
                    horizontalArrangement = Arrangement.SpaceEvenly,
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
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "DevSource",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ExposedDropdownMenuBox(
                        expanded = expanded.value,
                        onExpandedChange = { expanded.value = !expanded.value }
                    ) {
                        OutlinedTextField(
                            value = selectedCategory.value,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Select Team") },
                            trailingIcon = {
                                Icon(
                                    imageVector = if (expanded.value) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .width(160.dp)
                                .menuAnchor()
                                .focusRequester(focusRequester)
                        )

                        ExposedDropdownMenu(
                            shape = RoundedCornerShape(16.dp),
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false },
                            modifier = Modifier.width(160.dp)
                        ) {
                            membersMap.value.keys.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(category) },
                                    onClick = {
                                        selectedCategory.value=category
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
            ContentPages(modifier=Modifier.padding(innerpadding), selectedIndexforbottomnav, authViewModel, navController, selectedCategory, membersMap,aboutmap)
        }
    }
}
@Composable
fun ContentPages(modifier: Modifier=Modifier, selectedIndexforbottomnav:Int, authViewModel: AuthViewModel, navController: NavController, selectedCategory: MutableState<String>, membersMap: MutableState<Map<String, List<String>>>, aboutmap: MutableState<Map<String, List<String>>>){
    when (selectedIndexforbottomnav){
        0 -> Home(modifier, authViewModel, navController)
        1 -> Members(modifier, authViewModel, navController, selectedCategory, membersMap,aboutmap)
        2 -> Tasks(modifier, authViewModel, navController)
    }
}
@Composable
fun Home(modifier: Modifier=Modifier,authViewModel: AuthViewModel,navController: NavController){
    val context = LocalContext.current
    Column(
        modifier=modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text="Home")
        TextButton(onClick={
            authViewModel.signOut(context)
            navController.navigate("login")
        }){
            Text(text="Sign Out")
        }
    }
}

@Composable
fun Members(modifier: Modifier = Modifier, authViewModel: AuthViewModel, navController: NavController, selectedCategory: MutableState<String>, membersMap: MutableState<Map<String, List<String>>>, aboutmap: MutableState<Map<String, List<String>>>) {
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
                            color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.Transparent,
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
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
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
        val iconTint = when {
            selectedCategory.value.contains("Android", ignoreCase = true) -> Color(0xFF3DDC84)
            selectedCategory.value.contains("Web", ignoreCase = true) -> Color(0xFF4285F4)
            selectedCategory.value.contains("Game", ignoreCase = true) -> Color(0xFFE91E63)
            else -> MaterialTheme.colorScheme.onSurface
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val about=aboutmap.value[selectedCategory.value] ?: emptyList()
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row1(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "$about",
                            style = TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
            val members = membersMap.value[selectedCategory.value] ?: emptyList()
            items(members) { member ->
                Card(
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Row1(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Icon(
                                imageVector = teamIcon,
                                contentDescription = "Team member icon",
                                tint = iconTint,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
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
            }
        }
    }
}
@Composable
fun Tasks(modifier: Modifier=Modifier,authViewModel: AuthViewModel, navController : NavController){
    val context = LocalContext.current
    Column(
        modifier=modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text="Tasks")
        TextButton(onClick={
            authViewModel.signOut(context)
            navController.navigate("login")
        }){
            Text(text="Sign Out")   
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