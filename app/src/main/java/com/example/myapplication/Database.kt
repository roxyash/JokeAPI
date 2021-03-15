package com.example.myapplication

import android.content.Context
import androidx.room.*

@Entity
data class JokeDB (
    @ColumnInfo(name="id") val id:Int?,
    @ColumnInfo(name="setup") val setup:String?,
    @ColumnInfo(name="punchline") val punchline:String?
){
    @PrimaryKey(autoGenerate = true) var uid:Int = 0
}

@Dao
interface JokeDao{
    @Query("SELECT * FROM JokeDB")
    fun getAll():List<Joke>
    @Query("DELETE FROM JokeDB")
    fun clear()
    @Insert
    fun insert(jokes:List<JokeDB>)
}

@Database(entities = arrayOf(JokeDB::class), version = 3)
abstract class JokeDatabase:RoomDatabase(){
    abstract fun jokeDao():JokeDao
}

class JokeRepository(val applicationContext: Context){
    val db = Room.databaseBuilder(applicationContext, JokeDatabase::class.java, "Joke").fallbackToDestructiveMigration().build()

    fun getListJoke():List<Joke>{
        val list = db.jokeDao().getAll()
        val listForRecyclerView = list.map{Joke().apply {
            id = it.id
            setup   = it.setup
            punchline = it.punchline
        }}
        return listForRecyclerView
    }

    fun saveToDb(listJokes:List<Joke>){
        db.jokeDao().clear()
        db.jokeDao().insert(
            listJokes.map{
                JokeDB(
                    it.id!!,
                    it.setup!!,
                    it.punchline!!
                )
            }
        )
    }
}