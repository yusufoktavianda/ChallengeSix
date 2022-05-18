package binar.academy.challengesix.data.local

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user: User) : Long

    @Query("SELECT * FROM user WHERE email = :email AND password= :password")
    suspend fun authLogin(email: String? = null, password:String?=null):User

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getAllData(username: String?):User

//    @Query("SELECT imgPath FROM user where email = :email")
//    fun getImagePath(email: String?=null) : User

    @Query("UPDATE User SET username = :username ,fullName=:fullname ,birthDate= :birthdate ,address= :address , imgPath= :path WHERE email= :email")
    suspend fun updateData(email: String?=null, username:String?=null, fullname:String?=null, birthdate:String?=null, address:String?=null, path: String?=null):Int
}