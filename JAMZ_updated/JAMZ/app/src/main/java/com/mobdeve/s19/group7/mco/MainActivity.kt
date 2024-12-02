package com.mobdeve.s19.group7.mco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mobdeve.s19.group7.mco.ui.theme.MOBDEVEMACHINEPROJECTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MOBDEVEMACHINEPROJECTTheme {
                MainScreen() // Ensure MainScreen now uses the updated user profile logic
            }
        }
    }
}
