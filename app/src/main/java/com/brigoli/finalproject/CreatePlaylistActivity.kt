package com.brigoli.finalproject

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CreatePlaylistActivity : AppCompatActivity() {

    private val PREFS_NAME = "PlaylistPrefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_playlist)
    }
    fun savePlaylist(view: View) {
        val playlistNameEditText: EditText = findViewById(R.id.editTextPlaylistName)
        val playlistName = playlistNameEditText.text.toString()

        val sharedPreferences: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Assuming playlists are stored as a comma-separated string
        val existingPlaylists = sharedPreferences.getString("playlists", "")
        val newPlaylists = if (existingPlaylists.isNullOrEmpty()) playlistName else "$existingPlaylists,$playlistName"

        editor.putString("playlists", newPlaylists)
        editor.apply()

        finish()
    }
}