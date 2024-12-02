package com.mobdeve.s19.group7.mco

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesTab(navController: NavHostController, profiles: List<Profile>) {
    val matchedProfiles = profiles.filter { it.likedByUser && it.likesUser }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Messages") },
                actions = {
                    Button(
                        onClick = { navController.navigate("swipe_screen") }
                    ) {
                        Text("Home")
                    }
                }
            )
        },
        content = { padding ->
            if (matchedProfiles.isEmpty()) {
                // No Matched Profiles
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No matched profiles yet!",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Gray
                    )
                }
            } else {
                // Display Matched Profiles
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(matchedProfiles) { profile ->
                        MatchedProfileCard(profile = profile, navController = navController)
                    }
                }
            }
        }
    )
}

@Composable
fun MatchedProfileCard(profile: Profile, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable {
                navController.navigate("message_screen/${profile.name}")
            },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Image
            Image(
                painter = painterResource(profile.pictureResId),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Profile Name
            Text(
                text = profile.name,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

