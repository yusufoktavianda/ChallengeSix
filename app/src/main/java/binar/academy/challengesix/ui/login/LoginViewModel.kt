package binar.academy.challengesix.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.academy.challengesix.data.local.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(private val repository: UserRepository):ViewModel (){
    val loginvalidation : MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun result():LiveData<Boolean>{
        return loginvalidation
    }

    fun reset(){
        loginvalidation.value=false
    }

    fun authLogin(email:String, password:String){
        val emailResult  = StringBuffer()
        val passwordResult  = StringBuffer()
        val usernameResult  = StringBuffer()
        viewModelScope.launch(Dispatchers.IO){
            val result = repository.authLogin(email, password)
            runBlocking(Dispatchers.Main){
                if (result!=null){
                    result.let {
                        passwordResult.append(it.password.toString())
                        emailResult.append(it.email.toString())
                        usernameResult.append(it.username.toString())
                    }
                    if (email == emailResult.toString() && password == passwordResult.toString()){
                        loginvalidation.value = true
                        viewModelScope.launch {
                            repository.setUsername(usernameResult.toString())
                        }
                    }
                } else{
                    loginvalidation.value=false
                }
            }
        }
    }
}