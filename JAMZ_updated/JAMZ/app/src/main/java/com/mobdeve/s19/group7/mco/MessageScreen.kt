package com.mobdeve.s19.group7.mco

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen(profile: Profile, navController: NavHostController) {
    var currentMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Messages with ${profile.name}") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Display existing messages
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(profile.messages) { message ->
                        MessageCard(message = message)
                    }
                }

                // Input field and Send button in a row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = currentMessage,
                        onValueChange = { currentMessage = it },
                        label = { Text("Type a message") },
                        modifier = Modifier.weight(1f) // Take up remaining space
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (currentMessage.isNotBlank()) {
                                profile.messages.add(Message("You", currentMessage))
                                currentMessage = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5CE1E6)), // Button color
                        modifier = Modifier
                    ) {
                        Text("Send")
                    }
                }
            }
        }
    )
}

@Composable
fun MessageCard(message: Message) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "${message.sender}: ${message.content}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
