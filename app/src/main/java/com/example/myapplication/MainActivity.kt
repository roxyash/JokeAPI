package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JokeAdapter(val listJoke:List<Joke>, val mainActivity: MainActivity): RecyclerView.Adapter<JokeAdapter.JokeHolder>(){
    class JokeHolder(val view:ViewGroup):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.joke, parent, false) as ViewGroup
        return JokeHolder(view)
    }

    override fun onBindViewHolder(holder: JokeHolder, position: Int) {
        val joke = listJoke[position]
        holder.view.findViewById<TextView>(R.id.id).text = joke.id.toString()
        holder.view.findViewById<TextView>(R.id.setup).text = joke.setup
        holder.view.setOnClickListener{
            val intent = Intent(mainActivity, ShowJokeActivity::class.java)
            intent.putExtra("JOKE", listJoke[position])
            mainActivity.startActivity(intent)
        }
    }

    override fun getItemCount() = listJoke.size
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_joke)
        val jokeRepository = JokeRepository(applicationContext)

        CoroutineScope(Dispatchers.Main).launch {
            var listForRecyclerView: List<Joke>? = null
            withContext(Dispatchers.IO){
                listForRecyclerView = jokeRepository.getListJoke()
            }

            recyclerView.adapter = JokeAdapter(listForRecyclerView!!, this@MainActivity)
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        findViewById<Button>(R.id.load).setOnClickListener{
            val type = findViewById<EditText>(R.id.type).text.toString()
            val ethernetJoke = EthernetJoke()
            ethernetJoke.getJokeByType(type){
                when(it){
                    is EthernetJoke.ResultOK ->{
                        recyclerView.adapter = JokeAdapter(it.listJokes, this@MainActivity)
                        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                        CoroutineScope(Dispatchers.IO).launch {
                            jokeRepository.saveToDb(it.listJokes)
                        }

                    }
                    is EthernetJoke.ResultFAIL ->{
                        Toast.makeText(this@MainActivity, "Error" + it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}