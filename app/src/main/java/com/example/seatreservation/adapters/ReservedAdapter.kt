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

class ReservedAdapter(): RecyclerView.Adapter<ReservedAdapter.ReservedViewHolder>() {

    private var reservedList: List<Reservation> = listOf()

    inner class ReservedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReservedAdapter.ReservedViewHolder {
        return ReservedViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.movie_element,
            parent,
            false
        )
        )
    }

    override fun getItemCount(): Int {
        return reservedList.size
    }

    override fun onBindViewHolder(holder: ReservedViewHolder, position: Int) {
        holder.itemView.apply {
            val image = reservedList[position].movie.image
            val id = resources.getIdentifier(image, "drawable", context.packageName)
            val drawable: Drawable? = ResourcesCompat.getDrawable(resources, id, null)

            this.findViewById<ImageView>(R.id.movie_image).setImageDrawable(drawable)
            this.findViewById<TextView>(R.id.movie_title).text = reservedList[position].movie.title
            //this.findViewById<TextView>(R.id.movie_price).text = "Cena: ${reservedList[position].price}"

        }
    }

    fun updateList(newReservedList: List<Reservation>){
        reservedList = newReservedList
        notifyDataSetChanged()
    }
}