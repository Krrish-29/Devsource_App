package com.example.devsource.Homepage

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import androidx.compose.foundation.layout.Row as Row1


@Composable
fun HomePage(modifier: Modifier=Modifier, navController: NavController, authViewModel: AuthViewModel) {
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }
    val bottomnavItemList=listOf(
        BottomNavItem("Home", Icons.Default.Home,0),
        BottomNavItem("Members", Icons.Default.Person,0),
        BottomNavItem("Task", Icons.Default.DateRange,5)
    )
    val topNavItemList=listOf(
        TopNavItem("Menu", Icons.Filled.Menu,0),
        TopNavItem("More", Icons.Filled.MoreVert,0),
    )
    var selectedIndexforbottomnav by remember {
        mutableIntStateOf(0)
    }
    var selectedIndexfortopnav by remember {
        mutableIntStateOf(0)
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Row1(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(60.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                topNavItemList.forEachIndexed{ index,navItem->
                    if(index==0){
                        NavigationBarItem(
                            selected = selectedIndexfortopnav==index,
                            onClick={
                                selectedIndexfortopnav=index
                            },
                            icon={
                                BadgedBox(badge={
                                    if(navItem.badgeCount>0)
                                        Badge{
                                            Text(text=navItem.badgeCount.toString())
                                        }
                                }) { Icon(imageVector=navItem.icon, contentDescription = "Icon")
                                }
                            },
                            label={
                                Text(text=navItem.label)
                            }
                        )
                    }
                    else{
                        Spacer(modifier=Modifier.weight(1f))
                        NavigationBarItem(
                            selected = selectedIndexfortopnav==index,
                            onClick={
                                selectedIndexfortopnav=index
                            },
                            icon={
                                BadgedBox(badge={
                                    if(navItem.badgeCount>0)
                                        Badge{
                                            Text(text=navItem.badgeCount.toString())
                                        }
                                }) { Icon(imageVector=navItem.icon, contentDescription = "Icon")
                                }
                            },
                            label={
                                Text(text=navItem.label)
                            }
                        )
                    }
                }
                    }

        },
        bottomBar = {
            val bottomBarColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray

            Row1(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .background(bottomBarColor),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                bottomnavItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndexforbottomnav == index,
                        onClick = {
                            selectedIndexforbottomnav = index
                        },
                        icon = {
                            BadgedBox(badge = {
                                if (navItem.badgeCount > 0)
                                    Badge {
                                        Text(text = navItem.badgeCount.toString())
                                    }
                            }) {
                                Icon(imageVector = navItem.icon, contentDescription = "Icon")
                            }
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }) {innerpadding->
        ContentPages(modifier=Modifier.padding(innerpadding),selectedIndexforbottomnav,authViewModel,navController)
    }
}
@Composable
fun ContentPages(modifier: Modifier=Modifier,selectedindex:Int,authViewModel: AuthViewModel,navController: NavController){
    val context = LocalContext.current
    when (selectedindex){
        0->Home(modifier,authViewModel, navController)
        1->Members(modifier,authViewModel, navController)
        2->Tasks(modifier,authViewModel, navController)
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Members(modifier: Modifier = Modifier,authViewModel: AuthViewModel,navController: NavController) {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Members")
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val isFocused = remember { mutableStateOf(false) }
    val selectedCategory = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }
    val membersMap = remember { mutableStateOf(mapOf<String, List<String>>()) }
    DisposableEffect(Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categorizedMembers = mutableMapOf<String, MutableList<String>>()
                for (categorySnapshot in snapshot.children) {
                    val category = categorySnapshot.key.orEmpty()
                    val members = mutableListOf<String>()
                    for (memberSnapshot in categorySnapshot.children) {
                        val member = memberSnapshot.key.orEmpty()
                        members.add(member)
                    }
                    categorizedMembers[category] = members
                }
                membersMap.value = categorizedMembers

                if (categorizedMembers.isNotEmpty()) {
                    selectedCategory.value = categorizedMembers.keys.first()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Failed to read data: ${error.message}")
            }
        }
        myRef.addValueEventListener(valueEventListener)

        onDispose {
            myRef.removeEventListener(valueEventListener)
        }
    }
    LaunchedEffect(!isFocused.value) {
        if (!isFocused.value) {
            focusRequester.requestFocus()
            isFocused.value = false
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Members", style = MaterialTheme.typography.headlineMedium)
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
                modifier = Modifier.menuAnchor().focusRequester(focusRequester)
            )

            ExposedDropdownMenu(
                shape = RoundedCornerShape(16.dp),
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                membersMap.value.keys.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedCategory.value = category
                            expanded.value = false
                        }
                    )
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
            val members = membersMap.value[selectedCategory.value] ?: emptyList()
            items(members) { member ->
                Card(
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row1(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = teamIcon,
                                contentDescription = "Team member icon",
                                tint = iconTint,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = member,
                                style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp),
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
    val icon: ImageVector,
    val badgeCount:Int
)