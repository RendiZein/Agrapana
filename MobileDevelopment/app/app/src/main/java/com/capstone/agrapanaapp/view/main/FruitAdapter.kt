package com.capstone.agrapanaapp.view.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.capstone.agrapanaapp.R
import com.capstone.agrapanaapp.model.Fruit

class FruitAdapter(private val listHero: ArrayList<Fruit>) : RecyclerView.Adapter<FruitAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_available_fruit, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listHero[position])
    }

    override fun getItemCount(): Int = listHero.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgPhoto: ImageView = itemView.findViewById(R.id.cv_avatar_fruit)
        private var tvName: TextView = itemView.findViewById(R.id.nameTextView)

        fun bind(fruit: Fruit) {
            imgPhoto.setImageResource(fruit.photo)
            tvName.text = fruit.name

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("Fruit", fruit)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(imgPhoto, "profile"),
                        Pair(tvName, "name")
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

}