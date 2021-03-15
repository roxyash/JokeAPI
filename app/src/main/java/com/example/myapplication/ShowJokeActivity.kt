package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ShowJokeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_joke)

        val joke = intent.getSerializableExtra("JOKE") as Joke

        findViewById<TextView>(R.id.id).text = joke.id.toString()

        findViewById<TextView>(R.id.setup).text = joke.setup
        findViewById<TextView>(R.id.punchline).text = joke.punchline
    }
}