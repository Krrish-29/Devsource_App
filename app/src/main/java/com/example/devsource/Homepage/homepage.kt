package com.example.devsource.Homepage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
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
            Row(
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
                                        Badge(){
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
                                        Badge(){
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
            Row(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .background(Color.DarkGray),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                bottomnavItemList.forEachIndexed{ index,navItem->
                    NavigationBarItem(
                        selected = selectedIndexforbottomnav==index,
                        onClick={
                            selectedIndexforbottomnav=index
                        },
                        icon={
                            BadgedBox(badge={
                                if(navItem.badgeCount>0)
                                    Badge(){
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
@Composable
fun Members(modifier: Modifier=Modifier,authViewModel: AuthViewModel, navController : NavController){
    val context = LocalContext.current
    Column(
        modifier=modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text="Members")
        TextButton(onClick={
            authViewModel.signOut(context)
            navController.navigate("login")
        }){
            Text(text="Sign Out")
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
)data class TopNavItem(
    val label:String,
    val icon: ImageVector,
    val badgeCount:Int
)
