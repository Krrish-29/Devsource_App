package com.example.devsource.Homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController


@Composable
fun Horizontalpage(modifier: Modifier=Modifier,authViewModel: AuthViewModel,navController: NavController){
    var username="krrish"
    Box(
        modifier=Modifier
            .zIndex(1f)
            .fillMaxWidth(0.80f)
            .fillMaxHeight()
            .background(Color.Gray)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp),
        ){
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = "User",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = username, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 10.dp,
                color = Color.Gray
            )

//            Text(text="Share ContactUs Feedback TermsandConditions About Logout")


//            Button(contentPadding = PaddingValues(horizontal = 20.dp, vertical = 17.dp),
//                onClick = {
//                    when (authState.value) {
//                        is AuthState.Authenticated -> navController.navigate("fetchdata")
//                        is AuthState.Unauthenticated -> navController.navigate("login")
//                        else -> Unit
//                    }
//                },
//            ) {
//                Text(text = "Begin", fontSize = 22.sp, fontWeight = FontWeight.Bold)
//                Spacer(modifier = Modifier.width(8.dp))
//                Icon(
//                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
//                    contentDescription = "Favorite",
//                )
            }
        }
}