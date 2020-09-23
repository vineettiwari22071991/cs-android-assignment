package com.example.androidassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.R
import com.example.androidassignment.models.Genre
import kotlinx.android.synthetic.main.item_genres.view.*

//This Adapter is use by Dialog Fragment Details to show Genres List
class GenresAdapter(val genreList:ArrayList<Genre>):RecyclerView.Adapter<GenresAdapter.MyViewHolder>() {


    fun updateGenreslist(newMoview:ArrayList<Genre>)
    {
        genreList.clear()
        genreList.addAll(newMoview)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_genres,parent,false))

    override fun getItemCount()= genreList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(genreList[position])

    }


    class MyViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        private val tvGenres=view.tv_genres

        fun bind(genre: Genre)
        {

            tvGenres.text=genre.name
        }

    }
}