package br.com.andre.appandrenagem.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.andre.appandrenagem.R
import br.com.andre.appandrenagem.api.POSTER_BASE_URL
import br.com.andre.appandrenagem.repository.NetworkState
import br.com.andre.appandrenagem.vo.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlinx.android.synthetic.main.network_state_item.view.*

class PopularMoviePagedListAdapter(public val context: Context) : PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == MOVIE_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.movie_list_item, parent, false)
            return MovieItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position),context)
        }
        else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            MOVIE_VIEW_TYPE
        }
    }




    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }


    class MovieItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie?,context: Context) {
            itemView.cv_movie_title.text = movie?.title
            itemView.cv_movie_release_date.text =  movie?.releaseDate
            val genreId =movie?.genreIds.toString().subSequence(1,3)
            if(genreId==28.toString()){
                val genre = "Action"
                        itemView.genre.text=genre
            }
            if(genreId==12.toString()){
                val genre = "Adventure"
                itemView.genre.text=genre
            }
            if(genreId==16.toString()){
                val genre = "Animation"
                itemView.genre.text=genre
            }
            if(genreId==35.toString()){
                val genre = "Comedy"
                itemView.genre.text=genre
            }
            if(genreId==80.toString()){
                val genre = "Crime"
                itemView.genre.text=genre
            }
            if(genreId==99.toString()){
                val genre = "Documentary"
                itemView.genre.text=genre
            }
            if(genreId==18.toString()){
                val genre = "Drama"
                itemView.genre.text=genre
            }
            if(genreId==10751.toString()){
                val genre = "Family"
                itemView.genre.text=genre
            }
            if(genreId==14.toString()){
                val genre = "Fantasy"
                itemView.genre.text=genre
            }
            if(genreId==36.toString()){
                val genre = "History"
                itemView.genre.text=genre
            }
            if(genreId==27.toString()){
                val genre = "Horror"
                itemView.genre.text=genre
            }
            if(genreId==10402.toString()){
                val genre = "Music"
                itemView.genre.text=genre
            }
            if(genreId==9648.toString()){
                val genre = "Mystery"
                itemView.genre.text=genre
            }
            if(genreId==10749.toString()){
                val genre = "Romance"
                itemView.genre.text=genre
            }
            if(genreId==878.toString()){
                val genre = "Fiction"
                itemView.genre.text=genre
            }
            if(genreId==10770.toString()){
                val genre = "TV Movie"
                itemView.genre.text=genre
            }
            if(genreId==53.toString()){
                val genre = "Thriller"
                itemView.genre.text=genre
            }
            if(genreId==10752.toString()){
                val genre = "War"
                itemView.genre.text=genre
            }
            if(genreId==37.toString()){
                val genre = "Western"
                itemView.genre.text=genre
            }


            val moviePosterURL = POSTER_BASE_URL + movie?.posterPath
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(itemView.cv_iv_movie_poster);

        }

    }

    class NetworkStateItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                itemView.progress_bar_item.visibility = View.VISIBLE;
            }
            else  {
                itemView.progress_bar_item.visibility = View.GONE;
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.msg;
            }
            else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.msg;
            }
            else {
                itemView.error_msg_item.visibility = View.GONE;
            }
        }
    }


    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
                notifyItemRemoved(super.getItemCount())    //remove the progressbar at the end
            } else {                                       //hasExtraRow is true and hadExtraRow false
                notifyItemInserted(super.getItemCount())   //add the progressbar at the end
            }
        } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
            notifyItemChanged(itemCount - 1)       //add the network message at the end
        }

    }




}