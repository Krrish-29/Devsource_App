package com.example.devsource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.devsource.Homepage.AuthViewModel
import com.example.devsource.Homepage.LoginPage
import com.example.devsource.Homepage.SignUpPage
import com.example.devsource.Homepage.HomePage
import com.example.devsource.Homepage.WelcomePage

@Composable
fun Navigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome"){
            WelcomePage(modifier, navController)
        }
        composable("login") {
            LoginPage(modifier, navController, authViewModel)
        }
        composable("signup") {
            SignUpPage(modifier, navController, authViewModel)
        }
        composable("home") {
            HomePage(modifier, navController, authViewModel)
        }

    }
}