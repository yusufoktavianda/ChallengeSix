package binar.academy.challengesix.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.academy.challengesix.data.local.User
import binar.academy.challengesix.data.local.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class RegisterViewModel(private val repository: UserRepository):ViewModel(){
    val registervalidation : MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun result():LiveData<Boolean>{
        return registervalidation
    }

    fun reset(){
        registervalidation.postValue(false)
    }

    fun addUser(user: User, confirmPassword : String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.registerUser(user)
            runBlocking(Dispatchers.Main){
                if (result != 0. toLong()){
                    registervalidation.postValue(true)
                }
            }
        }
    }
}