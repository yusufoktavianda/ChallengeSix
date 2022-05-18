package binar.academy.challengesix.ui.profile

import androidx.lifecycle.*
import binar.academy.challengesix.data.local.User
import binar.academy.challengesix.data.local.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {

    private val user: MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }

    fun resultUser():LiveData<User>{
        return user
    }

    val updateValidation : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getUpdateValidation(): LiveData<Boolean> {
        return updateValidation
    }

    fun getUsername():LiveData<String>{
        return repository.getUsernameValue().asLiveData()
    }
    fun getEmail():LiveData<String>{
        return repository.getEmailValue().asLiveData()
    }
    fun setUsername(username:String){
        viewModelScope.launch {
            repository.setUsername(username)
        }
    }

    fun getUserData(username: String) {
        val usernameResult = StringBuffer()
        val fullnameResult = StringBuffer()
        val birthdateResult = StringBuffer()
        val addressResult = StringBuffer()
        val imageResult = StringBuffer()
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAllData(username = username)
            runBlocking(Dispatchers.Main) {
                result?.let {
                    usernameResult.append(it.username.toString())
                    fullnameResult.append(it.fullName.toString())
                    birthdateResult.append(it.birthDate.toString())
                    addressResult.append(it.address.toString())
                    imageResult.append(it.imgPath.toString())
                }
                val resultDataUser = User(
                    username = usernameResult.toString(),
                    fullName = fullnameResult.toString(),
                    birthDate = birthdateResult.toString(),
                    address = addressResult.toString(),
                    imgPath = imageResult.toString()
                )
                user.value = resultDataUser
            }
        }
    }

    fun updateData(userData: User,email: String){
        viewModelScope.launch(Dispatchers.IO){
            val result = repository.updateProfile(
                userData,email
            )
            runBlocking(Dispatchers.Main){
                if (result != 0){
                    updateValidation.postValue(true)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.clearDataStore()
        }
    }

}