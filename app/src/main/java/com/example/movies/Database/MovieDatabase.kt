package com.example.movies.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movies.DataModel.Movie

@Database(entities=[Movie::class], version=1, exportSchema=false)
abstract class MovieDatabase : RoomDatabase(){
    abstract  fun getMovieDao(): MovieDao

    companion object{
        @Volatile
        private var INSTANCE: MovieDatabase?=null
        fun getDatabase(context: Context): MovieDatabase{
            val tempInstance= INSTANCE
            if(tempInstance!=null) return tempInstance
            synchronized(this){
                val instance=
                    Room.databaseBuilder(context.applicationContext, MovieDatabase::class.java,"movies_db")
                    .allowMainThreadQueries().build()

                INSTANCE=instance
                return instance
            }
        }
    }
}