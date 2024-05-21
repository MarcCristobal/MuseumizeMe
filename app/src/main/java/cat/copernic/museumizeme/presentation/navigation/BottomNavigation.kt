package cat.copernic.museumizeme.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cat.copernic.museumizeme.authentication.AuthService
import cat.copernic.museumizeme.dao.MuseumDaoImpl
import cat.copernic.museumizeme.dao.UserDaoImpl
import cat.copernic.museumizeme.presentation.navigation.model.BottomNavItem
import cat.copernic.museumizeme.presentation.screens.domain.MapScreen
import cat.copernic.museumizeme.presentation.screens.domain.MuseumScreen

@Composable
fun BottomNavigation(navController: NavHostController, context: Context, authService: AuthService) {
    val museumDao = MuseumDaoImpl(context)

    val userDao = UserDaoImpl()

    val currentUser = authService.getCurrentUser()

    NavHost(navController = navController, startDestination = BottomNavItem.MuseumList.route) {
        composable(route = BottomNavItem.MuseumList.route) {
            MuseumScreen(museumDao = museumDao, currentUser = currentUser, userDao = userDao)
        }
        composable(route = BottomNavItem.Map.route) {
            MapScreen(museumDao = museumDao)
        }
    }
}

