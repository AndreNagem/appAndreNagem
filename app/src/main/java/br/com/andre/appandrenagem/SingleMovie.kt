package br.com.andre.appandrenagem

import android.graphics.Movie
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.andre.appandrenagem.api.MobieDBInterface
import br.com.andre.appandrenagem.api.MovieDBCliente
import br.com.andre.appandrenagem.api.POSTER_BASE_URL
import br.com.andre.appandrenagem.repository.NetworkState
import br.com.andre.appandrenagem.ui.*
import br.com.andre.appandrenagem.vo.MovieDetail
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class SingleMovie: AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository
    private lateinit var viewModel1: ListaViewModel
    lateinit var movieRepository1: MoviePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val apiService : MobieDBInterface = MovieDBCliente.getCliente()
        val apiService1 : MobieDBInterface = MovieDBCliente.getCliente()


        movieRepository = MovieDetailsRepository(apiService)
        movieRepository1 = MoviePagedListRepository(apiService1)


        viewModel1=getViewModel1()


        val movieAdapter = PopularMoviePagedListAdapter(this)
        val LayoutManager = LinearLayoutManager(this)


        rv_movie_list.layoutManager = LayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter

        viewModel1.moviePagedList.observe(this, androidx.lifecycle.Observer {
            movieAdapter.submitList(it)
        })

        viewModel1.networkState.observe(this, androidx.lifecycle.Observer {
            progress_bar.visibility = if (viewModel1.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (viewModel1.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel1.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })


// FIlME PRINCIPAL

        val movieId = 299534

        viewModel = getViewModel(movieId)


        viewModel.movieDetails.observe(this, androidx.lifecycle.Observer {
            bindUI(it)
        })


        viewModel.networkState.observe(this, androidx.lifecycle.Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })

    }

    fun bindUI( it: MovieDetail){
        movie_title.text = it.title
        movie_rating.text = it.popularity.toString()
        movie_overview.text = it.voteCount.toString()

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster);


    }

    private fun getViewModel1(): ListaViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ListaViewModel(
                    movieRepository1
                ) as T
            }
        })[ListaViewModel::class.java]
    }


    private fun getViewModel(movieId:Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(
                    movieRepository,
                    movieId
                ) as T
            }
        })[SingleMovieViewModel::class.java]
    }

    fun onCustomClick(view: View) {
    }
}
