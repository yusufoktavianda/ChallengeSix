package binar.academy.challengesix

import android.app.Application
import binar.academy.challengesix.data.remote.MovieDataSource
import binar.academy.challengesix.data.remote.MovieRepository

class MyApplication: Application() {
    private val remoteDataSource by lazy { MovieDataSource() }
    val repository by lazy { MovieRepository(remoteDataSource) }

}