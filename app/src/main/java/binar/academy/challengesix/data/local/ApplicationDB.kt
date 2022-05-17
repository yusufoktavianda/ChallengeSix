package binar.academy.challengesix.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class ApplicationDB : RoomDatabase (){
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: ApplicationDB? = null

        fun getInstance(context: Context): ApplicationDB? {
            if (INSTANCE == null) {
                synchronized(ApplicationDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ApplicationDB::class.java,
                        "MovieCinema.db"
                    ).build()
                }
            }
            return INSTANCE
        }

//        fun destroyInstance() {
//            INSTANCE = null
//        }
    }
}