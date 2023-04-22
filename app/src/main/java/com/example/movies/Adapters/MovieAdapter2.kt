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
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.DataModel.Movie
import com.example.movies.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text

class MovieAdapter2(val clickListener: ItemClickListener): RecyclerView.Adapter<MovieAdapter2.MovieViewHolder>() {

    class MovieViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.titleTvs)
        val year: TextView= view.findViewById(R.id.yearanddurationTvs)
        val cast: TextView= view.findViewById(R.id.castTvs)
        val image: ImageView = view.findViewById(R.id.imageAcs)
        val viewMore: TextView= view.findViewById(R.id.viewMores)
        val desc: TextView=view.findViewById(R.id.descTvs)
        val delete: FloatingActionButton=view.findViewById(R.id.delete)
        val yt: TextView=view.findViewById(R.id.ytsaved)
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
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item_view2, parent, false))

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
        holder.yt.setOnClickListener {
            if(movie.YouTubeTrailer!=null) {
                val q= Uri.parse("https://www.youtube.com/${movie.YouTubeTrailer}")
                holder.itemView.context.startActivity(Intent(Intent.ACTION_VIEW, q))

//                startActivity(holder.itemView.context,Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/${ movie.YouTubeTrailer }")))
            }
            else Toast.makeText(holder.itemView.context, "Couldnt find youtube trailer", Toast.LENGTH_SHORT).show()
        }
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

        holder.delete.setOnClickListener {
            clickListener.onItemClick(it, movie)
        }




    }
}