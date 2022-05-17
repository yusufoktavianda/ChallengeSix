package binar.academy.challengesix.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import binar.academy.challengesix.data.local.UserRepository

class SplashViewModel(private val repository: UserRepository):ViewModel() {
    val loginvalidation : MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    fun result():LiveData<Boolean>{
        return loginvalidation
    }
    fun loginCheck():LiveData<String>{
        return repository.getUsernameValue().asLiveData()
    }
}