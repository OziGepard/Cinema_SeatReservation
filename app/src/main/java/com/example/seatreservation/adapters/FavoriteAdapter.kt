package com.example.seatreservation.adapters

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.seatreservation.R
import com.example.seatreservation.models.Movie
import com.example.seatreservation.models.Reservation
import org.w3c.dom.Text

class FavoriteAdapter(): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var favoriteList: List<Movie> = listOf()

    inner class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.FavoriteViewHolder {
        return FavoriteViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.movie_element,
            parent,
            false
        )
        )
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.itemView.apply {
            val image = favoriteList[position].image
            val id = resources.getIdentifier(image, "drawable", context.packageName)
            val drawable: Drawable? = ResourcesCompat.getDrawable(resources, id, null)

            this.findViewById<ImageView>(R.id.movie_image).setImageDrawable(drawable)
            this.findViewById<TextView>(R.id.movie_title).text = favoriteList[position].title
            //this.findViewById<TextView>(R.id.movie_price).text = "Cena: ${reservedList[position].price}"

        }
    }

    fun updateList(newFavoriteList: List<Movie>){
        favoriteList = newFavoriteList
        notifyDataSetChanged()
    }
}