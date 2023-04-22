package com.example.movies.Adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.DataModel.Movie
import com.example.movies.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MovieAdapter(val itemClickListener: ItemClickListener): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.titleTv)
        val year: TextView= view.findViewById(R.id.yearanddurationTv)
        val cast: TextView= view.findViewById(R.id.castTv)
        val image: ImageView = view.findViewById(R.id.imageAc)
        val viewMore: TextView= view.findViewById(R.id.viewMore)
        val desc: TextView=view.findViewById(R.id.descTv)
        val addtofav: FloatingActionButton=view.findViewById(R.id.addtofav)
        val yt: TextView=view.findViewById(R.id.youtubebtn)
    }

    private val differCallback=object: DiffUtil.ItemCallback<Movie>(){
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.IMDBID==newItem.IMDBID
        }

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem==newItem
        }
    }
    val differ= AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item_view, parent, false))

    }

    override fun getItemCount(): Int {
        return differ.currentList.size

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie=differ.currentList[position]
        holder.desc.visibility=View.GONE
        Glide.with(holder.itemView.context).load(movie.MoviePoster).into(holder.image)
        holder.cast.text="Cast: "+movie.Cast
        holder.title.text=movie.Title
        holder.year.text="Year: " + movie.Year+"    Runtime: "+movie.Runtime+"m"
        holder.desc.text=movie.ShortSummary

        var descon=false
        holder.viewMore.setOnClickListener {
            if(descon==false) {
                holder.desc.visibility = View.VISIBLE
                holder.viewMore.text="Show Less..."
            }
            else {
                holder.desc.visibility = View.GONE
                holder.viewMore.text="Show More..."
            }
            descon=!descon
        }

        holder.yt.setOnClickListener {
            if(movie.YouTubeTrailer!=null) {
                val q=Uri.parse("https://www.youtube.com/${movie.YouTubeTrailer}")
                holder.itemView.context.startActivity(Intent(Intent.ACTION_VIEW, q))

//                startActivity(holder.itemView.context,Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/${ movie.YouTubeTrailer }")))
            }
            else Toast.makeText(holder.itemView.context, "Couldnt find youtube trailer", Toast.LENGTH_SHORT).show()
        }
        holder.addtofav.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Added to favourites...", Toast.LENGTH_SHORT).show()
            itemClickListener.onItemClick(it, movie)
        }


    }
}