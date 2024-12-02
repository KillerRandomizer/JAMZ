package com.mobdeve.s19.group7.mco

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Check if there are matched profiles
    val hasMatchedProfiles = ProfileData.profiles.any { it.likedByUser && it.likesUser }

    // State for managing the current index across navigation
    var currentIndex by rememberSaveable { mutableStateOf(0) }

    // State for the user's profile with ProfileSaver
    var userProfile by rememberSaveable(stateSaver = ProfileSaver) {
        mutableStateOf(Profile(name = "Your Name", pictureResId = R.drawable.your_picture))
    }

    Scaffold(
        bottomBar = {
            // Only show the BottomNavigationBar for logged-in screens
            if (currentRoute !in listOf("login_screen", "signup_screen")) {
                BottomNavigationBar(
                    currentRoute = currentRoute,
                    navController = navController,
                    hasMatchedProfiles = hasMatchedProfiles
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login_screen", // Start with the login screen
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login_screen") {
                LoginScreen(navController = navController)
            }
            composable("signup_screen") {
                SignupScreen(navController = navController)
            }
            composable("profile_screen") {
                ProfileScreen(
                    navController = navController,
                    userProfile = userProfile,
                    onProfileUpdate = { updatedProfile -> userProfile = updatedProfile },
                    playlists = userProfile.playlists // Pass playlists here
                )
            }
            composable("swipe_screen") {
                SwipeScreen(
                    navController = navController,
                    profiles = ProfileData.profiles,
                    currentIndex = currentIndex,
                    onIndexChange = { currentIndex = it },
                    userProfile = userProfile
                )
            }
            composable("messages_screen") {
                MessagesTab(navController = navController, profiles = ProfileData.profiles)
            }
            composable("edit_profile") {
                EditProfileScreen(
                    navController = navController,
                    userProfile = userProfile,
                    onProfileUpdate = { updatedProfile -> userProfile = updatedProfile }
                )
            }
            composable("edit_playlist") {
                EditPlaylistScreen(
                    navController = navController,
                    userPlaylist = userProfile.playlists.toMutableList(),
                    allSongs = SongDatabase.allSongs, // Pass the global song database
                    onPlaylistUpdate = { updatedPlaylist ->
                        userProfile = userProfile.copy(playlists = updatedPlaylist)
                    }
                )
            }
            composable("message_screen/{profileName}") { backStackEntry ->
                val profileName = backStackEntry.arguments?.getString("profileName")
                val profile = ProfileData.profiles.find { it.name == profileName }
                if (profile != null) {
                    MessageScreen(profile = profile, navController = navController)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    navController: NavHostController,
    hasMatchedProfiles: Boolean
) {
    NavigationBar {
        // Profile Tab
        NavigationBarItem(
            selected = currentRoute == "profile_screen",
            onClick = { navController.navigate("profile_screen") },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") }
        )

        // Messages Tab
        NavigationBarItem(
            selected = currentRoute == "messages_screen",
            onClick = { navController.navigate("messages_screen") },
            icon = {
                val iconResource = if (hasMatchedProfiles) {
                    R.drawable.message2 // Matched profiles icon
                } else {
                    R.drawable.message1 // Default messages icon
                }
                Image(
                    painter = painterResource(id = iconResource),
                    contentDescription = "Messages Icon",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("Messages") }
        )
    }
}

fun getMatchedProfiles(): List<Profile> {
    return ProfileData.profiles.filter { profile -> profile.messages.isNotEmpty() }
}
