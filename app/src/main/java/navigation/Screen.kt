package com.example.starsign.navigation

sealed class Screen(val route: String) {

    data object List : Screen("star_sign_list")

    data object Detail : Screen("star_sign_detail/{signId}") {
        fun createRoute(signId: Int): String {
            return "star_sign_detail/$signId"
        }
    }
}