package com.brigoli.finalproject



import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DiscoverMusicActivity : AppCompatActivity() {

    private lateinit var musicAdapter: MusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover_music)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMusic)
        recyclerView.layoutManager = LinearLayoutManager(this)

        musicAdapter = MusicAdapter(emptyList())
        recyclerView.adapter = musicAdapter

        val searchButton = findViewById<Button>(R.id.searchButton)
        val searchEditText = findViewById<EditText>(R.id.searchEditText)

        searchButton.setOnClickListener {
            val searchQuery = searchEditText.text.toString().trim()
            if (searchQuery.isNotEmpty()) {
                searchTracks(searchQuery)
            }
        }

        // Initially, load top tracks
        loadTopTracks()
    }

    private fun loadTopTracks() {
        val lastfmApiKey = "2f54eb9d81fcae1d54b42dca0d07cf78"
        val retrofit = Retrofit.Builder()
            .baseUrl("http://ws.audioscrobbler.com/2.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val lastfmService = retrofit.create(LastfmService::class.java)
        val call = lastfmService.getTopTracks(lastfmApiKey, "json")

        call.enqueue(object : Callback<LastfmResponse> {
            override fun onResponse(call: Call<LastfmResponse>, response: Response<LastfmResponse>) {
                handleResponse(response)
            }

            override fun onFailure(call: Call<LastfmResponse>, t: Throwable) {
                Log.e("DiscoverMusicActivity", "API call failed", t)
            }
        })
    }

    private fun searchTracks(query: String) {
        val lastfmApiKey = "2f54eb9d81fcae1d54b42dca0d07cf78"
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ws.audioscrobbler.com/2.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val lastfmService = retrofit.create(LastfmService::class.java)
        val call = lastfmService.searchTracks(lastfmApiKey, "json", query)

        call.enqueue(object : Callback<LastfmResponse> {
            override fun onResponse(call: Call<LastfmResponse>, response: Response<LastfmResponse>) {
                handleResponse(response)
            }

            override fun onFailure(call: Call<LastfmResponse>, t: Throwable) {
                Log.e("DiscoverMusicActivity", "API call failed", t)
            }
        })
    }

    private fun handleResponse(response: Response<LastfmResponse>) {
        if (response.isSuccessful) {
            val tracks = response.body()?.results?.trackmatches?.track ?: emptyList()
            val musicItems = tracks.map { track ->
                MusicItem(
                    track.name ?: "Unknown",
                    track.artist ?: "Unknown",
                    track.url ?: "",
                    track.listeners ?: "0",
                    track.images ?: emptyList()
                )
            }
            musicAdapter.setData(musicItems)
        } else {
            Log.e("DiscoverMusicActivity", "API call failed: ${response.code()}")
        }
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
