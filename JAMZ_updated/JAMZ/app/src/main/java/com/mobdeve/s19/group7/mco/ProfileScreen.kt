package com.mobdeve.s19.group7.mco

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable
import org.burnoutcrew.reorderable.detectReorder



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    userProfile: Profile,
    onProfileUpdate: (Profile) -> Unit,
    playlists: List<String> // Pass the user's playlist as a list of strings
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                actions = {
                    Button(
                        onClick = { navController.navigate("swipe_screen") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5CE1E6))
                    ) {
                        Text("Home", color = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display Profile Picture
            Image(
                painter = painterResource(id = userProfile.pictureResId),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp),
                contentScale = ContentScale.Crop
            )

            // Display Profile Name
            Text(
                text = userProfile.name,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Edit Profile Button
            Button(
                onClick = { navController.navigate("edit_profile") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5CE1E6))
            ) {
                Text("Edit Profile", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Edit Playlist Button
            Button(
                onClick = { navController.navigate("edit_playlist") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5CE1E6))
            ) {
                Text("Edit Playlist", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Playlist Section
            Text(
                text = "My Playlist",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(playlists) { song ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = song,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavHostController,
    userProfile: Profile,
    onProfileUpdate: (Profile) -> Unit
) {
    var name by remember { mutableStateOf(userProfile.name) }
    var profilePictureUri by remember { mutableStateOf("") } // String type for URI or resource path

    // Set initial profile picture
    LaunchedEffect(userProfile.pictureResId) {
        profilePictureUri = "android.resource://com.mobdeve.s19.group7.mco/${userProfile.pictureResId}"
    }

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            profilePictureUri = uri.toString() // Use URI from the gallery
        }
    }

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            // Save the bitmap to the device and obtain a URI (custom implementation required for storage)
            // For demonstration, the bitmap can be converted to a placeholder URI or temporary display
            profilePictureUri = bitmap.toString() // Placeholder for actual storage logic
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Display Profile Picture
            Image(
                painter = if (profilePictureUri.isEmpty()) {
                    painterResource(id = R.drawable.your_picture) // Default picture
                } else {
                    rememberAsyncImagePainter(model = profilePictureUri) // URI for loaded image
                },
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(150.dp)
                    .padding(16.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Change Profile Picture Buttons
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    onClick = { galleryLauncher.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5CE1E6))
                ) {
                    Text("Gallery", color = Color.White)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { cameraLauncher.launch() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5CE1E6))
                ) {
                    Text("Camera", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Name Input Field
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Your Name") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF5CE1E6),
                    cursorColor = Color(0xFF5CE1E6)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Save Button
            Button(
                onClick = {
                    // Update Profile
                    onProfileUpdate(
                        Profile(name = name, pictureResId = R.drawable.your_picture) // Resource logic
                    )
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5CE1E6))
            ) {
                Text("Save", color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPlaylistScreen(
    navController: NavHostController,
    userPlaylist: MutableList<String>,
    allSongs: List<String>, // Global song database
    onPlaylistUpdate: (List<String>) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var filteredSongs by remember { mutableStateOf(allSongs) }

    // Local state for dragging and reordering
    var dragOffset by remember { mutableStateOf(-1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Playlist") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search Bar
            TextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    filteredSongs = if (query.isEmpty()) {
                        allSongs
                    } else {
                        allSongs.filter { it.contains(query, ignoreCase = true) }
                    }
                },
                label = { Text("Search Songs") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF5CE1E6),
                    cursorColor = Color(0xFF5CE1E6)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Global Song List Section
            Text(
                text = "Add Songs",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(filteredSongs) { song ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                if (!userPlaylist.contains(song)) {
                                    userPlaylist.add(song)
                                    onPlaylistUpdate(userPlaylist)
                                }
                            },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                    ) {
                        Text(
                            text = song,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Playlist Section
            Text(
                text = "Your Playlist",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
            ) {
                itemsIndexed(userPlaylist, key = { _, item -> item }) { index, song ->
                    val isDragging = index == dragOffset
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(if (isDragging) Color.LightGray else Color.White)
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDragStart = { dragOffset = index },
                                    onDragEnd = { dragOffset = -1 },
                                    onDragCancel = { dragOffset = -1 },
                                    onDrag = { change, _ ->
                                        change.consume()
                                        val targetIndex = dragOffset + 1
                                        if (targetIndex in userPlaylist.indices) {
                                            userPlaylist.add(targetIndex, userPlaylist.removeAt(index))
                                            onPlaylistUpdate(userPlaylist)
                                        }
                                    }
                                )
                            },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = song,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            IconButton(
                                onClick = {
                                    userPlaylist.remove(song)
                                    onPlaylistUpdate(userPlaylist)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remove Song",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
