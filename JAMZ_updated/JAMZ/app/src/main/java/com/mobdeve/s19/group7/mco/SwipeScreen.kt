package com.mobdeve.s19.group7.mco

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun SwipeScreen(
    navController: NavHostController,
    profiles: List<Profile>,
    currentIndex: Int,
    onIndexChange: (Int) -> Unit,
    userProfile: Profile
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var isFlipped by remember { mutableStateOf(false) }

    // Function to proceed to the next profile safely
    suspend fun proceedToNextProfileWithAnimation(likedByUser: Boolean) {
        if (currentIndex >= profiles.size) return
        val profile = profiles[currentIndex]
        profile.likedByUser = likedByUser

        if (likedByUser && profile.likesUser) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("You are matched with ${profile.name}!")
            }
        }

        isFlipped = false // Reset flip state
        onIndexChange(currentIndex + 1)
    }

    if (currentIndex < profiles.size) {
        val profile = profiles[currentIndex]

        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.padding(bottom = 80.dp) // Adjust notification above buttons
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Profile Card with Flip Effect
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    FlipCard(
                        isFlipped = isFlipped,
                        onFlip = { isFlipped = !isFlipped },
                        profile = profile
                    )
                }

                // Like and Dislike Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dislike),
                        contentDescription = "Dislike",
                        modifier = Modifier
                            .size(80.dp)
                            .clickable {
                                coroutineScope.launch {
                                    proceedToNextProfileWithAnimation(false)
                                }
                            }
                    )

                    Image(
                        painter = painterResource(id = R.drawable.like),
                        contentDescription = "Like",
                        modifier = Modifier
                            .size(80.dp)
                            .clickable {
                                coroutineScope.launch {
                                    proceedToNextProfileWithAnimation(true)
                                }
                            }
                    )
                }
            }
        }
    } else {
        // End of Profiles
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No more profiles!",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun FlipCard(
    isFlipped: Boolean,
    onFlip: () -> Unit,
    profile: Profile
) {
    val rotationY by animateFloatAsState(targetValue = if (isFlipped) 180f else 0f)

    Box(
        modifier = Modifier
            .size(350.dp, 550.dp)
            .graphicsLayer(
                rotationY = rotationY,
                cameraDistance = 12f * LocalDensity.current.density
            )
            .clickable { onFlip() },
        contentAlignment = Alignment.Center
    ) {
        if (rotationY < 90f || rotationY > 270f) {
            ProfileFrontCard(profile = profile, onFlip = onFlip)
        } else {
            ProfileBackCard(profile = profile, isFlipped = rotationY > 90f)
        }
    }
}

// Define random bios
val randomBios = listOf(
    "🎶 Music fuels my soul!",
    "🎤 Singing my way through life.",
    "🎵 Always in tune with good vibes.",
    "🎧 Headphones on, world off.",
    "📀 Collector of timeless melodies."
)

@Composable
fun ProfileFrontCard(profile: Profile, onFlip: () -> Unit) {
    val bio = remember { randomBios.random() } // Pick a random bio for each profile

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Profile Picture
            Image(
                painter = painterResource(id = profile.pictureResId),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(250.dp) // Larger profile picture
                    .padding(16.dp),
                alignment = Alignment.Center
            )

            // Profile Name
            Text(
                text = profile.name,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = FontFamily.SansSerif, // Change font to Sans Serif for a clean look
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5CE1E6)
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Random Bio
            Text(
                text = bio,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = FontFamily.Serif, // Add a touch of elegance with Serif font
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // View Playlist Button
            Button(
                onClick = onFlip,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5CE1E6)),
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Text(
                    text = "View Playlist",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }
    }
}



@Composable
fun ProfileBackCard(profile: Profile, isFlipped: Boolean) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFEFEFEF), // Softer background color
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .graphicsLayer(
                    rotationY = if (isFlipped) 180f else 0f
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Playlist Title Section
            Text(
                text = "Playlist for ${profile.name}",
                style = MaterialTheme.typography.headlineMedium.copy(color = Color(0xFF5CE1E6)),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Divider(color = Color(0xFF5CE1E6), thickness = 2.dp) // Divider below the title

            Spacer(modifier = Modifier.height(8.dp))

            // Playlist Content
            if (profile.playlists.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp) // Spacing between items
                ) {
                    items(profile.playlists) { song ->
                        // Song Card
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Song Title
                                Text(
                                    text = song,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Black,
                                    modifier = Modifier.weight(1f) // Take available space
                                )
                            }
                        }
                    }
                }
            } else {
                Text(
                    text = "No songs in the playlist",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}



