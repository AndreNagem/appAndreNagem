package br.com.andre.appandrenagem.api

import br.com.andre.appandrenagem.vo.MovieDetail
import br.com.andre.appandrenagem.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MobieDBInterface {

    //https://api.themoviedb.org/3/movie/popular?api_key=5f6d540b691edf96d34cc92c25340ebb&language=en-US
    //https://api.themoviedb.org/3/movie/299534?api_key=5f6d540b691edf96d34cc92c25340ebb&language=en-US
    // https://api.themoviedb.org/3/

  @GET("movie/popular")
  fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

  @GET("movie/{movie_id}")
  fun getMovieDetails(@Path("movie_id")id: Int): Single<MovieDetail>
}