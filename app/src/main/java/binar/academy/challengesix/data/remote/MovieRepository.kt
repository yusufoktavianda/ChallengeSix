package binar.academy.challengesix.data.remote

import binar.academy.challengesix.data.remote.modal.Result

class MovieRepository constructor(private  val movieDataSource: MovieDataSource) {

    fun getMovies(movieCallBack: MovieDataSource.MovieCallBack): List<Result> {
        val result = movieDataSource.getMovies(movieCallBack)
        return result
    }
}