package com.brigoli.finalproject
import com.google.gson.annotations.SerializedName

data class LastfmResponse(
    val results: Results
)

data class Results(
    val trackmatches: TrackMatches
)

data class TrackMatches(
    val track: List<Track>
)

data class Track(
    val name: String?,
    val artist: String?,
    val url: String?,
    val listeners: String?,
    val images: List<Image>?
)

data class Image(
    val url: String?,
    val size: String?
)