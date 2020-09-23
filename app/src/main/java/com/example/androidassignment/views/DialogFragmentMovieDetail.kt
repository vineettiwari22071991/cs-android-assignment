package com.example.androidassignment.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.R
import com.example.androidassignment.adapter.GenresAdapter
import com.example.androidassignment.core.getViewModel
import com.example.androidassignment.utils.dateConverter
import com.example.androidassignment.utils.getProgressDrawable
import com.example.androidassignment.utils.loadImage
import com.example.androidassignment.viewmodels.MovieDetailViewModel
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class DialogFragmentMovieDetail(val items: String) : DialogFragment() {

    lateinit var viewModel: MovieDetailViewModel
    private val generesdAdapter = GenresAdapter(arrayListOf())
    lateinit var tv_errorview: TextView
    lateinit var pb_movie_detailview: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie_detail, container)

        val rv_genres = rootView.findViewById(R.id.rv_genres) as RecyclerView
        val arrow = rootView.findViewById(R.id.arrow) as ImageView
        tv_errorview = rootView.findViewById(R.id.tv_error) as TextView
        pb_movie_detailview = rootView.findViewById(R.id.pb_movie_detail) as ProgressBar

        viewModel = getViewModel(MovieDetailViewModel::class) as MovieDetailViewModel

        viewModel.getMovieDetails(Integer.parseInt(items))
        setMovieDetail()
        setErrorView()
        rv_genres.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = generesdAdapter
        }
        arrow.setOnClickListener {
            dismiss()
        }


        return rootView;
    }


    @SuppressLint("SetTextI18n")
    private fun setMovieDetail() {
        viewModel.movieDetail.observe(this, Observer {

            it.apply {

                pb_movie_detailview.visibility = GONE
                tv_duration.text = dateConverter(this.release_date) + " - 1h 45m"
                tv_overview.text = this.overview
                tv_title.text = this.title

                img_poster.loadImage(
                    "https://image.tmdb.org/t/p/w500" + this.poster_path,
                    getProgressDrawable(img_poster.context)
                )
                generesdAdapter.updateGenreslist(this.genres)

            }

        })


    }

    private fun setErrorView() {

        viewModel.error.observe(this, Observer {

            errorView(it)
        })

    }


    private fun errorView(errorvalue: Boolean) {
        if (errorvalue) {
            tv_errorview.visibility = VISIBLE
            pb_movie_detailview.visibility = GONE
            tv_duration.visibility = GONE
            tv_overview.visibility = GONE
            tv_title.visibility = GONE

            img_poster.visibility = GONE
        } else {
            tv_errorview.visibility = GONE
            pb_movie_detailview.visibility = GONE
            tv_duration.visibility = VISIBLE
            tv_overview.visibility = VISIBLE
            tv_title.visibility = VISIBLE

            img_poster.visibility = VISIBLE


        }
    }

}