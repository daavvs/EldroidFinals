package com.brigoli.finalproject
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.brigoli.finalproject.Image
import com.brigoli.finalproject.MusicItem
import com.brigoli.finalproject.R

class MusicAdapter(private var musicList: List<MusicItem>) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val artistTextView: TextView = itemView.findViewById(R.id.artistTextView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_music, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val musicItem = musicList[position]

        holder.titleTextView.text = musicItem.title
        holder.artistTextView.text = musicItem.artist

        // Load the image using Glide library
        if (musicItem.images.isNotEmpty()) {
            val imageUrl = musicItem.images.firstOrNull { it.size == "extralarge" }?.url
            imageUrl?.let {
                Glide.with(holder.itemView.context)
                    .load(it)
                    .into(holder.imageView)
            }
        }
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    fun setData(newData: List<MusicItem>) {
        musicList = newData
        notifyDataSetChanged()
    }
}

