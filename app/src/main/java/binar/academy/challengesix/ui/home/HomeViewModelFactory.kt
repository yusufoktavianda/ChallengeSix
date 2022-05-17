package binar.academy.challengesix.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import binar.academy.challengesix.data.remote.MovieRepository
import java.lang.IllegalArgumentException

class HomeViewModelFactory constructor(private val movieRepository: MovieRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(movieRepository) as T
        }
        throw IllegalArgumentException("unknown view model")
    }
}