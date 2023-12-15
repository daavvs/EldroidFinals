package com.brigoli.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun createPlaylist(view: View) {
        val intent = Intent(this, CreatePlaylistActivity::class.java)
        startActivity(intent)
    }

    fun listPlaylists(view: View) {
        val intent = Intent(this, ListPlaylistsActivity::class.java)
        startActivity(intent)
    }
    fun discoverMusic(view: View) {
        val intent = Intent(this, DiscoverMusicActivity::class.java)
        startActivity(intent)
    }

}