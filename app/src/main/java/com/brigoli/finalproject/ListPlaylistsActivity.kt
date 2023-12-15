package com.brigoli.finalproject

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ListPlaylistsActivity : AppCompatActivity() {

    private val PREFS_NAME = "PlaylistPrefs"
    private lateinit var playlists: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_playlists)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sharedPreferences: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val playlistsString = sharedPreferences.getString("playlists", "")
        playlists = playlistsString?.split(",")?.toTypedArray() ?: arrayOf()

        val listView: ListView = findViewById(R.id.listViewPlaylists)
        val adapter = ArrayAdapter(this, R.layout.list_item_playlist, R.id.textViewPlaylistItem, playlists)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            // Handle click on a playlist item
            showOptionsDialog(position)
        }
    }

    private fun showOptionsDialog(position: Int) {
        val options = arrayOf("Rename", "Delete")

        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Options")
        alertDialog.setItems(options) { _, which ->
            when (which) {
                0 -> showRenameDialog(position)
                1 -> showDeleteDialog(position)
            }
        }
        alertDialog.show()
    }

    private fun showRenameDialog(position: Int) {
        val renameDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_rename_playlist, null)
        val editTextNewPlaylistName: EditText = renameDialogView.findViewById(R.id.editTextNewPlaylistName)

        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Rename Playlist")
        alertDialog.setView(renameDialogView)

        alertDialog.setPositiveButton("Rename") { _, _ ->
            val newPlaylistName = editTextNewPlaylistName.text.toString()
            if (newPlaylistName.isNotEmpty()) {
                renamePlaylist(position, newPlaylistName)
            }
        }

        alertDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        alertDialog.show()
    }

    private fun showDeleteDialog(position: Int) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Delete Playlist")
        alertDialog.setMessage("Are you sure you want to delete this playlist?")

        alertDialog.setPositiveButton("Yes") { _, _ ->
            deletePlaylist(position)
        }

        alertDialog.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        alertDialog.show()
    }

    private fun deletePlaylist(position: Int) {
        val sharedPreferences: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val tempList = playlists.toMutableList()
        tempList.removeAt(position)
        playlists = tempList.toTypedArray()

        val newPlaylists = playlists.joinToString(",")
        editor.putString("playlists", newPlaylists)
        editor.apply()

        // Update the ListView
        val listView: ListView = findViewById(R.id.listViewPlaylists)
        val adapter = ArrayAdapter(this, R.layout.list_item_playlist, R.id.textViewPlaylistItem, playlists)
        listView.adapter = adapter
    }

    private fun renamePlaylist(position: Int, newPlaylistName: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val tempList = playlists.toMutableList()
        tempList[position] = newPlaylistName
        playlists = tempList.toTypedArray()

        val newPlaylists = playlists.joinToString(",")
        editor.putString("playlists", newPlaylists)
        editor.apply()

        // Update the ListView
        val listView: ListView = findViewById(R.id.listViewPlaylists)
        val adapter = ArrayAdapter(this, R.layout.list_item_playlist, R.id.textViewPlaylistItem, playlists)
        listView.adapter = adapter
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}