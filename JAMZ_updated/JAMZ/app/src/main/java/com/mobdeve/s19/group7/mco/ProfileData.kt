package com.mobdeve.s19.group7.mco

import kotlin.random.Random

object ProfileData {
    val profiles = listOf(
        Profile(
            name = "Joe",
            pictureResId = R.drawable.profilepic1,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Hana",
            pictureResId = R.drawable.profilepic2,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Chris",
            pictureResId = R.drawable.profilepic3,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Tin",
            pictureResId = R.drawable.profilepic4,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Charles",
            pictureResId = R.drawable.profilepic5,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Max",
            pictureResId = R.drawable.profilepic6,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Lando",
            pictureResId = R.drawable.profilepic7,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Oscar",
            pictureResId = R.drawable.profilepic8,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Jennie",
            pictureResId = R.drawable.profilepic9,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Lewis",
            pictureResId = R.drawable.profilepic10,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Rick",
            pictureResId = R.drawable.profilepic11,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Sara",
            pictureResId = R.drawable.profilepic12,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Boyet",
            pictureResId = R.drawable.profilepic13,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "John",
            pictureResId = R.drawable.profilepic14,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Rina",
            pictureResId = R.drawable.profilepic15,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Micah",
            pictureResId = R.drawable.profilepic16,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Mick",
            pictureResId = R.drawable.profilepic17,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Alex",
            pictureResId = R.drawable.profilepic18,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Franco",
            pictureResId = R.drawable.profilepic19,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Marie",
            pictureResId = R.drawable.profilepic20,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Jam",
            pictureResId = R.drawable.profilepic21,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
        Profile(
            name = "Lissandra",
            pictureResId = R.drawable.profilepic22,
            playlists = getRandomSongs(),
            likesUser = Random.nextBoolean()
        ),
    )

    // Default user profile
    var userProfile = Profile(
        name = "Your Name",
        pictureResId = R.drawable.your_picture,
        playlists = mutableListOf(), // Start with an empty playlist
        likesUser = false // The user's profile does not need a `likesUser` property
    )

    // Helper function to get random songs from the SongDatabase
    private fun getRandomSongs(): MutableList<String> {
        return SongDatabase.allSongs.shuffled().take(10).toMutableList() // Take 10 random songs
    }
}
