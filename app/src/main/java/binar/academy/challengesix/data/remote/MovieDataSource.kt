package binar.academy.challengesix.data.remote


import binar.academy.challengesix.service.ApiClient
import kotlinx.coroutines.*
import binar.academy.challengesix.data.remote.modal.Result

class MovieDataSource {

    @DelicateCoroutinesApi
    fun getMovies(movieCallBack: MovieCallBack): List<Result>{
        GlobalScope.launch(Dispatchers.IO){
            val response = ApiClient.instance.getAllMovie()
            runBlocking(Dispatchers.Main) {
                if (response.isSuccessful){
                    val result= response.body()
                    movieCallBack.onCompllete(result!!.results)
                }
            }

        }
        return emptyList()
    }
    interface MovieCallBack{
        fun onCompllete(result:List<Result>)
    }
}