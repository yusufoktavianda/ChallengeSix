package binar.academy.challengesix.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.academy.challengesix.data.local.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel (private val repository: UserRepository):ViewModel(){
    fun logout(){
        viewModelScope.launch {
            repository.clearDataStore()
        }
    }
}