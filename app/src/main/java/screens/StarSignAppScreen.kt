package com.example.starsign.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.starsign.navigation.Screen
import androidx.compose.foundation.layout.width
import androidx.compose.ui.unit.dp
import androidx.compose.animation.AnimatedVisibility

@Composable
fun StarSignApp(
    windowWidthSizeClass: WindowWidthSizeClass
) {
    val isDualPane =
        windowWidthSizeClass != WindowWidthSizeClass.Compact

    val isExpanded =
        windowWidthSizeClass == WindowWidthSizeClass.Expanded

    var selectedSignId by remember {
        mutableStateOf<Int?>(null)
    }

    var selectedNavigationIndex by remember {
        mutableIntStateOf(0)
    }

    val navController = rememberNavController()

    LaunchedEffect(isDualPane, selectedSignId) {
        if (!isDualPane && selectedSignId != null) {

            val currentRoute =
                navController.currentBackStackEntry
                    ?.destination
                    ?.route

            if (currentRoute == Screen.List.route) {
                navController.navigate(
                    Screen.Detail.createRoute(selectedSignId!!)
                )
            }
        }
    }

    fun selectNavigationItem(index: Int) {
        selectedNavigationIndex = index

        if (index == 1) {
            selectedSignId = null

            navController.popBackStack(
                Screen.List.route,
                false
            )
        }
    }

    Scaffold(
        topBar = {
            if (!isDualPane) {
                StarSignTopBar(
                    isCompact = true,
                    navController = navController,
                    onBackClick = {
                        selectedSignId = null
                        navController.popBackStack()
                    }
                )
            }
        },
        bottomBar = {
            if (!isDualPane) {
                StarSignNavigationBar(
                    selectedIndex = selectedNavigationIndex,
                    onItemSelected = { index ->
                        selectNavigationItem(index)
                    }
                )
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            if (selectedNavigationIndex == 1) {

                DailyHoroscopeScreen()

            } else if (isDualPane) {

                Row(
                    modifier = Modifier.fillMaxSize()
                ) {

                    if (isExpanded) {
                        StarSignNavigationRail(
                            selectedIndex = selectedNavigationIndex,
                            onItemSelected = { index ->
                                selectNavigationItem(index)
                            }
                        )
                    }

                    if (isExpanded) {

                        StarSignList(
                            selectedSignId = selectedSignId,
                            onSignClick = { sign ->
                                selectedSignId = sign.id
                            },
                            modifier = Modifier.width(300.dp)
                        )

                        AnimatedVisibility(
                            visible = selectedSignId != null,
                            modifier = Modifier.weight(1f)
                        ) {
                            StarSignDetail(
                                signId = selectedSignId
                            )
                        }

                    } else {

                        StarSignList(
                            selectedSignId = selectedSignId,
                            onSignClick = { sign ->
                                selectedSignId = sign.id
                            },
                            modifier = Modifier.weight(1f)
                        )

                        StarSignDetail(
                            signId = selectedSignId,
                            modifier = Modifier.weight(2f)
                        )
                    }
                }

            } else {

                NavHost(
                    navController = navController,
                    startDestination = Screen.List.route
                ) {
                    composable(
                        route = Screen.List.route
                    ) {
                        StarSignList(
                            selectedSignId = selectedSignId,
                            onSignClick = { sign ->
                                selectedSignId = sign.id

                                navController.navigate(
                                    Screen.Detail.createRoute(sign.id)
                                )
                            }
                        )
                    }

                    composable(
                        route = Screen.Detail.route,
                        arguments = listOf(
                            navArgument("signId") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->

                        val signId = backStackEntry
                            .arguments
                            ?.getInt("signId")

                        BackHandler {
                            selectedSignId = null
                            navController.popBackStack()
                        }

                        StarSignDetail(
                            signId = signId
                        )
                    }
                }
            }
        }
    }
}