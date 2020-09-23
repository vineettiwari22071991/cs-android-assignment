package com.example.androidassignment.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.R
import com.example.androidassignment.models.MoviePopularResult
import com.example.androidassignment.utils.dateConverter
import com.example.androidassignment.utils.getProgressDrawable
import com.example.androidassignment.utils.loadImage
import com.example.androidassignment.views.FragmentMovieList
import kotlinx.android.synthetic.main.item_vertical_movie_list.view.*

class MovieListVerticalAdapter(
    var movieList:ArrayList<MoviePopularResult>,
    var imovieClick: FragmentMovieList
):RecyclerView.Adapter<MovieListVerticalAdapter.MyMoviewViewHolder>() {

    fun updatedMoviePopularList(newMoview:ArrayList<MoviePopularResult>)
    {
        //movieList.clear()
        movieList.addAll(newMoview)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=MyMoviewViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_vertical_movie_list, parent, false)
        )

    override fun getItemCount()=movieList.size

    override fun onBindViewHolder(holder: MyMoviewViewHolder, position: Int) {

        holder.bind(movieList[position],imovieClick)

    }




    class MyMoviewViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        private val img_poster=view.img_poster
        private val moviename=view.tv_title
        private val movierelease=view.tv_release
        private val movieduration=view.tv_duration
        private val progressDrawable= getProgressDrawable(img_poster.context)
        private val parentview=view.vparent
        private val rating=view.rating
        private val txtProgress=view.txtProgress


        @SuppressLint("SetTextI18n")
        fun bind(moviePopular:MoviePopularResult, imovieClick:IonMovieClick)
        {

            moviename.text=moviePopular.title
            movierelease.text=dateConverter(moviePopular.release_date)
            movieduration.text="1h 45m"
            img_poster.loadImage("https://image.tmdb.org/t/p/w500"+moviePopular.poster_path,progressDrawable)

            parentview.setOnClickListener {

                imovieClick.onMovieClick(""+moviePopular.id)
            }


            val percentage=(moviePopular.vote_average/10)*100
            val percentagevalue=""+percentage.toInt()+"%"

            if(percentage>50)
            {

                rating.progress=percentage.toInt()
                rating.secondaryProgress=0
                txtProgress.text=percentagevalue

            }else
            {
                rating.progress=0
                rating.secondaryProgress=percentage.toInt()
                txtProgress.text=percentagevalue
            }

        }

    }

    interface  IonMovieClick{

        fun onMovieClick(id: String)
    }
}



