package com.jeanbarrossilva.screen.app.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.jeanbarrossilva.screen.app.ui.theme.ScreenTheme

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenTheme {
                Main(this)
            }
        }
    }
}