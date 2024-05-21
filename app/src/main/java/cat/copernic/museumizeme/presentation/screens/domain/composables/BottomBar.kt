package cat.copernic.museumizeme.presentation.screens.domain.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import cat.copernic.museumizeme.presentation.navigation.model.BottomNavItem

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.MuseumList,
        BottomNavItem.Map
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            if (currentDestination != null) {
                val selected = currentDestination == item.route
                NavigationBarItem(
                    selected = false,
                    label = {
                        Text(
                            text = item.title,
                            color = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    },
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = "Icons",
                                tint = if (selected) MaterialTheme.colorScheme.primary else Color.Gray,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                    },
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.id)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

