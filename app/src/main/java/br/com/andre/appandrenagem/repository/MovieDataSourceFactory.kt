package br.com.andre.appandrenagem.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import br.com.andre.appandrenagem.api.MobieDBInterface
import br.com.andre.appandrenagem.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory (private val apiService : MobieDBInterface, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource =  MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService,compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}