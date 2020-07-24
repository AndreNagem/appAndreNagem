package br.com.andre.appandrenagem.ui

import androidx.lifecycle.LiveData
import br.com.andre.appandrenagem.api.MobieDBInterface
import br.com.andre.appandrenagem.repository.MovieDetailsNetworkDataSource
import br.com.andre.appandrenagem.repository.NetworkState
import br.com.andre.appandrenagem.vo.MovieDetail
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService : MobieDBInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetail> {

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }



}