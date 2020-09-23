package com.example.androidassignment.views

import android.view.View.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.R
import com.example.androidassignment.adapter.MovieListHorizontalAdapter
import com.example.androidassignment.adapter.MovieListVerticalAdapter
import com.example.androidassignment.core.getViewModel
import com.example.androidassignment.utils.isInternetAvailable
import com.example.androidassignment.viewmodels.MovieListViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.*


class FragmentMovieList : Fragment(R.layout.fragment_movie_list),
    MovieListHorizontalAdapter.IonMovieClick, MovieListVerticalAdapter.IonMovieClick {

    lateinit var viewModel: MovieListViewModel
    private val movieNowPlayingAdapter = MovieListHorizontalAdapter(arrayListOf(), this)
    private val moviePopularAdapter = MovieListVerticalAdapter(arrayListOf(), this)
    private var pageno = 1
    lateinit var layoutManagerveritcal: LinearLayoutManager
    private var isloading = true;


    override fun onStart() {
        super.onStart()
        viewModel = getViewModel(MovieListViewModel::class) as MovieListViewModel


        if (isInternetAvailable(activity as MainActivity)) {
            viewModel.fetchNowPlayingMovieList()
            movie_list.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = movieNowPlayingAdapter
            }

            viewModel.fetchPopularMoiveList(pageno)

            movie_popular_list.apply {
                layoutManagerveritcal =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                layoutManager = layoutManagerveritcal
                adapter = moviePopularAdapter
            }


            horizontalMovieObserve()

            verticalMovieObserve()

            showError()

            movie_popular_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int, dy: Int
                ) {
                    if (dy > 0) {

                        if (!isloading) {
                            val totalvalue = moviePopularAdapter.getItemCount()
                            val lastitemvisibale =
                                layoutManagerveritcal.findLastVisibleItemPosition()

                            if (lastitemvisibale == totalvalue - 1) {
                                pb_movie_popular.visibility = VISIBLE
                                pageno += 1
                                viewModel.fetchPopularMoiveList(pageno)
                            }
                            isloading = false
                        }


                    }
                    super.onScrolled(recyclerView, dx, dy)

                }
            })

        } else {
            errorView(true)
        }


    }

    private fun horizontalMovieObserve() {

        viewModel.moviedata.observe(this, Observer { movie ->

            movie.let {

                movieNowPlayingAdapter.updatedMovieList(movie.results)
                pb_movie.visibility = GONE

            }

        })

    }

    private fun verticalMovieObserve() {

        viewModel.moviePopulardata.observe(this, Observer { movie ->

            movie.let {

                pb_movie_popular.visibility = GONE
                moviePopularAdapter.updatedMoviePopularList(movie.results)
                isloading = false

            }

        })
    }


    private fun showError() {

        viewModel.error.observe(this, Observer {

            errorView(it)
        })
    }


    private fun errorView(errorvalue: Boolean) {
        if (errorvalue) {
            tv_error.visibility = VISIBLE
            movie_popular_list.visibility = INVISIBLE
            movie_list.visibility = INVISIBLE
            pb_movie_popular.visibility = GONE
            pb_movie.visibility = GONE

        } else {
            tv_error.visibility = GONE
            movie_popular_list.visibility = VISIBLE
            movie_list.visibility = VISIBLE
            pb_movie_popular.visibility = GONE
            pb_movie.visibility = GONE
        }

    }

    override fun onMovieClick(id: String) {

        if (isInternetAvailable(activity as MainActivity)) {
            val detailDialog = DialogFragmentMovieDetail(id)

            detailDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme)
            detailDialog.show(activity!!.supportFragmentManager, "Detail_Dialog")
        } else {
            errorView(true)
        }
    }

}