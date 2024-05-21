package cat.copernic.museumizeme.presentation.navigation

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.copernic.museumizeme.authentication.AuthService
import cat.copernic.museumizeme.presentation.navigation.model.Routes
import cat.copernic.museumizeme.presentation.screens.MainScreen
import cat.copernic.museumizeme.presentation.screens.authorization.LoginScreen
import cat.copernic.museumizeme.presentation.screens.authorization.ResetPasswordScreen
import cat.copernic.museumizeme.presentation.screens.authorization.SignUpScreen
import com.google.firebase.auth.FirebaseUser

@ExperimentalMaterial3Api
@Composable
fun Navigation(context: Context, navController: NavHostController = rememberNavController()) {
    val authService: AuthService = AuthService(context)

    val user: FirebaseUser? = authService.getCurrentUser()

    NavHost(
        navController = navController,
        startDestination = if (user == null) Routes.Login.route else Routes.Main.route
    ) {
        composable(Routes.Login.route) {
            LoginScreen(
                auth = authService,
                navigation = navController
            )

        }
        composable(Routes.Main.route) {
            MainScreen(
                auth = authService,
                navigation = navController
            )
        }
        composable(Routes.SignUp.route) {
            SignUpScreen(
                auth = authService,
                navigation = navController
            )
        }
        composable(Routes.ForgotPassword.route) {
            ResetPasswordScreen(
                auth = authService,
                navigation = navController
            )
        }
    }
}
