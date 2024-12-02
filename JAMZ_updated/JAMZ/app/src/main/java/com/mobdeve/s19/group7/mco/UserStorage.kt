package com.mobdeve.s19.group7.mco

// Simulated local storage
object UserStorage {
    val users = mutableListOf<Pair<String, String>>() // List of email-password pairs

    // Check if email is already registered
    fun isEmailRegistered(email: String): Boolean {
        return users.any { it.first == email }
    }

    // Add a new user
    fun addUser(email: String, password: String) {
        users.add(Pair(email, password))
    }
}
