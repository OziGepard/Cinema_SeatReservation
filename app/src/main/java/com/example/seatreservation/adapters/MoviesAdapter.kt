package com.example.seatreservation.adapters

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.seatreservation.R
import com.example.seatreservation.models.Movie

class MoviesAdapter(val changeFragment: (String, Movie) -> Unit) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var moviesList: List<Movie> = listOf()
    private val TAG = "MoviesAdapter"

    inner class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.movie_element,
            parent,
            false
            )
        )
    }

    override fun onBindViewHolder(holder: MoviesAdapter.MovieViewHolder, position: Int) {
        val title = moviesList[position].title
        val price = moviesList[position].price

        holder.itemView.apply {
            val image = moviesList[position].image
            val id = resources.getIdentifier(image, "drawable", context.packageName)
            val drawable: Drawable? = ResourcesCompat.getDrawable(resources, id, null)


            this.findViewById<ImageView>(R.id.movie_image).setImageDrawable(drawable)
            this.findViewById<TextView>(R.id.movie_title).text = title
            this.findViewById<TextView>(R.id.movie_price).text = "Cena: $price"

        }

        holder.itemView.setOnClickListener{
            changeFragment(title, moviesList[position])
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun updateList(newMoviesList: List<Movie>){
        moviesList = newMoviesList
        notifyDataSetChanged()
    }
}