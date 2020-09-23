package com.example.androidassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.R
import com.example.androidassignment.models.MovieNowPlayingResult
import com.example.androidassignment.utils.getProgressDrawable
import com.example.androidassignment.utils.loadImage
import kotlinx.android.synthetic.main.item_horizontal_movie.view.*

class MovieListHorizontalAdapter(var movieList:ArrayList<MovieNowPlayingResult>,
var imovieClick:IonMovieClick):RecyclerView.Adapter<MovieListHorizontalAdapter.MovieViewHolder>() {

    fun updatedMovieList(newMoview:ArrayList<MovieNowPlayingResult>)
    {
        movieList.clear()
        movieList.addAll(newMoview)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= MovieViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal_movie,parent,false)
    )

    override fun getItemCount()=movieList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position],imovieClick)
    }



     class MovieViewHolder(view: View):RecyclerView.ViewHolder(view)
    {

        private val imageView=view.img_poster
        private val progressDrawable= getProgressDrawable(view.context)


        fun bind(movieresult: MovieNowPlayingResult,imovieClick: IonMovieClick)
        {
            imageView.loadImage("https://image.tmdb.org/t/p/w500"+movieresult.poster_path,progressDrawable)

            imageView.setOnClickListener{

                imovieClick.onMovieClick(""+movieresult.id)
            }

        }

    }


    interface  IonMovieClick{

        fun onMovieClick(id: String)
    }

}