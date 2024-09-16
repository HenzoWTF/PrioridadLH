package edu.ucne.prioridadlh

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: MainActivity.Route,
    val badgeCount: Int? = null
)
