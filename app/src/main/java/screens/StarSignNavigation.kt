package com.example.starsign.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem



data class NavigationItem(
    val label: String,
    val icon: ImageVector
)

val navigationItems = listOf(
    NavigationItem(
        label = "Star Signs",
        icon = Icons.Default.Star
    ),
    NavigationItem(
        label = "Daily Horoscope",
        icon = Icons.Default.CalendarMonth
    )
)

@Composable
fun StarSignNavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    onItemSelected(index)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(item.label)
                }
            )
        }
    }
}

@Composable
fun StarSignNavigationRail(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationRail {
        navigationItems.forEachIndexed { index, item ->
            NavigationRailItem(
                selected = selectedIndex == index,
                onClick = {
                    onItemSelected(index)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(item.label)
                }
            )
        }
    }
}