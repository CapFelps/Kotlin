package br.com.fiap.softekfiap.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.fiap.softekfiap.ui.screens.LoginScreen
import br.com.fiap.softekfiap.ui.screens.CheckinScreen
import br.com.fiap.softekfiap.ui.screens.HistoryScreen
import br.com.fiap.softekfiap.ui.screens.HomeScreen
import br.com.fiap.softekfiap.ui.screens.QuestionnaireScreen
import br.com.fiap.softekfiap.ui.screens.RegisterScreen
import br.com.fiap.softekfiap.util.SessionManager

@Composable
fun AppNavGraph(navController: NavHostController) {

    val context = LocalContext.current
    val userId = SessionManager.getIdUsuario(context)

    LaunchedEffect(Unit) {
        if (userId != null) {
            navController.navigate("checkin?userId=$userId") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("checkin?userId={userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
            CheckinScreen(navController, userId)
        }
        composable("home?userId={userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
            HomeScreen(navController, userId)
        }
        composable("questionnaire") { QuestionnaireScreen(navController) }
        composable("historico") { HistoryScreen(navController) }
    }
}
